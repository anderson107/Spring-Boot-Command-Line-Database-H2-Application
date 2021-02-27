package com.alpha.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.alpha.Config.DbConfig;
import com.alpha.dao.CardDao;
import com.alpha.dao.CardDaoImpl;
import com.alpha.dao.CustomerDao;
import com.alpha.dao.CustomerDaoImpl;
import com.alpha.entities.card.Card;
import com.alpha.entities.card.CardFactory;
import com.alpha.entities.customer.Customer;

public final class Application {

	// private fields
	private AnnotationConfigApplicationContext ctx = null;
	private CustomerDao customerDao;
	private CardDao cardDao;
	private BufferedReader br;
	
	// constructors
	public Application() {
		
		ctx = new AnnotationConfigApplicationContext(DbConfig.class);
		customerDao = ctx.getBean("customerDao", CustomerDaoImpl.class);
		cardDao = ctx.getBean("cardDao", CardDaoImpl.class);
		br = new BufferedReader(new InputStreamReader(System.in));
	}
	
	// display main menu
	public void displayMenu() {
		System.out.println("Select an option");
		System.out.println("1 - Customer management");
		System.out.println("2 - Card management");
		System.out.println("3 - Logout");
		String input = readUserInput();
		
		while(!input.matches("[1-3]")) {
			System.out.println("Please, enter a valid option");
			input = readUserInput();
		}
		
		switch(input) {
		case "1":
			displayCustomerMenu();
			break;
		case "2":
			displayCardMenu();
			break;
		case "3":
			close();
			break;
		}
	}
	
	private void close() {
		
		try {
			br.close();
		} catch (IOException e) {
			System.out.println("Error closing application, please try again");
			displayMenu();
		}
		
		ctx.close();
	}
	
	// display the card menu
	private void displayCardMenu() {
		System.out.println("Select an option");
		System.out.println("1 - Create new card");
		System.out.println("2 - Update card");
		System.out.println("3 - Delete card");
		System.out.println("4 - Search card by ID");
		System.out.println("5 - Show all cards");
		System.out.println("6 - Main menu");
		String input = readUserInput();
		
		while(!input.matches("[1-6]")) {
			System.out.println("Enter a valid option");
			input = readUserInput();
		}
		
		switch(input){
		case "1":
			createNewCard();
			break;
		case "2":
			updateCard();
			break;
		case "3":
			deleteCard();
			break;
		case "4":
			searchCardById();
			break;
		case "5":
			showAllCards();
			break;
		case "6":
			displayMenu();
			break;
		}
		
	}

	// update card menu
	private void updateCard() {
		Card card = null;
		System.out.println("Enter card id number");
		String input = readUserInput();
		while(!input.matches("[0-9]+")) {
			System.out.println("Enter a valid number");
			input = readUserInput();
		}
		int id = Integer.parseInt(input);
		try {
			card = cardDao.findCard(id);
			
			if(card == null) {
				System.out.println("Card was not found");
				displayCardMenu();
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
			displayCardMenu();
		}
		
		System.out.println("Select an option:");
		System.out.println("1 - Update Expire Date");
		System.out.println("2 - Update Card code");
		input = readUserInput();
		while(!input.matches("[1-2]")) {
			System.out.println("Enter a valid option");
			input = readUserInput();
		}
		
		try {
		switch(input) {
		case "1":
			System.out.println("Enter expire Date");
			input = readUserInput();
			while(!input.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
				System.out.println("Enter a valid date e.g 2022-10-02");
				input = readUserInput();
			}
			card.setExpireDate(LocalDate.parse(input));;
			break;
		case "2":
			System.out.println("Enter new Card code");
			input = readUserInput();
			card.setCardCode(input);
				displayCardMenu();
			break;
		}
		}catch(IllegalArgumentException e) {
			System.out.println(e.getMessage());
			displayCardMenu();
		}
		
		try {
			cardDao.update(card);
		}catch(Exception e) {
			System.out.println(e.getMessage());
			displayCardMenu();
		}
		
		displayCardMenu();
	}

	// delete card menu
	private void deleteCard() {
		
		System.out.println("Enter card id: ");
		String input = readUserInput();
		
		while(!input.matches("[0-9]+")) {
			System.out.println("Enter a valid number");
			input = readUserInput();
		}
		
		int id = Integer.parseInt(input);
		
		try {
			cardDao.delete(id);
		}catch(Exception e) {
			System.out.println(e.getMessage());
			displayCardMenu();
		}
		
		displayCardMenu();
	}

	// search card by id menu
	private void searchCardById() {
		
		System.out.println("Enter card Id");
		String input = readUserInput();
		
		while(!input.matches("[0-9]+")) {
			System.out.println("Enter a valid number");
			input = readUserInput();
		}
		
		int id = Integer.parseInt(input);
		Card card = null;
		try {
			card = cardDao.findCard(id);
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
			displayCardMenu();
		}
		
		if(card == null) {
			System.out.println("Card not found by this ID");
			displayCardMenu();
		}
		
		System.out.println(card);
		displayCardMenu();
	}

	// show all cards 
	private void showAllCards() {
		
		List<Card>cardList = new ArrayList<>();
		
		try {
			cardList.addAll(cardDao.findAll());
			
			if(cardList.isEmpty()) {
				System.out.println("There is no cards registered");
				displayCardMenu();
			}
		}catch(Exception e){
			e.printStackTrace();
			System.out.println(e.getMessage());
			displayCardMenu();
		}
		
		for(Card c: cardList) {
			System.out.println(c);
			System.out.println();
		}
		
		displayCardMenu();
	}

	// create new card menu
	private void createNewCard() {
		
		System.out.println("Select an option");
		System.out.println("1 - Visa");
		System.out.println("2 - Master");
		System.out.println("3 - AmericanExpress");
		String input = readUserInput();
		
		while(!input.matches("[1-3]")) {
			System.out.println("Please, enter a valid option");
			input = readUserInput();
		}
		
		String cardType = null;
		
		switch(input) {
		case "1":
			cardType = "4";
			break;
		case "2":
			cardType = "5";
			break;
		case "3":
			cardType = "3";
			break;
		}
		
		String cardNumber = generateCardNumber(cardType);
		String cardCode = generateCardCode();
		
		System.out.println("Enter customer ID");
		input = readUserInput();
		while(!input.matches("[0-9]+")) {
			System.out.println("Enter a valid number");
			input = readUserInput();
		}
		
		int id = Integer.parseInt(input);
		Customer customer = null;
		try {
			customer = customerDao.findCustomer(id);
			
			if(customer == null) {
				System.out.println("Customer was not found");
				displayCardMenu();
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
			displayCardMenu();
		}
		
		LocalDate date = LocalDate.now().plusYears(2);
		
		Card card = CardFactory.createCard(cardNumber, customer, cardCode, date);
		
		try {
			cardDao.insert(card);
		}catch(Exception e) {
			System.out.println(e.getMessage());
			displayCardMenu();
		}
		displayCardMenu();
	}

	// displays customer management
	private void displayCustomerMenu() {
		System.out.println("Select an option");
		System.out.println("1 - Add customer");
		System.out.println("2 - Update customer");
		System.out.println("3 - Delete customer");
		System.out.println("4 - Find customer by ID");
		System.out.println("5 - Show all customers");
		System.out.println("6 - Main menu");
		
		String input = readUserInput();
		
		switch(input) {
		case "1":
			addCustomer();
			break;
		case "2":
			updateCustomer();
			break;
		case "3":
			deleteCustomer();
			break;
		case "4":
			fetchCustomer();
			break;
		case "5":
			showAllCustomers();
			break;
		case "6":
			displayMenu();
			break;
		}
		
	}

	// update customer menu
	private void updateCustomer() {
		
		System.out.println("Enter customer ID");
		String input = readUserInput();
		Customer customer = null;
		while(!input.matches("[0-9]+")) {
			System.out.println("Enter a valid number");
			input = readUserInput();
		}
		
		int id = Integer.parseInt(input);
		
		try {
			customer = customerDao.findCustomer(id);
			
			if(customer == null) {
				System.out.println("Customer was not found by this ID");
				displayCustomerMenu();
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
			displayCustomerMenu();
		}
		
		System.out.println(customer);
		System.out.println();
		System.out.println("What would you like to update?");
		System.out.println("1 - First Name");
		System.out.println("2 - Last Name");
		System.out.println("3 - Address");
		System.out.println("4 - Email");
		
		String input1 = readUserInput();
		
		while(!input1.matches("[0-4]+")) {
			System.out.println("Enter a valid option");
			input = readUserInput();
		}
		
		try {
			
			switch(input1) {
			case "1":
				System.out.println(customer.getFirstName());
				System.out.println("Enter new first name: ");
				String firstName = readUserInput();
				customer.setFirstName(firstName);
				break;
			case "2":
				System.out.println(customer.getLastName());
				System.out.println("Enter new last name");
				String lastName = readUserInput();
				customer.setLastName(lastName);
				break;
			case "3":
				System.out.println(customer.getAddress());
				System.out.println("Enter new address");
				String address = readUserInput();
				customer.setAddress(address);
				break;
			case "4":
				System.out.println(customer.getEmail());
				System.out.println("Enter new email address");
				String email = readUserInput();
				customer.setEmail(email);
				break;
			}

			customerDao.update(customer);
			
		}catch(IllegalArgumentException il) {
			System.out.println(il.getMessage());
			System.out.println();
			displayCustomerMenu();
		}catch(Exception e) {
			System.out.println(e.getMessage());
			displayCustomerMenu();
		}
		
		displayCustomerMenu();
	}

	// find customer by id menu
	private void fetchCustomer() {
		System.out.println("Enter customer ID");
		String input = readUserInput();
		Customer customer = null;
		while(!input.matches("[0-9]+")) {
			System.out.println("Enter a valid number");
			input = readUserInput();
		}
		
		int id = Integer.parseInt(input);
		
		try {
			customer = customerDao.findCustomer(id);
			
			if(customer == null) {
				System.out.println("Customer could not be found");
				displayCustomerMenu();
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
			displayCustomerMenu();
		}
		
		System.out.println(customer);
		System.out.println();
		displayCustomerMenu();
	}

	// delete customer menu
	private void deleteCustomer() {
		
		System.out.println("Enter customer id");
		String input = readUserInput();
		
		while(!input.matches("[0-9]+")) {
			System.out.println("Enter a valid number");
			input = readUserInput();
		}
		
		int id = Integer.parseInt(input);
		try {
			customerDao.delete(id);
		
		}catch(Exception e) {
			System.out.println(e.getMessage());
			displayCustomerMenu();
		}
		displayCustomerMenu();
	}

	// prints out all the customer in the DB
	private void showAllCustomers() {
		List<Customer>list = null;
		try {
			list = new ArrayList<>(customerDao.findAll());
			
			if(list.isEmpty()) {
				System.out.println("There is no customers registered");
				displayCustomerMenu();
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
			displayCustomerMenu();
		}
		
		for(Customer c: list) {
			System.out.println(c);
			System.out.println();
		}
		
		displayCustomerMenu();
	}

	// add customer menu
	private void addCustomer() {
		Customer customer = new Customer();
		try {
		System.out.println("Enter first name:");
		String firstName = readUserInput();
		customer.setFirstName(firstName);
		System.out.println("Enter last name");
		String lastName = readUserInput();
		customer.setLastName(lastName);
		System.out.println("Enter address");
		String address = readUserInput();
		customer.setAddress(address);
		System.out.println("Enter email");
		String email = readUserInput();
		customer.setEmail(email);
		
		}catch(IllegalArgumentException e) {
			System.out.println(e.getMessage());
			System.out.println();
			addCustomer();
		}
		
		try {
		customerDao.insert(customer);
		
		}catch(Exception e) {
			System.out.println("Customer could not be added" + e.getMessage());
			addCustomer();
		}
		
		displayCustomerMenu();
	}

	// it generates random card code
	private String generateCardCode() {
		String number = "";
		
		for(int i=0; i<3; i++) {
			int random = (int) (Math.random()*9);
			number = number+random;
		}
		return number;
	}
	// it generates a random number to the card
	private String generateCardNumber(String n) {
		String number = n;
		
		for(int i=0; i<15; i++) {
			int random = (int) (Math.random()*9);
			number = number + random;
		}
		
		return number;
	}
	// reads user's input
	private String readUserInput() {
		String input = null;
		try {
			input = br.readLine();
			return input;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
