package com.spring.domain;

public class Products {
	
	private int sku;
	private String description;
	private String category;
	private int price;
	private String location;
	private int qty;
	
	public int getSku() {
		return sku;
	}
	public void setSku(int sku) {
		this.sku = sku;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	
	@Override
    public String toString() {
        return "Products{" +
                "sku=" + sku + 
                "description='" + description + '\'' +
                "category='" + category + '\'' +
                "price=" + price +
                "location='" + location + '\'' +
                "qty=" + qty +
                '}';
    }

}
