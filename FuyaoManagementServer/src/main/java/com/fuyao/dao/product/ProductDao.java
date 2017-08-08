package com.fuyao.dao.product;

import java.util.HashMap;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.fuyao.model.product.Product;
import com.fuyao.model.product.ProductImages;
import com.fuyao.model.product.ProductStandard;

@Repository("productDao")
public class ProductDao implements IProductDao {
	
	@Resource
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	public HashMap<String, String> addProduct(Product product) {
		// TODO Auto-generated method stub
		HashMap<String,String> result = new HashMap<String,String>();
		Session session = this.getCurrentSession();
		String hql = "from Product where pid=:pid";
		Query<Product> query = session.createQuery(hql,Product.class);
		query.setParameter("pid", product.getpId());
		if (query.getResultList().size() >= 1) {
			result.put("result", "duplicate pid");
			return result;
		}
		session.save(product);
		result.put("result", "success");
		return result;
	}

	public HashMap<String, String> addPImages(ProductImages image) {
		// TODO Auto-generated method stub
		HashMap<String,String> result = new HashMap<String,String>();
		try {
			this.getCurrentSession().save(image);
			result.put("result", "success");
			return result;
		} catch (HibernateException e){
			result.put("result", "fault");
			e.printStackTrace();
			return result;
		}
	}

	public HashMap<String, String> addStandard(ProductStandard standard) {
		// TODO Auto-generated method stub
		HashMap<String,String> result = new HashMap<String,String>();
		try {
			this.getCurrentSession().save(standard);
			result.put("result", "success");
			return result;
		} catch (HibernateException e){
			result.put("result", "fault");
			e.printStackTrace();
			return result;
		}
	}

	public long getProductId(Product product) {
		// TODO Auto-generated method stub
		String hql = "from Product where pid=:pid";
		Query<Product> query = this.getCurrentSession().createQuery(hql,Product.class);
		return query.getResultList().get(0).getId();
	}
}
