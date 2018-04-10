/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.testing;

import com.lib.dal.access.DBExecutor;
import com.lib.dal.entites.DBConfig;
import java.util.Date;
import java.util.List;

/**
 *
 * @author programer10
 */


public class Start {
    public static void main(String[] args) {
        DBConfig con = new DBConfig("den1.mssql1.gear.host", "testiranjejave", "testiranjejave", "Lu00?H1-GJKa");
        
        DBExecutor exec = new DBExecutor(con);
        
        List<Testiranje> testic = exec.executeQuery("Select * from Tablica1", array ->  new Testiranje((int)array[0], array[1].toString(), (Boolean)array[2], (Date)array[3]));
    
        for (Testiranje testiranje : testic) {
            System.out.println(testiranje);
        }
    }
    
    
}

class Testiranje{
        int id;
        String naziv;
        Boolean ozenjen;
        Date datum;

        public Testiranje(int id, String naziv, Boolean ozenjen, Date datum) {
            this.id = id;
            this.naziv = naziv;
            this.ozenjen = ozenjen;
            this.datum = datum;
        }

        @Override
        public String toString() {
            return "Testiranje{" + "id=" + id + ", naziv=" + naziv + ", ozenjen=" + ozenjen + ", datum=" + datum + '}';
        }
        
        
    }

