package com.model2.mvc.service.domain;

import java.sql.Date;


public class Purchase {
	
	private User buyer;
	private String receiverAddr;
	private String receiverDate;
	private String receiverRequest;
	private Date orderDate;
	private String paymentOption;
	private Product purchaseProd;
	private String receiverName;
	private String receiverPhone;
	private String tranCode;
	private int tranNo;
	private int purchasePrice;
	private int purchaseQuantity;
	
	public Purchase(){
	}
	
	public User getBuyer() {
		return buyer;
	}
	public void setBuyer(User buyer) {
		this.buyer = buyer;
	}
	
	public String getReceiverAddr() {
		return receiverAddr;
	}

	public void setReceiverAddr(String receiverAddr) {
		this.receiverAddr = receiverAddr;
	}

	public String getReceiverDate() {
		return receiverDate;
	}

	public void setReceiverDate(String receiverDate) {
		this.receiverDate = receiverDate;
	}

	public String getReceiverRequest() {
		return receiverRequest;
	}

	public void setReceiverRequest(String receiverRequest) {
		this.receiverRequest = receiverRequest;
	}

	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public String getPaymentOption() {
		return paymentOption.trim();
	}
	public void setPaymentOption(String paymentOption) {
		this.paymentOption = paymentOption;
	}
	public Product getPurchaseProd() {
		return purchaseProd;
	}
	public void setPurchaseProd(Product purchaseProd) {
		this.purchaseProd = purchaseProd;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public String getReceiverPhone() {
		return receiverPhone;
	}
	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}
	public String getTranCode() {
		return tranCode.trim();
	}
	public void setTranCode(String tranCode) {
		this.tranCode = tranCode;
	}
	public int getTranNo() {
		return tranNo;
	}
	public void setTranNo(int tranNo) {
		this.tranNo = tranNo;
	}
	
	public int getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(int purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public int getPurchaseQuantity() {
		return purchaseQuantity;
	}

	public void setPurchaseQuantity(int purchaseQuantity) {
		this.purchaseQuantity = purchaseQuantity;
	}

	@Override
	public String toString() {
		return "Purchase [buyer=" + buyer + ", receiverAddr=" + receiverAddr + ", receiverDate=" + receiverDate
				+ ", receiverRequest=" + receiverRequest + ", orderDate=" + orderDate + ", paymentOption="
				+ paymentOption + ", purchaseProd=" + purchaseProd + ", receiverName=" + receiverName
				+ ", receiverPhone=" + receiverPhone + ", tranCode=" + tranCode + ", tranNo=" + tranNo
				+ ", purchasePrice=" + purchasePrice + ", purchaseQuantity=" + purchaseQuantity + "]";
	}

	
	
}