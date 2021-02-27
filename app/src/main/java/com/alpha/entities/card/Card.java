package com.alpha.entities.card;

import java.time.LocalDate;

import com.alpha.entities.customer.Customer;

public abstract class Card {

	// private fields
	private int id;
	private Customer customer;
	private String cardNumber;
	private LocalDate expireDate;
	private String cardCode;
	
	// constructors
	public Card() {
		
	}

	// getters and setters
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		
		if(customer == null) {
			throw new IllegalArgumentException("Customer cannot be null");
		}
		this.customer = customer;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		
		if(!(cardNumber.length() == 16)) {
			throw new IllegalArgumentException("Card Number must be 16 digit number");
		}
		this.cardNumber = cardNumber;
	}

	public LocalDate getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(LocalDate expireDate) {
		
		if(expireDate.isBefore(LocalDate.now().plusDays(1))) {
			throw new IllegalArgumentException("Invalid expire date, please enter a new one");
		}
			
		this.expireDate = expireDate;
	}

	public String getCardCode() {
		return cardCode;
	}

	public void setCardCode(String cardCode) {
		
		if(!(cardCode.length() == 3)) {
			throw new IllegalArgumentException("Card code must be 3 digit number");
		}
		this.cardCode = cardCode;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}
