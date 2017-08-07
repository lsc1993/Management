package com.fuyao.controller.product;

import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fuyao.model.product.Product;
import com.fuyao.service.product.IProductService;

@Controller
@RequestMapping("product")
@MultipartConfig
public class ProductController {
	
	@Resource
	private IProductService productService;

	public void setProductService(IProductService productService) {
		this.productService = productService;
	}
	
	@RequestMapping("upload")
	private HashMap<String,String> addProduct(HttpServletRequest request) {
		boolean isSubmit = true;
		float price = 0;
		try{
			price = Float.parseFloat(request.getParameter("price"));
		} catch(NumberFormatException e) {
			isSubmit = false;
			e.printStackTrace();
		}
		
		HashMap<String,String> result = new HashMap<String,String>();
		if (!isSubmit) {
			result.put("result", "priceException");
			return result;
		}
		
		int count = 0;
		try{
			count = Integer.parseInt(request.getParameter("count"));
		} catch(NumberFormatException e) {
			isSubmit = false;
			e.printStackTrace();
		}
		if (!isSubmit) {
			result.put("result", "countException");
			return result;
		}
		
		String pId = request.getParameter("pId");
		String name = request.getParameter("name");
		String type = request.getParameter("type");
		String describe = request.getParameter("describe");
		
		Product product = new Product();
		
		product.setpId(pId);
		product.setName(name);
		product.setType(type);
		product.setDescribe(describe);
		return productService.addProduct(product);
	}
}
