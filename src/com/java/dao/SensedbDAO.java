package com.java.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

import com.java.bo.PatternBO;
import com.java.iface.SensedbIface;

public class SensedbDAO implements SensedbIface{
	private Connection connection;
	
	
	public Connection getConnection() {
		if(connection == null){
			createConnection();
		}
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	private void createConnection(){
		Connection c = null;
		try {
		     Class.forName("org.hsqldb.jdbc.JDBCDriver" );
		     c = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost", "SA", "");
		     
		 } catch (Exception e) {
		     System.err.println("ERROR: failed to load HSQLDB JDBC driver.");
		     e.printStackTrace();
		 }

		 this.setConnection(c);
	}

	@Override
	public void insertData(String feedback,String result) {
		// TODO Auto-generated method stub
		String query = "INSERT INTO \"PUBLIC\".\"SENTIMENT_ANALYSIS\" ( \"ID\",\"FEEDBACK\", \"RESULT\" ) VALUES ()";
		
		
	}

	@Override
	public HashMap<String, String> fetchFeedback() {
		String query = "select FEEDBACK,RESULT FROM \"PUBLIC\".\"SENTIMENT_ANALYSIS\"";
		
		HashMap<String, String> feedbackresults = new HashMap<String, String>();
		try{
		Connection conn = getConnection();
		Statement stmnt = conn.createStatement();
		ResultSet rs = stmnt.executeQuery(query);
		
		while(rs.next()){
			String key = rs.getString("FEEDBACK");
			String value = rs.getString("result");
			feedbackresults.put(key, value);
		}
		
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return feedbackresults;
	}
	
	public int getnextCount(){
		int count = 0;
		try{
		
		String query = "select max(ID)as ID from \"PUBLIC\".\"SENTIMENT_ANALYSIS\"";
		Connection conn = getConnection();
		Statement stmnt = conn.createStatement();
		ResultSet rs = stmnt.executeQuery(query);
		if(rs.next()){
		count = rs.getInt("ID");
		count++;
		}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public HashMap<String, String> fetchDictionary() {
		
		// TODO Auto-generated method stub
		return null;
	}

}
