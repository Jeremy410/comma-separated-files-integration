/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unapec.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Jeremy Lopez
 */
public class DbContext {
    
    private Connection connection = null;
    private String host = "";
    private String database = "";
    private String port = "";
    private String user = "";
    private String password = "";
    
    public DbContext(String host, String database, String port, String user, String password) {
        this.host = host;
        this.database = database;
        this.port = port;
        this.user = user;
        this.password = password;       
    }
    
    public Connection connect() throws ClassNotFoundException, SQLException {      
        Class.forName("oracle.jdbc.driver.OracleDriver");
        connection = DriverManager.getConnection("jdbc:oracle:thin:@" + host + ":" + port + ":"+ database, user, password);
        
        return connection;
    }
    
    public ResultSet getData(String sql) throws SQLException {        
        Statement stmt = this.connection.createStatement();
        return stmt.executeQuery(sql);        
    }
    
     public int insertData(String sql) throws SQLException {        
        Statement stmt = this.connection.createStatement();
        return stmt.executeUpdate(sql);
      
    }
}
