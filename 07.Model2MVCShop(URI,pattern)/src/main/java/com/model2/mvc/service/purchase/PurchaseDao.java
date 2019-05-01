package com.model2.mvc.service.purchase;

import java.util.List;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Code;
import com.model2.mvc.service.domain.Purchase;

public interface PurchaseDao {

	public Purchase findPurchase(int no) throws Exception;
	
	public List<Purchase> getPurchaseList(Map<String,Object> map)
			throws Exception;
	
	public void insertPurchase(Purchase purchase) throws Exception;
	
	public void updatePurchase(Purchase purchase) throws Exception;
	
	public void updateTranCode(Purchase purchase) throws Exception;
	
	public int getgetTotalCount(Map<String,Object> map) throws Exception;
	
	public List<Code> codeCall(String groupId) throws Exception;
	
	public int getCountPurchase(String buyerId) throws Exception;
	
}
