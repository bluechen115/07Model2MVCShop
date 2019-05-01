package com.model2.mvc.service.purchase.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Code;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.purchase.PurchaseDao;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.user.UserDao;

@Service("purchaseServiceImpl")
public class PurchaseServiceImpl implements PurchaseService {

	@Autowired
	@Qualifier("purchaseDaoImpl")
	private PurchaseDao purchaseDao;
	
	@Autowired
	@Qualifier("userDaoImpl")
	private UserDao userDao;
	
	public PurchaseServiceImpl() {
	}

	@Override
	public void addPurchase(Purchase purchase) throws Exception {
		purchaseDao.insertPurchase(purchase);
	}

	@Override
	public Purchase getPurchase(int tranNo) throws Exception {
		return purchaseDao.findPurchase(tranNo);
	}

	@Override
	public Map<String, Object> getPurchaseList(Search search, String buyerId) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("search", search);
		map.put("buyerId", buyerId);
		List<Purchase> purchaseList = purchaseDao.getPurchaseList(map);
		for(int i=0;i<purchaseList.size();i++) {
			System.out.println(purchaseList.get(i).getTranNo());
		}
		
		
		List<Code> codeList=purchaseDao.codeCall("tr01");
		int totalCount = purchaseDao.getgetTotalCount(map);
		
		List<User> userList=new ArrayList<User>();
		for(int i=0;i<purchaseList.size();i++) {
			buyerId=purchaseList.get(i).getBuyer().getUserId();
			userList.add(userDao.getUser(buyerId));
		}
		
		map=new HashMap<String,Object>();
		
		map.put("purchaseList", purchaseList);
		map.put("userList", userList);
		map.put("totalCount", totalCount);
		map.put("codeList", codeList);
		
		return map;
	}

	@Override
	public void updatePurcahse(Purchase purchase) throws Exception {
		purchaseDao.updatePurchase(purchase);
	}

	@Override
	public void updateTranCode(Purchase purchase) throws Exception {
		purchaseDao.updateTranCode(purchase);
	}

	@Override
	public List<Code> codeCall(String groupId) throws Exception {
		return purchaseDao.codeCall(groupId);
	}

	@Override
	public int getCountPurchase(String buyerId) throws Exception {
		return purchaseDao.getCountPurchase(buyerId);
	}

}
