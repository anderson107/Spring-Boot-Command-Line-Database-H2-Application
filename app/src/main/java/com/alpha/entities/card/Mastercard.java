package com.alpha.entities.card;

import java.time.LocalDate;

import com.alpha.entities.customer.Customer;

public class Mastercard extends Card {

	// Builder for Mastercard
	static class Builder {

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

		public Mastercard build() {
			Mastercard master = new Mastercard();
			master.setCustomer(this.customer);
			master.setCardNumber(this.cardNumber);
			master.setExpireDate(this.expireDate);
			master.setCardCode(this.cardCode);
			return master;
		}
	}
	
	@Override
	public String toString() {
		return "MASTERCARD \n" + "CARD ID: " + getId() + "\n"+ getCustomer() + "\nCARD NUMBER: " + getCardNumber()
		+ "\nEXPIRE= " + getExpireDate() + "\nCARD CODE: " + getCardCode();
	}
}
