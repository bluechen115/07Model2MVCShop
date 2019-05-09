package com.model2.mvc.service.domain;

public class Discount {
	
	private String discountDate;
	private int discountBoard;
	
	public String getDiscountDate() {
		return discountDate;
	}
	public void setDiscountDate(String discountDate) {
		this.discountDate = discountDate;
	}

	public int getDiscountBoard() {
		return discountBoard;
	}
	public void setDiscountBoard(int discountBoard) {
		this.discountBoard = discountBoard;
	}
	@Override
	public String toString() {
		return "Discount [discountDate=" + discountDate + ", discountBoard=" + discountBoard + "]";
	}
	
}
