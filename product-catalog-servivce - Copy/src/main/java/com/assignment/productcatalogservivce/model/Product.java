package com.assignment.productcatalogservivce.model;

import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

@Document(collection = "products")
public class Product 
{
	@Field
	private String name;
	
	@Field
	private String description;
	
	@Field
	private double price;
	
	@Field
	private String availability;
	
	@Id
	private String id;

	public Product() {
		super();
		
	}

	public Product(String name, String description, double price, String availability, String id) {
		super();
		this.name = name;
		this.description = description;
		this.price = price;
		this.availability = availability;
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getAvailability() {
		return availability;
	}

	public void setAvailability(String availability) {
		this.availability = availability;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Product [name=" + name + ", description=" + description + ", price=" + price + ", availability="
				+ availability + ", id=" + id + "]";
	}
	
	
	
	

}
