package com.model2.mvc.service.product.impl;

import java.util.HashMap;
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
		productDao.insertProduct(product);
	}

	@Override
	public Map<String, Object> getProduct(int prodNo) throws Exception {
		Map<String, Object> map=new HashMap<String,Object>();
		map.put("product", productDao.findProduct(prodNo));
		return map;
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


	/*@Override
	public void plusViewCount(int prodNo) throws Exception {
		productDao.addViewCount(prodNo);
	}*/

}
