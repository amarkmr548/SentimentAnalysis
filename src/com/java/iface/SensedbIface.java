package com.java.iface;

import java.util.HashMap;

public interface SensedbIface {
	public void insertData();
	public HashMap<String, String> fetchFeedback();
	public HashMap<String,String> fetchDictionary();
}
