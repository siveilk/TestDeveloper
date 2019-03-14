package com.tes.colors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

@Entity
@NamedQuery(name = "getPeople", query = "select p from Person p")
public class Person {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private String lastname;
	private String zipcode;
	private String city;
	private Colors color;

	Person() {
	}

	Person(String lastname, String name, String zipcode, String city, Colors color) {
		this.name = name;
		this.lastname = lastname;
		this.zipcode = zipcode;
		this.city = city;
		this.color = color;
	}

	Person(int id, String lastname, String name, String zipcode, String city, Colors color) {
		this.id = id;
		this.name = name;
		this.lastname = lastname;
		this.zipcode = zipcode;
		this.city = city;
		this.color = color;
	}

	public int getId() {
		return id;
	}

	void setId(int id) {
		this.id = id;
	}

	public String getLastname() {
		return lastname;
	}

	public String getName() {
		return name;
	}

	public String getZipcode() {
		return zipcode;
	}

	public String getCity() {
		return city;
	}

	public Colors getColor() {
		return color;
	}

}
