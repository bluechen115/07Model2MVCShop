package com.model2.mvc.service.product;

import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;



public interface ProductService {
	
	public void addProduct(Product product) throws Exception;

	public Map<String,Object> getProduct(int prodNo) throws Exception;
	
	public Product getProductByBoardNo(int boardNo) throws Exception;

	public Map<String,Object> getProductList(Search search) throws Exception;

	public void updateProduct(Product product) throws Exception;
	
	/*public void plusViewCount(int prodNo) throws Exception;*/
	
	
	
}