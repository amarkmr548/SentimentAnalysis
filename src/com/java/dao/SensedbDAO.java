package com.java.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;

import com.java.iface.SensedbIface;

public class SensedbDAO implements SensedbIface{
	
	private static Connection createConnection(){
		Connection c;
		try {
		     Class.forName("org.hsqldb.jdbc.JDBCDriver" );
		     c = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/xdb", "SA", "");
		     
		 } catch (Exception e) {
		     System.err.println("ERROR: failed to load HSQLDB JDBC driver.");
		     e.printStackTrace();
		     return null;
		 }

		 return c;
	}

	@Override
	public void insertData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public HashMap<String, String> fetchFeedback() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HashMap<String, String> fetchDictionary() {
		// TODO Auto-generated method stub
		return null;
	}

}
