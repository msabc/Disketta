/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lib.dal.entites;

import java.text.MessageFormat;

/**
 *
 * @author programer10
 */
public class DBConfig {
    
    //private final String connectionURL;
    private final String serverName;
    private String databaseName;
    private String userName;
    private String password;
    private Integer port = 1433;
    private String instanceName;
    private boolean integratedSecurity = false;

    public String getServerName() {
        return serverName;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public Integer getPort() {
        return port;
    }

    public String getInstanceName() {
        return instanceName;
    }

    public boolean getIntegratedSecurity() {
        return integratedSecurity;
    }
    
    
    
    //jdbc:sqlserver://[serverName[\instanceName][:portNumber]][;property=value[;property=value]]
//    public String getConnectionString(){
//        return MessageFormat.format("jdbc:sqlserver://{0};servername={1};integratedSecurity=true;authenticationScheme=JavaKerberos", arguments)
//    }

    public DBConfig(String serverName, String databaseName, String userName, String password) {
        this.serverName = serverName;
        this.databaseName = databaseName;
        this.userName = userName;
        this.password = password;
    }
    
    public DBConfig(String serverName, String databaseName, String userName, String password, Integer port) {
        this.serverName = serverName;
        this.databaseName = databaseName;
        this.userName = userName;
        this.password = password;
        this.port = port;
    }
    
}
