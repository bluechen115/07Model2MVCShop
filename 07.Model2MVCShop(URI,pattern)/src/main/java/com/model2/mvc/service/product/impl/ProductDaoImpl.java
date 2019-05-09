package com.model2.mvc.service.product.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Discount;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductDao;

@Repository("productDaoImpl")
public class ProductDaoImpl implements ProductDao {
	
	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSession sqlsession;
	
	public ProductDaoImpl() {
	}

	@Override
	public Product findProduct(int prodNo) throws Exception {
		return sqlsession.selectOne("ProductMapper.findProduct", prodNo);
	}

	@Override
	public Product findProductByBoardNo(int boardNo) throws Exception {
		return sqlsession.selectOne("ProductMapper.findProductByBoardNo", boardNo);
	}
	
	@Override
	public List<Product> getProductList(Search search) throws Exception {
		return sqlsession.selectList("ProductMapper.getProductList", search);
	}

	@Override
	public void insertProduct(Product product) throws Exception {
		sqlsession.insert("ProductMapper.insertProduct", product);
		
	}

	@Override
	public void updateProduct(Product product) throws Exception {
		sqlsession.update("ProductMapper.updateProduct", product);
	}

/*	@Override
	public void addViewCount(int prodNo) throws Exception {
		sqlsession.update("ProductMapper.addViewCount", prodNo);
	}*/


	@Override
	public int getTotalCount(Search search) throws Exception {
		return sqlsession.selectOne("ProductMapper.getTotalCount", search);
	}


/*	@Override
	public Discount selectDiscountProd() throws Exception {
		return sqlsession.selectOne("ProductMapper.selectDiscountProd");
	}*/

/*	@Override
	public void insertDiscountProd(Discount discount) throws Exception {
		sqlsession.insert("ProductMapper.insertDiscountProd", discount);
	}

	@Override
	public int getRandomProdNo() throws Exception {
		return sqlsession.selectOne("ProductMapper.getRandomProdNo");
	}
*/
}
