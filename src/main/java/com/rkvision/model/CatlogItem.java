package com.rkvision.model;

public class CatlogItem {
	
	private int rating;
	private String name;
	private String description;
	public CatlogItem( String name, String description, int rating) {
		super();
		this.rating = rating;
		this.name = name;
		this.description = description;
	}
	public CatlogItem() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Integer getRating() {
		return rating;
	}
	public void setRating(Integer rating) {
		this.rating = rating;
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
	@Override
	public String toString() {
		return "CatlogItem [rating=" + rating + ", name=" + name + ", description=" + description + "]";
	}
	
	

}
