package com.model2.mvc.service.product;

import java.util.List;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Discount;
import com.model2.mvc.service.domain.Product;

public interface ProductDao {
	
	public Product findProduct(int prodNo) throws Exception ;
	
	public Product findProductByBoardNo(int boardNo) throws Exception;
	
	public List<Product> getProductList(Search search) throws Exception;
	
	public void insertProduct(Product product) throws Exception;
	
	public void updateProduct(Product product) throws Exception;
	
	public int getTotalCount(Search search) throws Exception;
	
//	public void addViewCount(int prodNo) throws Exception;
	
//	public Discount selectDiscountProd() throws Exception;
	
//	public void insertDiscountProd(Discount discount) throws Exception;
//	
//	public int getRandomProdNo() throws Exception;
	
}
