/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lib.dal.access;

import com.lib.dal.entites.DBConfig;
import com.lib.dal.entites.SQLParameter;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
/**
 *
 * @author programer10
 */
public class DBExecutor {
    private SQLServerDataSource ds = new SQLServerDataSource();

    public DBExecutor(DBConfig config){
        try {
            System.out.println(config.getServerName());
            ds.setServerName(config.getServerName());
            ds.setInstanceName(config.getInstanceName());
            ds.setDatabaseName(config.getDatabaseName());
            ds.setUser(config.getUserName());
            ds.setPassword(config.getPassword());
            ds.setIntegratedSecurity(config.getIntegratedSecurity());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    public <T> List<T> executeQuery(String query, Function<Object[],T> converterMethod){
        try(Connection con = ds.getConnection();
            Statement st = con.createStatement();){
            
            ResultSet rs = st.executeQuery(query);
            return getResults(rs,converterMethod);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    /**
     Ako je poslana converterMethod onda znači da želiš stvoriti konkretne objekte od rezultata queryja,
     * tj. napravio si SELECT, inače želiš odraditi neku drugu operaciju na bazi.
     */
    public <T> Object executeProc(String procName,Function<Object[],T> converterMethod , SQLParameter... params){
        String call = "{ CALL " + procName;
        
        if (params.length > 0) {
            String paramDefinitions = " (";
            
            for (int i = 0; i < params.length; i++) {
                paramDefinitions += "?";
                if (i != params.length -1) {
                    paramDefinitions+=", ";
                }
            }
            
            paramDefinitions += ")";
            call += paramDefinitions;
        }
        
        call += " }";
        
        try(Connection con = ds.getConnection();
            CallableStatement st = con.prepareCall(call);) {
            
            for (int i = 0; i < params.length; i++) {
                SQLParameter parameter = params[i];
                if (!parameter.getIsOutputParameter()) {
                    st.setObject(i+1, parameter.getValue());
                }
                else{
                    st.registerOutParameter(i+1, Types.OTHER);
                }
            }
            
            if (converterMethod != null) {
                return getResults(st.executeQuery(), converterMethod);
            }
            else{
                int changed = st.executeUpdate();
                for (int i = 0; i < params.length; i++) {
                    SQLParameter param = params[i];
                    
                    if (param.getIsOutputParameter()) {
                        param.setValue(i+1);
                    }
                }
                System.out.println(changed);
                return st.executeUpdate();
            }
           
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private <T> List<T> getResults(ResultSet rs, Function<Object[], T> converterMethod) throws SQLException {
        List<T> result = new ArrayList<>();
            
            int numColumns = rs.getMetaData().getColumnCount();
            
            while (rs.next()) {
                Object[] columnData = new Object[numColumns];
                
                for (int i = 0; i < numColumns; i++) {
                    columnData[i] = rs.getObject(i+1);
                }
                result.add(converterMethod.apply(columnData));
            }
            return result;
    }

    
}
