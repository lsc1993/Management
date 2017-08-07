package com.fuyao.service.product;

import java.util.HashMap;

import com.fuyao.model.product.Product;

public interface IProductService {
	HashMap<String,String> addProduct(Product product);
}
