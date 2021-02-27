package com.alpha.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alpha.entities.customer.Customer;

public class CustomerDaoImpl implements CustomerDao {

	//private fields
	private static Logger logger = LoggerFactory.getLogger(CustomerDaoImpl.class);
	private DataSource dataSource;

	// constructors
	public CustomerDaoImpl() {
		
	}
	
	@Override
	public void setDataSource(DataSource conn) {
		this.dataSource = conn;
	}

	// CRUD methods
	// return all customers
	@Override
	public List<Customer> findAll() {
		List<Customer>customer = new ArrayList<>();
		Customer client = null;
		Connection conn= null;
		PreparedStatement stmt = null;
		String query = "select * from customer";
		try {
			conn = (Connection) dataSource.getConnection();
			stmt= conn.prepareStatement(query);
			ResultSet results = stmt.executeQuery();

			while (results.next()) {
				client = new Customer();
				client.setId(results.getInt("customer_id"));
				client.setFirstName(results.getString("first_name"));
				client.setLastName(results.getString("last_name"));
				client.setEmail(results.getString("email"));
				client.setAddress(results.getString("address"));
				customer.add(client);
			}
			return customer;
		} catch (SQLException e) {
			logger.error("Customer list could not be updated", e);
			return null;
		} finally {
			closeConnection(conn, stmt);
		}
	}

	// return customer by Id input
	@Override
	public Customer findCustomer(int customerId) {

		Customer customer = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		String query = "select * from customer where customer_id =" + customerId;
		try {
			conn = (Connection) dataSource.getConnection();
			stmt = conn.prepareStatement(query);
			ResultSet results = stmt.executeQuery();

			while (results.next()) {
				customer = new Customer();
				customer.setId(results.getInt("customer_id"));
				customer.setFirstName(results.getString("first_name"));
				customer.setLastName(results.getString("last_name"));
				customer.setEmail(results.getString("email"));
				customer.setAddress(results.getString("address"));
			}
			
			return customer;
		} catch (SQLException e) {
			logger.error("Customer was not found", e);
			return null;
		} finally {
			closeConnection(conn, stmt);
		}
	}

	// insert customer object into DB
	@Override
	public void insert(Customer customer) {

		Connection conn = null;
		PreparedStatement stmt = null;
		String query = "insert into Customer(first_name, last_name, address, email) values (?, ?, ?, ?)";
		try {
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, customer.getFirstName());
			stmt.setString(2, customer.getLastName());
			stmt.setString(3, customer.getAddress());
			stmt.setString(4, customer.getEmail());
			stmt.execute();
			
			ResultSet generatedKeys = stmt.getGeneratedKeys();
			if(generatedKeys.next()) {
				customer.setId(generatedKeys.getInt(1));
			}
			
			logger.info(customer.getFirstName() + " added successfully");
		}catch(SQLException e) {
			logger.error("Customer could not be added", e);
		}finally {
			closeConnection(conn, stmt);
		}
			
	}

	// it removes customer from DB by id input
	@Override
	public void delete(int id) {

		Connection conn = null;
		Statement stmt = null;
		String query = "delete from customer where customer_id=" + id;
		String query2 = "select * from customer where customer_id =" + id;
		try {
			conn = dataSource.getConnection();
			 stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery(query2);
			
			String name = null;
			while(result.next()) {
				name = result.getString("first_name");
			}
			stmt.execute(query);
			logger.info(name + " deleted succesfully");
		}catch(SQLException e) {
			logger.error("Customer could not be deleted", e);
		}finally {
			closeConnection(conn, stmt);
		}
	}

	// it updates customer data
	@Override
	public void update(Customer customer) {

		Connection conn = null;
		PreparedStatement stmt = null;
		String query = "update customer set first_name = ?, last_name = ?, address = ?, email = ? where customer_id = ?";
		
		
		try {
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement(query);
			stmt.setInt(5, customer.getId());
			stmt.setString(1, customer.getFirstName());
			stmt.setString(2, customer.getLastName());
			stmt.setString(3, customer.getAddress());
			stmt.setString(4, customer.getEmail());
			
			stmt.executeUpdate();
			logger.info("Customer id: " + customer.getId() + " updated successfully");
			
		}catch(SQLException e) {
			logger.error("Problem updating customer", e);
			
		}finally {
			closeConnection(conn, stmt);
		}
	}

	// it closes data source connection
	@Override
	public void closeConnection(Connection conn, Statement stmt) {
		if (conn == null && stmt == null) {
			return;
		}
		try {
			conn.close();
			stmt.close();
		} catch (SQLException e) {
			logger.error("Problem closing connection", e);
		}

	}

}
