package server.tool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import server.dao.ControlDB;

public class LinkDB {
	public static final String URL = "jdbc:mysql://localhost:3306/Chat1chat?serverTimezone=UTC";
    public static final String USER = "root";
    public static final String PASSWORD = "huang0524";
    
   
    
    public static  Statement getStatement() throws Exception {
    	 //1.加载驱动程序
        Class.forName("com.mysql.cj.jdbc.Driver");
        //2. 获得数据库连接
        Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
        //3.操作数据库，实现增删改查
        Statement stmt = conn.createStatement();
        
        return stmt;
    }
    public static void main(String[] args) throws Exception {
        //1.加载驱动程序
        Class.forName("com.mysql.cj.jdbc.Driver");
        //2. 获得数据库连接
        Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
        //3.操作数据库，实现增删改查
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT friend FROM relation WHERE '"+"狄仁杰"+"'=username");
        //如果有数据，rs.next()返回true
        while(rs.next()){
            System.out.println(rs.getString("friend"));
        }
      
        System.out.println("test");
       // controlDB.addFriend("猴子","狄仁杰");

        ControlDB.delectFriend("猴子","狄仁杰");
        // controlDB.putMessage("ds","da","ds","2018-11-08 17:30:49") ;
        // controlDB.delectMessage("ds","da","ds","2018-11-08 17:30:49") ;
    }
}

