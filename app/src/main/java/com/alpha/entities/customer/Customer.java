package com.alpha.entities.customer;

public class Customer {

	// private fields
	private int id;
	private String firstName;
	private String lastName;
	private String address;
	private String email;

	// constructors
	public Customer() {

	}

	public Customer(int id, String firstName, String lastName, String address, String email) {
		this.setId(id);
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.email = email;
	}

	// getters and setters
	public String getFirstName() {
		return firstName;
	}

	// Argument must match only letters
	public void setFirstName(String firstName) {

		if (!firstName.matches("^[a-zA-Z]+$")) {
			throw new IllegalArgumentException("Please, enter only letters for first name");
		}
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	// Argument must match only letters
	public void setLastName(String lastName) {

		if (!lastName.matches("^[a-zA-Z]+$")) {
			throw new IllegalArgumentException("Please, enter only letters for last name");
		}

		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	// Argument cannot be null or empty
	public void setAddress(String address) {

		if (address == null || address.equals("")) {
			throw new IllegalArgumentException("Please, enter a valid address");
		}

		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	// Argument must be email format
	public void setEmail(String email) {

		if (!email.matches("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")) {
			throw new IllegalArgumentException("Please, enter a valid email address e.g email@email.com");
		}
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	// to string method
	@Override
	public String toString() {
		return "NAME: " + firstName + "\nSURNAME: " + lastName + "\nADDRESS: " + address + " \nEMAIL: " + email;
	}

}
