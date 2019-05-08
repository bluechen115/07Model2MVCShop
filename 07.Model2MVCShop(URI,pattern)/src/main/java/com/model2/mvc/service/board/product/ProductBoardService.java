package com.model2.mvc.service.board.product;

import java.util.List;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Discount;
import com.model2.mvc.service.domain.ProductBoard;

public interface ProductBoardService {
	
	public void addProductBoard(ProductBoard productBoard) throws Exception;
	
	public Map<String, Object> getProductBoardByBoardNo(int boardNo) throws Exception;
	
	public Map<String,Object> getProductBoardList(Search search) throws Exception;
	
	public void modifyProductBoard(ProductBoard productBoard) throws Exception;
	
	public void removeProductBoardByBoardNo(int boardNo) throws Exception;
	
	public void addViewCount(int boardNo) throws Exception;
	
}

