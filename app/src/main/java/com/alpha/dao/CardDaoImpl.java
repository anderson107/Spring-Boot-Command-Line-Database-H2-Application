package com.alpha.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alpha.entities.card.Card;
import com.alpha.entities.card.CardFactory;
import com.alpha.entities.customer.Customer;

public class CardDaoImpl implements CardDao {

	// fields
	private DataSource dataSource;
	private static Logger logger = LoggerFactory.getLogger(CardDaoImpl.class);
	
	//constructors
	public CardDaoImpl() {
		
	}

	@Override
	public void setDataSource(DataSource conn) {
		this.dataSource = conn;
	}

	//CRUD methods
	//returns all cards in the DB
	@Override
	public List<Card> findAll() {
		List<Card> list = new ArrayList<>();
		Connection conn = null;
		Statement stmt = null;
		String query = "select c.customer_id, c.first_name, c.last_name, c.email,"
				+ " c.address, s.card_id, s.customer_id, s.card_number, s.card_code,"
				+ " s.expire_date from customer c left join card s on c.customer_id = s.customer_id";
		try {
			conn = dataSource.getConnection();
			stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery(query);

			while (result.next()) {
				Customer client = new Customer();
				client.setId(result.getInt("customer_id"));
				client.setFirstName(result.getString("first_name"));
				client.setLastName(result.getString("last_name"));
				client.setAddress(result.getString("address"));
				client.setEmail(result.getString("email"));
				
				Date sqlDate = result.getDate("expire_date");
				LocalDate date = sqlDate.toLocalDate();
				
				Card card = CardFactory.createCard(
						result.getString("card_number"), 
						client,
						result.getString("card_code"), 
						date);
				card.setId(result.getInt("card_id"));
				list.add(card);
			}
			return list;
		} catch (SQLException e) {
			logger.error("Problem loading cards");
			return null;
		} finally {
			closeConnection(conn, stmt);
		}
	}

	// it returns card by id
	@Override
	public Card findCard(int id) {
		
		Connection conn = null;
		PreparedStatement stmt = null;
		String query = "select c.customer_id, c.first_name, c.last_name, c.email,"
				+ " c.address, s.card_id, s.customer_id, s.card_number, s.card_code,"
				+ " s.expire_date from customer c left join card s on c.customer_id = s.customer_id where s.card_id =" + id;
		Card card = null;
		try {
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement(query);
			ResultSet result = stmt.executeQuery();
			while(result.next()) {
				Customer customer = new Customer();
				customer.setId(result.getInt("customer_id"));
				customer.setFirstName(result.getString("First_name"));
				customer.setLastName(result.getString("last_name"));
				customer.setAddress(result.getString("address"));
				customer.setEmail(result.getString("email"));
				String cardNumber = result.getString("card_number");
				String cardCode = result.getString("card_code");
				LocalDate date = result.getDate("expire_date").toLocalDate();
				card = CardFactory.createCard(cardNumber, customer, cardCode, date);
				card.setId(result.getInt("card_id"));
			}
			return card;
		}catch(SQLException e) {
			logger.error("Card was not found", e);
			return null;
		}finally {
			closeConnection(conn, stmt);
		}
	}

	// it add a card to the db
	@Override
	public void insert(Card card) {
		Connection conn = null;
		PreparedStatement stmt = null;
		String query = "insert into card(customer_id, card_number, card_code, expire_date) values (?, ?, ?, ?)";
		try {
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, card.getCustomer().getId());
			stmt.setString(2, card.getCardNumber());
			stmt.setString(3, card.getCardCode());
			stmt.setDate(4, Date.valueOf(card.getExpireDate()));
			stmt.execute();
			
			ResultSet generatedKeys = stmt.getGeneratedKeys();
			if(generatedKeys.next()) {
				card.setId(generatedKeys.getInt(1));
			}
			
			logger.info("Card added successfully");
		}catch(SQLException e) {
			logger.error("Problem inserting card", e);
		}finally {
			closeConnection(conn, stmt);
		}

	}

	// it updates card code and expire date
	@Override
	public void update(Card card) {
		Connection conn = null;
		PreparedStatement stmt = null;
		String query = "update card SET card_number = ?, card_code =?, expire_date=? where card_id=?";
		
		try {
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement(query);
			stmt.setString(1, card.getCardNumber());
			stmt.setString(2, card.getCardCode());
			Date date = Date.valueOf(card.getExpireDate());
			stmt.setDate(3, date);
			stmt.setInt(4, card.getId());
			stmt.executeUpdate();
			
		}catch(SQLException e) {
			logger.error("Problem updating card");
		}finally {
			closeConnection(conn, stmt);
		}
	}

	// it deletes a card by id
	@Override
	public void delete(int id) {
		Connection conn = null;
		PreparedStatement stmt = null;
		String query = "delete from card where card_id=?";
		
		try {
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, id);
			stmt.execute();
			logger.info("Card deleted successfully");
		}catch(SQLException e) {
			logger.error("Problem deleting card", e);
		}finally {
			closeConnection(conn, stmt);
		}
	}

	// it closes the connections
	private void closeConnection(Connection conn, Statement smt) {

		if (conn == null && smt == null) {
			return;
		}
		try {
			smt.close();
			conn.close();
		} catch (Exception e) {
			logger.error("Problem closing connection or statement", e);
		}
	}

}
