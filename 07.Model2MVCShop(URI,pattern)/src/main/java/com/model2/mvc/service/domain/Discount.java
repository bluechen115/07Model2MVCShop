package com.model2.mvc.service.domain;

public class Discount {
	
	private String discountDate;
	private int discountProdBoard;
	
	public String getDiscountDate() {
		return discountDate;
	}
	public void setDiscountDate(String discountDate) {
		this.discountDate = discountDate;
	}
	public int getDiscountProd() {
		return discountProdBoard;
	}
	public void setDiscountProd(int discountProd) {
		this.discountProdBoard = discountProd;
	}

	@Override
	public String toString() {
		return "Discount [discountDate=" + discountDate + ", discountProdBoard=" + discountProdBoard + "]";
	}
}
