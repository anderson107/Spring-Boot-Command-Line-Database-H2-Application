package com.alpha.entities.card;

import java.time.LocalDate;

import com.alpha.entities.customer.Customer;

public class Visa extends Card{

	// Builder for Visa
		public static class Builder {

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

			public Visa build() {
				Visa visa = new Visa();
				visa.setCustomer(this.customer);
				visa.setCardNumber(this.cardNumber);
				visa.setExpireDate(this.expireDate);
				visa.setCardCode(this.cardCode);
				return visa;
			}
		}

		@Override
		public String toString() {
			return "VISA \n"+ "CARD ID: " + getId() + "\n" + getCustomer() + "\nCARD NUMBER: " + getCardNumber()
					+ "\nEXPIRE= " + getExpireDate() + "\nCARD CODE: " + getCardCode();
		}
		
		
}
