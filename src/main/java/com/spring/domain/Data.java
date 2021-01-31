package com.spring.domain;

import java.util.List;

public class Data {
	
	private List<Products> products;
	private TransmissionSummary transmissionsummary;
	
	public List<Products> getProducts() {
		return products;
	}
	public void setProducts(List<Products> products) {
		this.products = products;
	}
	public TransmissionSummary getTransmissionsummary() {
		return transmissionsummary;
	}
	public void setTransmissionsummary(TransmissionSummary transmissionsummary) {
		this.transmissionsummary = transmissionsummary;
	}

	@Override
    public String toString() {
        return "Data{" +
                "products=" + products +
                ", transmissionsummary=" + transmissionsummary +
                '}';
    }
}
