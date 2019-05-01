package com.model2.mvc.service.domain;

public class Discount {
	
	private String discountDate;
	private int discountProd;
	
	public String getDiscountDate() {
		return discountDate;
	}
	public void setDiscountDate(String discountDate) {
		this.discountDate = discountDate;
	}
	public int getDiscountProd() {
		return discountProd;
	}
	public void setDiscountProd(int discountProd) {
		this.discountProd = discountProd;
	}

	@Override
	public String toString() {
		return "Discount [discountDate=" + discountDate + ", discountProd=" + discountProd + "]";
	}
}
