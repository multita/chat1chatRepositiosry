package server.service;

import server.tool.*;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

//public class UserManager {
//	static private Map<String,String> accountAndPassword=new HashMap<String,String>(); 
//
//	public static Map<String, String> getAccountAndPassword() {
//		return accountAndPassword;
//	}
//
//	public UserManager() {
//		Statement stmt;
//		try {
//			stmt = LinkDB.getStatement();
//		
//			 ResultSet rs = stmt.executeQuery("SELECT username, password FROM userInfo");
//			 while(rs.next()){
//				 accountAndPassword.put( rs.getString("username"), rs.getString("password"));
//				 
//			 }
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	
//}