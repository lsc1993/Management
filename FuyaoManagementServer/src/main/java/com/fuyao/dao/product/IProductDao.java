package com.fuyao.dao.product;

import java.util.HashMap;

import com.fuyao.model.product.Product;

public interface IProductDao {
	HashMap<String,String> addProduct(Product product);
}
