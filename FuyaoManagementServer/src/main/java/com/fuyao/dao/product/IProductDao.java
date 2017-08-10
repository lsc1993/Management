package com.fuyao.dao.product;

import java.util.HashMap;

import com.alibaba.fastjson.JSON;
import com.fuyao.model.product.Product;
import com.fuyao.model.product.ProductImages;
import com.fuyao.model.product.ProductStandard;

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
}
