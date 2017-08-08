package com.fuyao.service.product;

import java.util.HashMap;

import com.fuyao.model.product.Product;
import com.fuyao.model.product.ProductImages;
import com.fuyao.model.product.ProductStandard;

public interface IProductService {
	HashMap<String,String> addProduct(Product product);
	HashMap<String,String> addPImages(ProductImages image);
	HashMap<String,String> addStandard(ProductStandard standard);
	long getProductId(Product product);
}
