package com.java.sentimentanalyser;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.tomcat.util.codec.binary.StringUtils;

import com.java.dao.SensedbDAO;
import com.java.iface.SensedbIface;



public class ReviewProcessor {
	SensedbIface sense;
	
	public String processReview(String line){
		StringTokenizer st = new StringTokenizer(line, ".");
		String result = "";
		
		while(st.hasMoreTokens()){
			String str = st.nextToken();
			result = process(str.toUpperCase().trim());
			
			if(null == result || ("").equals(result)){
				continue;
			}
			
		}
		if(null == result || ("").equals(result)){
			return "NEUTRAL";
		}
		
		return result;
	}
	
	public String process(String str){
		
		String result;
		sense = new SensedbDAO();
		HashMap<String, String> feedback = sense.fetchFeedback();
		
		Set<String> feedbackwords = feedback.keySet();
		
		Iterator<String> iter = feedbackwords.iterator();
		
		if(str.contains("BUT")){
			return "NEUTRAL";
		}
		
		if(str.contains("DON'T")){
			return "NEGATIVE";
		}
		
		while(iter.hasNext()){
			String keyword = iter.next().toUpperCase().trim();
			
			
			if(str.contains("NOT " + keyword)){
				result = feedback.get(keyword.toLowerCase().trim());
				
				if(result.trim().equalsIgnoreCase("POSITIVE")){
					return "NEGATIVE";
				}
				
				if(result.trim().equalsIgnoreCase("NEGATIVE")){
					return "POSITIVE";
				}
			}
			
			if(str.contains("NO " + keyword)){
				result = feedback.get(keyword.toLowerCase().trim());
				
				if(result.trim().equalsIgnoreCase("POSITIVE")){
					return "NEGATIVE";
				}
				
				if(result.trim().equalsIgnoreCase("NEGATIVE")){
					return "POSITIVE";
				}
			}
			
			if(str.contains(keyword)){
				return feedback.get(keyword.toLowerCase().trim());
			}
		}
		
		return "NEUTRAL";
	}
}
