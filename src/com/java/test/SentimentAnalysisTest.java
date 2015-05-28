package com.java.test;

import org.junit.Before;
import org.junit.Test;

import com.java.dao.SensedbDAO;

import junit.framework.TestCase;

public class SentimentAnalysisTest extends TestCase {
	@Before
	public void setup(){
		
	}
	@Test
	public void testsensedbdao(){
		SensedbDAO sensedbDAO = new SensedbDAO();
		System.out.println(sensedbDAO.getnextCount());
	}
	
}
