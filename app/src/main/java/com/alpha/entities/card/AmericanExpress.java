package com.alpha.entities.card;

import java.time.LocalDate;

import com.alpha.entities.customer.Customer;

public class AmericanExpress extends Card {

	
	// Builder class for Amex
	static class Builder{
		
		private Customer customer;
		private String cardNumber;
		private LocalDate expireDate;
		private String cardCode;
		
		public Builder setCustomer(Customer customer) {
			this.customer = customer;
			return this;
		}
		
		public Builder setCardNumber(String number) {
			this.cardNumber = number;
			return this;
		}
		
		public Builder setExpireDate(LocalDate date) {
			this.expireDate = date;
			return this;
		}
		
		public Builder setCardCode(String code) {
			this.cardCode = code;
			return this;
		}
		
		public AmericanExpress build() {
			AmericanExpress amex = new AmericanExpress();
			amex.setCustomer(this.customer);
			amex.setCardNumber(this.cardNumber);
			amex.setExpireDate(this.expireDate);
			amex.setCardCode(this.cardCode);
			return amex;
		}
		
	}
	
	@Override
	public String toString() {
		return "AMERICANEXPRESS \n" + "CARD ID: " + getId() + "\n" + getCustomer() + "\nCARD NUMBER: " + getCardNumber()
		+ "\nEXPIRE= " + getExpireDate() + "\nCARD CODE: " + getCardCode();
	}
}
