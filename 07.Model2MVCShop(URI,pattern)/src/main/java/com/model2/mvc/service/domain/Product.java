package com.model2.mvc.service.domain;

import java.sql.Date;

import org.springframework.web.multipart.MultipartFile;


public class Product {
	
	private String fileName;
	private String manuDate;
	private int price;
	private String prodName;
	private int prodNo;
	private String proTranCode;
	private int resultPrice;
	private int boardNo;
	private String saleStatus;
	private MultipartFile uploadFile;
	
	public Product(){
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getManuDate() {
		return manuDate;
	}

	public void setManuDate(String manuDate) {
		this.manuDate = manuDate;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public int getProdNo() {
		return prodNo;
	}

	public void setProdNo(int prodNo) {
		this.prodNo = prodNo;
	}

	public String getProTranCode() {
		return proTranCode;
	}

	public void setProTranCode(String proTranCode) {
		this.proTranCode = proTranCode;
	}

	public int getResultPrice() {
		return resultPrice;
	}

	public void setResultPrice(int resultPrice) {
		this.resultPrice = resultPrice;
	}

	public int getBoardNo() {
		return boardNo;
	}

	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}

	public String getSaleStatus() {
		return saleStatus;
	}

	public void setSaleStatus(String saleStatus) {
		this.saleStatus = saleStatus;
	}

	public MultipartFile getUploadFile() {
		return uploadFile;
	}

	public void setUploadFile(MultipartFile uploadFile) {
		this.uploadFile = uploadFile;
	}

	@Override
	public String toString() {
		return "Product [fileName=" + fileName + ", manuDate=" + manuDate + ", price=" + price + ", prodName="
				+ prodName + ", prodNo=" + prodNo + ", proTranCode=" + proTranCode + ", resultPrice=" + resultPrice
				+ ", boardNo=" + boardNo + ", saleStatus=" + saleStatus + "]";
	}

	
	

}