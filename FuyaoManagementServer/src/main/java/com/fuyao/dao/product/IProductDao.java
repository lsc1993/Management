package com.fuyao.dao.product;

import java.util.HashMap;

import com.alibaba.fastjson.JSON;
import com.fuyao.model.product.Product;
import com.fuyao.model.product.ProductImages;
import com.fuyao.model.product.ProductStandard;

public interface IProductDao {
	/*
	 * ��Ӳ�Ʒ��Ϣ�ӿ�
	 **/
	HashMap<String,String> addProduct(Product product);
	HashMap<String,String> addPImages(ProductImages image);
	HashMap<String,String> addStandard(ProductStandard standard);
	long getProductId(String pid);
	
	/*
	 * ��ȡ��Ʒ�б��ҳ
	 **/
	long getProductCount();
	JSON getProductList(HashMap<String,String> data);
}
