package com.hexaware.controller;

import com.hexaware.entity.Sale;
import com.hexaware.dao.SalesDao;
public class SalesController implements SalesControllerInterface {
	SalesDao salesDao=new SalesDao();
	public void addToSalesReport(Sale sale) {
		salesDao.addToSalesReport(sale);
	}
	public void getReport() {
		salesDao.getReport();
	}
}
