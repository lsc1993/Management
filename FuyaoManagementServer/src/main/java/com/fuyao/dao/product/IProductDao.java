package com.fuyao.dao.product;

import java.util.HashMap;

import com.fuyao.model.product.Product;
import com.fuyao.model.product.ProductImages;
import com.fuyao.model.product.ProductStandard;

public interface IProductDao {
	HashMap<String,String> addProduct(Product product);
	HashMap<String,String> addPImages(ProductImages image);
	HashMap<String,String> addStandard(ProductStandard standard);
	long getProductId(String pid);
}
