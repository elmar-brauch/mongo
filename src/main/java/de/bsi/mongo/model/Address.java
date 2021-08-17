package de.bsi.mongo.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Address {
	
	private String city;
	private String street;

	public Address(String city, String street) {
		this.city = city;
		this.street = street;
	}
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	
}
