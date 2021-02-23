package com.cognizant.truyum.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConectionHandler {
         public static Connection getConnection() throws IOException, SQLException, ClassNotFoundException {
        	 Connection con = null;
        	 FileInputStream finput = new FileInputStream("src/connection.properties");
        	 Properties prop = new Properties();
        	 prop.load(finput);
        	 String driver = prop.getProperty("driver");
        	 String url = prop.getProperty("connection-url");
        	 String user = prop.getProperty("user");
        	 String pass = prop.getProperty("password");
        	 Class.forName(driver);
        	 con = DriverManager.getConnection(url, user, pass);
        	 return con;
         }
}
