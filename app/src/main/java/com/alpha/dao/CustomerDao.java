package com.alpha.dao;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

import javax.sql.DataSource;

import com.alpha.entities.customer.Customer;

// interface for customer CRUD operations
public interface CustomerDao {

	public List<Customer>findAll();
	public Customer findCustomer(int id);
	public void insert(Customer customer);
	public void delete(int id);
	public void update(Customer customer);
    public void closeConnection(Connection conn, Statement stmt);
    public void setDataSource(DataSource conn);
}
