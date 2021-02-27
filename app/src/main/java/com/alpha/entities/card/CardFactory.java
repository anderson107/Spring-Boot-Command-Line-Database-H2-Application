package com.alpha.entities.card;

import java.time.LocalDate;

import com.alpha.entities.customer.Customer;

public final class CardFactory {

	private CardFactory() {
		
	}
	
	// returns a card object according to the first number of the card number (4 visa, 5 master, and 3 amex)
	public static final Card createCard(String cardNumber, Customer customer, String cardCode, LocalDate expiredate) {
		Card card = null;
		char letter = cardNumber.charAt(0);
		switch(letter) {
		case '4':
			card = new Visa.Builder()
			.setCardNumber(cardNumber)
			.setCardCode(cardCode)
			.setCustomer(customer)
			.setExpireDate(expiredate)
			.build();
			break;
		case '5':
			card = new Mastercard.Builder()
			.setCardNumber(cardNumber)
			.setCardCode(cardCode)
			.setCustomer(customer)
			.setExpireDate(expiredate)
			.build();
			break;
		case '3':
			card = new AmericanExpress.Builder()
			.setCardNumber(cardNumber)
			.setCardCode(cardCode)
			.setCustomer(customer)
			.setExpireDate(expiredate)
			.build();
			break;
		default:
			return null;
		}
		return card;
	}
}
