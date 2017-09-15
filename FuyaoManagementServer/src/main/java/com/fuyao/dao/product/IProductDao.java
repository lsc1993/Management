package com.fuyao.dao.product;

import java.util.HashMap;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.fuyao.model.product.Product;
import com.fuyao.model.product.ProductImages;
import com.fuyao.model.product.ProductStandard;
import com.fuyao.service.product.ProductService.ProductStatus;

public interface IProductDao {
	/*
	 * 添加产品信息接口
	 **/
	HashMap<String,String> addProduct(Product product);
	HashMap<String,String> addPImages(ProductImages image);
	HashMap<String,String> addStandard(ProductStandard standard);
	long getProductId(String pid);
	
	/*
	 * 获取产品列表分页
	 **/
	long getProductCount();
	JSON getProductList(HashMap<String,String> data);
	
	List<ProductImages> getProductImages(long pId);
	List<ProductStandard> getProductStandard(long pId);
	Product getProduct(String pId);
	
	HashMap<String,String> changeProduct(Product product);
	HashMap<String,String> changeStandard(ProductStandard standard);
	
	HashMap<String,String> changeStatus(long id, ProductStatus status);
}
