package com.fuyao.controller.product;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fuyao.model.product.Product;
import com.fuyao.model.product.ProductImages;
import com.fuyao.service.product.IProductService;
import com.fuyao.util.FuyaoUtil;

@Controller
@RequestMapping("product")
@MultipartConfig
public class ProductController {
	
	@Resource
	private IProductService productService;

	public void setProductService(IProductService productService) {
		this.productService = productService;
	}
	
	@ResponseBody
	@RequestMapping(value="/upload",method=RequestMethod.POST)
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
		product.setPrice(price);
		product.setCount(count);
		
		result = productService.addProduct(product);
		if (!"success".equals(result.get("result"))) {
			return result;
		}
		
		//ArrayList<ProductImages> images = new ArrayList<ProductImages>();
		long id = productService.getProductId(product);
		OutputStream out;
		InputStream in;
		try {
			ArrayList<Part> pImgs = (ArrayList<Part>) request.getParts();
			//ArrayList<Part> dImgs = (ArrayList<Part>) request.getParts();
			for (int i = 0;i < pImgs.size();++i) {
				ProductImages image = new ProductImages();
				Part part = pImgs.get(i);
				String imgName = FuyaoUtil.getImageUUID() + FuyaoUtil.getImageType(part);
				image.setImage(imgName);
				image.setpId(id);
				result = productService.addPImages(image);
				//image.setProduct(product);
				//images.add(image);
				
				in = part.getInputStream();
				out = new FileOutputStream(new File(FuyaoUtil.IMAGE_PATH+imgName));
				int read = 0;
				byte[] bytes;
				if(part.getSize() <= Integer.MAX_VALUE) {
					bytes = new byte[(int)part.getSize()];
				} else {
					bytes = new byte[12400];
				}
				while ((read = in.read(bytes)) != -1) {
					out.write(bytes, 0, read);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
}
