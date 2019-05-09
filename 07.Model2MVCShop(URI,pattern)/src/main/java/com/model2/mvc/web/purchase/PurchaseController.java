package com.model2.mvc.web.purchase;

import java.util.List;
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
import com.model2.mvc.service.board.product.ProductBoardService;
import com.model2.mvc.service.domain.Discount;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.ProductBoard;
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
	
	@Autowired
	@Qualifier("productBoardServiceImpl")
	private ProductBoardService productBoardService;
	
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
								@RequestParam("boardNo") int boardNo,
								Model model) throws Exception{
		
		User user=userService.getUser(buyerId);
		
		Map<String, Object> map = productBoardService.getProductBoardByBoardNo(boardNo);
		ProductBoard productBoard = (ProductBoard)map.get("productBoard");
		Discount discount = (Discount)map.get("discount");
		
		List<Product> listProd = productService.getProductListByBoardNoInStock(boardNo);
		
		
		int purchaseCount = purchaseService.getCountPurchase(user.getUserId());
		int price=productService.getProductByBoardNo(boardNo).getPrice();
		if(productBoard.getBoardNo()==discount.getDiscountBoard()) {
			price=(int)(price*0.75);
		}
		if(purchaseCount % 4 == 0) {
			price=(int)(price*0.9);
		}
		
		purchase.setBuyer(user);
		purchase.setTranCode("2");
		purchase.setPurchasePrice(price);
		
		int OriginalQuantity = productBoard.getQuantity();
		if((OriginalQuantity-purchase.getPurchaseQuantity()) >= 0){
			for(int i=0; i<purchase.getPurchaseQuantity();i++) {
				purchase.setPurchaseProd(listProd.get(i));
				purchaseService.addPurchase(purchase);
				listProd.get(i).setSaleStatus("0");
				productService.updateProductSaleStatus(listProd.get(i));
			}
			productBoard.setQuantity(OriginalQuantity-purchase.getPurchaseQuantity());
			productBoardService.modifyProductBoard(productBoard);
		}
		
		model.addAttribute("purchase", purchase);
		
		return "forward:/purchase/successAddPurchase.jsp";
	}
	
	@RequestMapping("addPurchaseView")
	public String addPurchaseView(HttpSession session,
									HttpServletRequest request,
									@RequestParam("boardNo") int boardNo,
									Model model)throws Exception{
			
		User user=(User)session.getAttribute("user");
		
//		Map<String, Object> map = productService.getProduct(prodNo);
//		Product product=(Product)map.get("product");
//		Discount discount=(Discount)map.get("discount");
		
		Map<String, Object> map = productBoardService.getProductBoardByBoardNo(boardNo);
		ProductBoard productBoard = (ProductBoard)map.get("productBoard");
		Discount discount = (Discount)map.get("discount");
		
		Product product = productService.getProductByBoardNo(boardNo);
		
		int purchaseCount = purchaseService.getCountPurchase(user.getUserId());
		int price=product.getPrice();
		if(productBoard.getBoardNo()==discount.getDiscountBoard()) {
			price=(int)(product.getPrice()*0.75);
		}
		if(purchaseCount % 4 == 0) {
			price=(int)(price*0.9);
		}
		
		product.setResultPrice(price);
		
		model.addAttribute("user", user);
		model.addAttribute("product", product);
		model.addAttribute("productBoard", productBoard);
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
		System.out.println("purchaseService 끝");
		
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
	
	@RequestMapping("cancelPurchase")
	public String cancelPurchase(@RequestParam("tranNo") int tranNo,
									@RequestParam("tranCode") String tranCode) throws Exception{
		
		Purchase purchase = purchaseService.getPurchase(tranNo);
		
		// 재고 복구
		Product product = productService.getProduct(purchase.getPurchaseProd().getProdNo());
		product.setProdNo(purchase.getPurchaseProd().getProdNo());
		product.setSaleStatus("1"); 
		productService.updateProductSaleStatus(product);
		
		// 수량 복구
		Map<String,Object> map = productBoardService.getProductBoardByBoardNo(product.getBoardNo());
		ProductBoard productBoard = (ProductBoard)map.get("productBoard");
		int originQuantity = productBoard.getQuantity();
		productBoard.setQuantity(originQuantity+1);
		productBoardService.modifyProductBoard(productBoard);
		
		purchase=new Purchase();
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
















