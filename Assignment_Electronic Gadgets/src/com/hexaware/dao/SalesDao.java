package com.hexaware.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.hexaware.entity.Sale;
import com.hexaware.util.DBConnUtil;

public class SalesDao {

	public void addToSalesReport(Sale sale) {
		try {
			Connection connection=DBConnUtil.getMyDBConnection();
			PreparedStatement preparedStatement=connection.prepareStatement("insert into sale values(?,?,?,?)");
			preparedStatement.setInt(1, sale.getSaleId());		
			preparedStatement.setInt(2, sale.getProductId());
			preparedStatement.setDouble(3, sale.getAmount());
			preparedStatement.setString(4, sale.getDate());
			int x=preparedStatement.executeUpdate();
			System.out.println(x+" rows successful");
			
		}catch(SQLException e) {
			e.getStackTrace();
		}
		
	}

	public void getReport() {
		try {
			Connection connection=DBConnUtil.getMyDBConnection();
			PreparedStatement preparedStatement=connection.prepareStatement("select saleid,(select productname from product where product.productid=sale.productid),amount,date from sale");
			ResultSet rs=preparedStatement.executeQuery();
			while(rs.next()) {
				System.out.println("SaleId: "+rs.getInt(1));
				System.out.println("Product Name: "+rs.getString(2));
				System.out.println("Amount: "+rs.getDouble(3));
				System.out.println("Sale Date : "+rs.getString(4));
				System.out.println();
			}
		}catch(SQLException e) {
			e.getStackTrace();
		}
	
	}

}
