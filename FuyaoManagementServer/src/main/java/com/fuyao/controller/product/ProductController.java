package com.fuyao.controller.product;

import java.util.HashMap;
import javax.annotation.Resource;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.fuyao.service.product.ProductService;
import com.fuyao.util.Log;

@Controller
@RequestMapping("product")
@MultipartConfig
public class ProductController {
	
	@Resource
	private ProductService productService;

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}
	
	@ResponseBody
	@RequestMapping(value="/upload",method=RequestMethod.POST)
	private HashMap<String,String> addProduct(HttpServletRequest request) {
		HashMap<String,String> result = new HashMap<String,String>();
		result = productService.uploadProduct(request);
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="/list",method=RequestMethod.POST)
	private JSON getProductList(HttpServletRequest request) {
		HashMap<String,String> data = new HashMap<String,String>();
		String start = request.getParameter("start");
		String limit = request.getParameter("limit");
		if (null != start) {
			data.put("start", start);
		} 
		if (null != limit) {
			data.put("limit", limit);
		}
		Log.log(data.toString());
		return productService.getProductList(data);
	}
	
	@ResponseBody
	@RequestMapping(value="/detail",method=RequestMethod.POST)
	private JSON getProductDetail(@RequestBody HashMap<String,String> data) {
		Log.log("detail:" + data.toString());
		JSON json = productService.getProductDetail(data);
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value="/change",method=RequestMethod.POST)
	private HashMap<String,String> changeProduct(HttpServletRequest request) {
		return productService.changeProduct(request);
	}
	
	@ResponseBody
	@RequestMapping(value="/status",method=RequestMethod.POST)
	private HashMap<String,String> changeProduct(@RequestBody HashMap<String,String> data) {
		Log.log(data.toString());
		return productService.changeStatus(data);
	}
}
