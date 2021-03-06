package com.model2.mvc.service.board.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.board.product.ProductBoardService;
import com.model2.mvc.service.domain.Discount;
import com.model2.mvc.service.domain.ProductBoard;

import junit.framework.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:config/context-common.xml",
		"classpath:config/context-aspect.xml",
		"classpath:config/context-mybatis.xml",
		"classpath:config/context-transaction.xml"})
public class ProductBoardServiceTest {
	
	@Autowired
	@Qualifier("productBoardServiceImpl")
	private ProductBoardService productBoardService;
	
	//@Test
	public void TestaddProductBoard() throws Exception{
		ProductBoard productBoard = new ProductBoard();
		productBoard.setBoardDetail("상세정보");
		productBoard.setQuantity(5);
		productBoard.setTitle("제목");
		productBoard.setViewCount(0);
		
		productBoardService.addProductBoard(productBoard);
	}
	
	//@Test
	public void TestgetProductBoard() throws Exception{
		int boardNo = 1;
		Map<String, Object> map = productBoardService.getProductBoardByBoardNo(boardNo);
		ProductBoard productBoard = (ProductBoard)map.get("productBoard");
		
		Assert.assertEquals("상세정보", productBoard.getBoardDetail());
	}
	
	//@Test
	public void TestgetProductBoardList() throws Exception{
		Search search = new Search();
		search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	
	 	Map<String, Object> map = productBoardService.getProductBoardList(search);
	 	List<ProductBoard> list = (List<ProductBoard>)map.get("list");
	 	Discount discount = (Discount)map.get("discount");
	 	
	 	for(int i=0;i<list.size();i++) {
	 		System.out.println(list.get(i));
	 	}
	 	
	 	System.out.println("discount :: "+discount);
	 	//Assert.assertEquals(1, ((Integer)map.get("totalCount")).intValue());
	}
	
	//@Test
	public void TestModifyProductBoard() throws Exception{
		ProductBoard productBoard = new ProductBoard();
		productBoard.setBoardDetail("상세정보수정");
		productBoard.setBoardNo(1);
		
		productBoardService.modifyProductBoard(productBoard);
		
		Map<String, Object> map = productBoardService.getProductBoardByBoardNo(1);
		productBoard =  (ProductBoard)map.get("productBoard");
		
		Assert.assertEquals("상세정보수정",productBoard.getBoardDetail());
	}
	
	//@Test
	public void TestRemoveProductBoardByBoardNo() throws Exception{
		int boardNo = 1;
		
		productBoardService.removeProductBoardByBoardNo(boardNo);
	}
	
	//@Test
	public void TestAddViewCount() throws Exception{
		int boardNo = 2;
		productBoardService.addViewCount(boardNo);
		
		Map<String, Object> map = productBoardService.getProductBoardByBoardNo(boardNo);
		ProductBoard productBoard = (ProductBoard)map.get("productBoard");
		
		Assert.assertEquals(1, productBoard.getViewCount());
	}
	
}
