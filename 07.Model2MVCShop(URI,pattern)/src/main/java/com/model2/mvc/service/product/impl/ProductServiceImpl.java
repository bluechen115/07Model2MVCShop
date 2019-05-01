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
		map.put("discount", productDao.selectDiscountProd());
		return map;
	}

	@Override
	public Map<String, Object> getProductList(Search search) throws Exception {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("list", productDao.getProductList(search));
		map.put("totalCount", productDao.getTotalCount(search));
		
		Discount discount=productDao.selectDiscountProd();
		if(discount==null) {
			int discountProd=productDao.getRandomProdNo();
			discount=new Discount();
			discount.setDiscountProd(discountProd);
			productDao.insertDiscountProd(discount);
		}
		map.put("discount", discount);
		
		return map;
	}

	@Override
	public void updateProduct(Product product) throws Exception {
		productDao.updateProduct(product);
	}

	@Override
	public void plusViewCount(int prodNo) throws Exception {
		productDao.addViewCount(prodNo);
	}

}
