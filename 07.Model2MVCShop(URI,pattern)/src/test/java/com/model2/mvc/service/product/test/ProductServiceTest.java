package com.model2.mvc.service.product.test;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Discount;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;

import junit.framework.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:config/context-common.xml",
										"classpath:config/context-aspect.xml",
										"classpath:config/context-mybatis.xml",
										"classpath:config/context-transaction.xml"})
public class ProductServiceTest {
	
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	
	//@Test
	public void TestFindProduct() throws Exception {
		Map<String, Object> map = productService.getProduct(10100);
		Product product= (Product)map.get("product");
		
		Assert.assertEquals("테스트", product.getProdName());
	}
	
	//@Test
	public void TestInsertProduct() throws Exception{
		Product product=new Product();
		product.setFileName("");
		product.setManuDate("19/02/03");
		product.setPrice(1000);
		product.setProdDetail("상세정보");
		product.setProdName("테스트");
		
		productService.addProduct(product);
		
		Map<String, Object> map = productService.getProduct(10100);
		product= (Product)map.get("product");
		
		Assert.assertEquals("테스트", product.getProdName());
	}
	
	//@Test
	public void TestUpdateProduct() throws Exception{
		Product product=new Product();
		product.setFileName("");
		product.setManuDate("19/12/03");
		product.setPrice(10000);
		product.setProdDetail("상세정보수정함");
		product.setProdName("테스트");
		product.setProdNo(10100);
		
		productService.updateProduct(product);
		
		Map<String, Object> map = productService.getProduct(10100);
		product= (Product)map.get("product");
		
		Assert.assertEquals("상세정보수정함", product.getProdDetail());
	}
	
	@Test
	public void TestGetProductList() throws Exception{
		Search search=new Search();
		search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	
		Map<String, Object> map = productService.getProductList(search);
		
		List<Product> list=(List<Product>)map.get("list");
		int totalCount=((Integer)map.get("totalCount")).intValue();
		Discount discount=(Discount)map.get("discount");
		
		Assert.assertEquals(3, list.size());
		System.out.println("totalCount :: "+totalCount);
		//Assert.assertEquals(10007, discount.getDiscountProd());
	}
	
	//@Test
	public void TestPlusViewCount() throws Exception {
		int prodNo = 10100;
		productService.plusViewCount(prodNo);
	}
	

}
