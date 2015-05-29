package com.java.test;

import java.util.HashMap;

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
		HashMap<String, String> mm = sensedbDAO.fetchFeedback();
		System.out.println(mm.size());
	}
	
}
