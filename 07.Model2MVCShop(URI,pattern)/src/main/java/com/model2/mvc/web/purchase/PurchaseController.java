package com.model2.mvc.web.purchase;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Discount;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.user.UserService;

@Controller
@RequestMapping("/purchase/*")
public class PurchaseController {
	
	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;
	
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;

	@Autowired
	@Qualifier("purchaseServiceImpl")
	private PurchaseService purchaseService;
	
	@Value("#{commonProperties['pageUnit']}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	int pageSize;
	
	public PurchaseController() {
		System.out.println(this.getClass());
	}
	
	@RequestMapping("addPurchase")
	public String addPurchase(@ModelAttribute("purchase") Purchase purchase ,
								@RequestParam("buyerId") String buyerId,
								@RequestParam("prodNo") int prodNo,
								Model model) throws Exception{
		
		User user=userService.getUser(buyerId);
		
		Map<String, Object> map=productService.getProduct(prodNo);
		Product product=(Product)map.get("product");
		Discount discount=(Discount)map.get("discount");
		
		int purchaseCount = purchaseService.getCountPurchase(user.getUserId());
		int price=product.getPrice();
		if(product.getProdNo()==discount.getDiscountProd()) {
			price=(int)(product.getPrice()*0.75);
		}
		if(purchaseCount % 4 == 0) {
			price=(int)(price*0.9);
		}
		
		purchase.setBuyer(user);
		purchase.setPurchaseProd(product);
		purchase.setTranCode("2");
		purchase.setPurchasePrice(price);
		
		purchaseService.addPurchase(purchase);
		
		model.addAttribute("purchase", purchase);
		
		return "forward:/purchase/successAddPurchase.jsp";
	}
	
	@RequestMapping("addPurchaseView")
	public String addPurchaseView(HttpSession session,
									HttpServletRequest request,
									@RequestParam("prod_no") int prodNo,
									Model model)throws Exception{
			
		User user=(User)session.getAttribute("user");
		
		Map<String, Object> map = productService.getProduct(prodNo);
		Product product=(Product)map.get("product");
		Discount discount=(Discount)map.get("discount");
		
		int purchaseCount = purchaseService.getCountPurchase(user.getUserId());
		int price=product.getPrice();
		if(product.getProdNo()==discount.getDiscountProd()) {
			price=(int)(product.getPrice()*0.75);
		}
		if(purchaseCount % 4 == 0) {
			price=(int)(price*0.9);
		}
		
		product.setResultPrice(price);
		
		model.addAttribute("user", user);
		model.addAttribute("product", product);
		model.addAttribute("discount", discount);
		model.addAttribute("purchaseCount", purchaseCount);
		
		return "forward:/purchase/addPurchaseView.jsp";
	}
	
	@RequestMapping("getPurchase")
	public String getPurchase(@RequestParam("tranNo") int tranNo,
								Model model)throws Exception{
		
		Purchase purchase=purchaseService.getPurchase(tranNo);
		User user=userService.getUser(purchase.getBuyer().getUserId());

		model.addAttribute("purchase", purchase);
		model.addAttribute("user", user);
		
		return "forward:/purchase/listPurchaseDetail.jsp";
	}
	
	@RequestMapping("listPurchase")
	public String getListPurchase(HttpSession session,
									@ModelAttribute("search") Search search,
									@ModelAttribute("page") Page page,
									Model model) throws Exception{

		if(page.getPageSize()!=0) {
			pageSize=page.getPageSize();
		}
		page.setPageUnit(pageUnit);
		page.setPageSize(pageSize);
		
		search.setPageSize(pageSize);
		
		User user=(User)session.getAttribute("user");
		Map<String, Object> map=purchaseService.getPurchaseList(search, user.getUserId());
		System.out.println("purchaseService ³¡");
		
		Page resultPage = new Page(search.getCurrentPage(), ((Integer) map.get("totalCount")).intValue(),
									page.getPageUnit(), page.getPageSize());
		
		model.addAttribute("map", map);
		model.addAttribute("search", search);
		model.addAttribute("resultPage", resultPage);
		
		return "forward:/purchase/listPurchase.jsp";
	}

	@RequestMapping("updatePurchase")
	public String updatePurchase(@RequestParam("tranNo") int tranNo,
									@RequestParam("buyerId") String buyerId,
									@ModelAttribute("purchase") Purchase purchase,
									Model model) throws Exception{
		
		User user=userService.getUser(buyerId);
		
		purchase.setBuyer(user);
		purchase.setTranNo(tranNo);
		
		purchaseService.updatePurcahse(purchase);
		
		purchase=purchaseService.getPurchase(tranNo);
		
		model.addAttribute("purchase", purchase);
		model.addAttribute("user", user);
		
		return "forward:/purchase/listPurchaseDetail.jsp";
	}
	
	@RequestMapping("updatePurchaseView")
	public String updatePurchaseView(@RequestParam("tranNo") int tranNo,
										Model model) throws Exception{
		
		Purchase purchase=purchaseService.getPurchase(tranNo);
		
		model.addAttribute("purchase",purchase);
		
		return "forward:/purchase/updatePurchaseView.jsp";
	}
	
	@RequestMapping("updateTranCode")
	public String updateTranCode(@RequestParam("tranNo") int tranNo,
									@RequestParam("tranCode") String tranCode) throws Exception{
		
		Purchase purchase=new Purchase();
		purchase.setTranNo(tranNo);
		purchase.setTranCode(tranCode);
		
		purchaseService.updateTranCode(purchase);
		
		return "redirect:/purchase/listPurchase";
	}
	
	@RequestMapping("updateTranCodeByProd")
	public String updateTranCodeByProd(@RequestParam("prodNo") int prodNo,
										@RequestParam("tranCode") String tranCode) throws Exception{
		
		Purchase purchase=new Purchase();
		Product product=new Product();
		
		Map<String, Object> map=productService.getProduct(prodNo);
		product=(Product)map.get("product");
		
		
		purchase.setPurchaseProd(product);
		purchase.setTranCode(tranCode);
		purchaseService.updateTranCode(purchase);
		purchase=purchaseService.getPurchase(purchase.getTranNo());
		
		return "redirect:/product/listProduct?menu=manage";
	}
	
}
















