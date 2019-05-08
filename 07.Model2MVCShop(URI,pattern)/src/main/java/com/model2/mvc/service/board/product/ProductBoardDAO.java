package com.model2.mvc.service.board.product;

import java.util.List;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Discount;
import com.model2.mvc.service.domain.ProductBoard;

public interface ProductBoardDAO {
	
	public void insertProductBoard(ProductBoard productBoard) throws Exception;
	
	public ProductBoard selectProductBoardByBoardNo(int boardNo) throws Exception;
	
	public List<ProductBoard> selectProductBoardList(Search search) throws Exception;
	
	public void updateProductBoard(ProductBoard productBoard) throws Exception;
	
	public void deleteProductBoardByBoardNo(int boardNo) throws Exception;
	
	public int selectTotalCount(Search search) throws Exception;
	
	public void updateViewCount(int boardNo) throws Exception;
	
	public Discount selectDiscountProdBoard() throws Exception;
	
	public void insertDiscountProdBoard(Discount discount) throws Exception;
	
	public int selectRandomProdBoardNo() throws Exception;
	
}
