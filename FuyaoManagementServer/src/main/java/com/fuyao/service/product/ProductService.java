package com.fuyao.service.product;

import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fuyao.dao.product.IProductDao;
import com.fuyao.model.product.Product;

@Transactional
@Service("productService")
public class ProductService implements IProductService {
	
	@Resource
	private IProductDao productDao;
	
	public void setProducrDao(IProductDao producrDao) {
		this.productDao = producrDao;
	}

	public HashMap<String, String> addProduct(Product product) {
		// TODO Auto-generated method stub
		return productDao.addProduct(product);
	}
	
}
