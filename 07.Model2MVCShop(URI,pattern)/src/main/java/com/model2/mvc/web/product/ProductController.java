package com.model2.mvc.web.product;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Discount;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.purchase.PurchaseService;

@Controller
@RequestMapping("/product/*")
public class ProductController {
	
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	
	@Autowired
	@Qualifier("purchaseServiceImpl")
	private PurchaseService purchaseService;

	public ProductController() {
		System.out.println(this.getClass());
	}
	
	@Value("#{commonProperties['pageUnit']}")
	//@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	//@Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;
	
	@RequestMapping("addProduct")
	public ModelAndView addProduct(HttpServletRequest request,
									HttpServletResponse response) throws Exception{
		System.out.println("/addProduct");
		ModelAndView modelAndView=new ModelAndView();
		Product product=null;
		
		if(FileUpload.isMultipartContent(request)) {
			String tempDir=
					"C:\\Users\\USER\\git\\07Model2MVCShop\\07.Model2MVCShop(URI,pattern)\\WebContent\\images\\uploadFiles\\";
			
			DiskFileUpload fileUpload=new DiskFileUpload();
			fileUpload.setRepositoryPath(tempDir);
			fileUpload.setSizeMax(1024*1024*100);
			fileUpload.setSizeThreshold(1024*1024);
			
			
			if(request.getContentLength() < fileUpload.getSizeMax()) {
				product=new Product();
				StringTokenizer token=null;
				
				List fileItemList = fileUpload.parseRequest(request);
				int Size = fileItemList.size();

				for(int i = 0; i < Size; i++) {
					FileItem fileItem=(FileItem)fileItemList.get(i);
					
					if(fileItem.isFormField()) {
						if(fileItem.getFieldName().equals("manuDate")) {
							token = new StringTokenizer(fileItem.getString("euc-kr"), "-");
							String manuDate = token.nextToken()+token.nextToken()+token.nextToken();
							product.setManuDate(manuDate);
						}
						else if(fileItem.getFieldName().equals("prodName")) {
							product.setProdName(fileItem.getString("euc-kr"));
						}
						else if(fileItem.getFieldName().equals("prodDetail")) {
							product.setProdDetail(fileItem.getString("euc-kr"));
							}
						else if(fileItem.getFieldName().equals("price")) {
							product.setPrice(Integer.parseInt(fileItem.getString("euc-kr")));
						}
					}else { //파일형식이면..
							if(fileItem.getSize()>0) {
								int idx = fileItem.getName().lastIndexOf("\\");
								if(idx == -1) {
									idx=fileItem.getName().lastIndexOf("/");
								}
								String fileName = fileItem.getName().substring(idx+1);
								product.setFileName(fileName);
								try {
									File uploadedFile = new File(tempDir,fileName);
									fileItem.write(uploadedFile);
								}catch(IOException e) {
									System.out.println(e);
								}
							}else {
								product.setFileName("../../images/empty.GIF");
							}
					}
				}
				productService.addProduct(product);
				
			}else {
				int overSize = (request.getContentLength()/1000000);
				System.out.println("<script>alert('파일 크기는 1MB까지입니다. 올리신 파일 용량은"+overSize+"MB입니다');");
				System.out.println("history.back();</script>");
			}
		}else {
			System.out.println("인코딩 타입이 multipart/form-data가 아닙니다..");
		}
		System.out.println("::5::");
		
		modelAndView.addObject("product", product);
		modelAndView.setViewName("forward:/product/successAddProduct.jsp");
		
		return modelAndView;
	}
	
	@RequestMapping("getProduct")
	public ModelAndView getProduct(@RequestParam("prodNo") int prodNo,
								@RequestParam("menu") String menu,
								HttpServletRequest request,
								HttpServletResponse response,
								HttpSession session
								) throws Exception{
		System.out.println("/getProduct");
		
		User user=(User)session.getAttribute("user");
		
		if(menu.equals("search")) {
			productService.plusViewCount(prodNo);
		}
		
		Map<String,Object> map=productService.getProduct(prodNo);
		Product product = (Product)map.get("product");
		Discount discount = (Discount)map.get("discount");
		
		int purchaseCount = purchaseService.getCountPurchase(user.getUserId());
		int price=product.getPrice();
		if(product.getProdNo()==discount.getDiscountProd()) {
			price=(int)(product.getPrice()*0.75);
		}
		if(purchaseCount % 4 == 0) {
			price=(int)(price*0.9);
		}
		
		product.setResultPrice(price);
		
		//////////////Cookie//////////////////
		Cookie[] cookies = request.getCookies();
		Cookie cookie = null;

		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals("history")) {
					cookie = cookies[i];
					break;
				}
			}

		}
		if (cookie != null) {
			cookie = new Cookie("history", cookie.getValue() + "," + product.getProdNo());
			cookie.setPath("/");
			response.addCookie(cookie);
		} else {
			cookie = new Cookie("history", String.valueOf(product.getProdNo()));
			cookie.setPath("/");
			response.addCookie(cookie);
		}
		//////////////Cookie//////////////////
		String viewName="";
		if (menu != null) {
			if (menu.equals("search")) {
				viewName= "forward:/product/getProduct.jsp";
			}else {
				viewName="forward:/product/updateProductView.jsp";
			}
		}

		
		ModelAndView modelAndView=new ModelAndView();
		modelAndView.setViewName(viewName);
		modelAndView.addObject("product", product);
		modelAndView.addObject("discount", discount);
		modelAndView.addObject("user", user);
		modelAndView.addObject("purchaseCount", purchaseCount);
		
		return modelAndView;
	}	

	
	@RequestMapping(value="listProduct")
	public ModelAndView getListProduct(HttpServletRequest request,
									@ModelAttribute("search") Search search,
									@ModelAttribute("page") Page page,
									@RequestParam("menu") String menu
									) throws Exception {
		System.out.println("/listProduct");
		
		page.setPageUnit(pageUnit);
		
		if(page.getPageSize()==0) {
		page.setPageSize(pageSize);
		}
		
		if(search.getSearchKeyword()!=null) {
			if(request.getMethod().equals("GET")) {
//				search.setSearchKeyword(CommonUtil.convertToKo(request.getParameter("searchKeyword")));
				search.setSearchKeyword(URLDecoder.decode(search.getSearchKeyword(), "EUC-KR"));
				System.out.println("GET방식으로 실행");
			}else {
				search.setSearchKeyword(search.getSearchKeyword());
				System.out.println("POST방식으로 실행, SearchKeyword :: "+search.getSearchKeyword());
			}
		}
		
		search.setPageSize(page.getPageSize());
		
		Map<String,Object> map=productService.getProductList(search);
		
		Page resultPage=new Page(search.getCurrentPage(),((Integer)map.get("totalCount")).intValue(), page.getPageUnit(), page.getPageSize());

		ModelAndView modelAndView=new ModelAndView();
		modelAndView.addObject("list", map.get("list"));
		modelAndView.addObject("discount", map.get("discount"));
		modelAndView.addObject("resultPage", resultPage);
		modelAndView.addObject("search", search);
		modelAndView.setViewName("forward:/product/listProduct.jsp");
		
		return modelAndView;
		
	}
	
	@RequestMapping("updateProduct")
	public ModelAndView updateProduct(@ModelAttribute("product") Product product) throws Exception{
		
		System.out.println("/updateProduct.do");
		
		System.out.println("fileName :: "+product.getFileName());
		
		//productService.updateProduct(product);
		
		Map<String,Object> map=productService.getProduct(product.getProdNo());
		product = (Product)map.get("product");
		
		ModelAndView modelAndView=new ModelAndView();
		modelAndView.setViewName("forward:/product/getProduct.jsp");
		
		return modelAndView;
	}
	
	
	

}
