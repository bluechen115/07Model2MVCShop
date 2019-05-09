package com.model2.mvc.service.product.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Discount;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductDao;
import com.model2.mvc.service.product.ProductService;

@Service("productServiceImpl")
public class ProductServiceImpl implements ProductService {

	@Autowired
	@Qualifier("productDaoImpl")
	ProductDao productDao;
	
	public ProductServiceImpl() {
	}

	@Override
	public void addProduct(Product product) throws Exception {
		product.setSaleStatus("1"); // 재고있음으로 세팅
		productDao.insertProduct(product);
	}

	@Override
	public Product getProduct(int prodNo) throws Exception {
		return productDao.findProduct(prodNo);
	}

	@Override
	public Product getProductByBoardNo(int boardNo) throws Exception {
		return productDao.findProductByBoardNo(boardNo);
	}
	
	@Override
	public Map<String, Object> getProductList(Search search) throws Exception {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("list", productDao.getProductList(search));
		map.put("totalCount", productDao.getTotalCount(search));
		
		return map;
	}

	@Override
	public void updateProduct(Product product) throws Exception {
		String manuDate = product.getManuDate().replaceAll("-", "");
		product.setManuDate(manuDate);
		productDao.updateProduct(product);
	}

	@Override
	public List<Product> getProductListByBoardNoInStock(int boardNo) throws Exception {
		return productDao.selectProductListByBoardNoInStock(boardNo);
	}

	@Override
	public void updateProductSaleStatus(Product product) throws Exception {
		productDao.updateProductSaleStatus(product);
	}


	/*@Override
	public void plusViewCount(int prodNo) throws Exception {
		productDao.addViewCount(prodNo);
	}*/

}
