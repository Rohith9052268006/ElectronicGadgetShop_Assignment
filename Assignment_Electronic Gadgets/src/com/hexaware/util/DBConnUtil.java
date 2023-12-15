package com.hexaware.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnUtil {

	static Connection con;
	public static Connection getMyDBConnection(){
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JavaAssignment","Rohith","Rohith@02");
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return con;
	}
    
}
