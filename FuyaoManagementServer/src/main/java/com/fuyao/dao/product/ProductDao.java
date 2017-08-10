package com.fuyao.dao.product;

import java.util.HashMap;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.fuyao.model.product.Product;
import com.fuyao.model.product.ProductImages;
import com.fuyao.model.product.ProductStandard;
import com.fuyao.page.ProductPage;
import com.fuyao.util.Log;

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
		query.setParameter("pid", product.getPId());
		if (query.getResultList().size() >= 1) {
			result.put("result", "duplicate pid");
			return result;
		}
		try {
			session.save(product);
			result.put("result", "success");
			result.put("message", "产品上传成功");
		} catch (HibernateException e) {
			result.put("result", "fault");
			result.put("message", "产品上传失败");
			e.printStackTrace();
		}
		return result;
	}

	public HashMap<String, String> addPImages(ProductImages image) {
		// TODO Auto-generated method stub
		HashMap<String,String> result = new HashMap<String,String>();
		try {
			this.getCurrentSession().save(image);
			result.put("message", "图片上传成功");
			result.put("result", "success");
		} catch (HibernateException e){
			result.put("message", "图片上传失败");
			result.put("result", "fault");
			e.printStackTrace();
		}
		return result;
	}

	public HashMap<String, String> addStandard(ProductStandard standard) {
		// TODO Auto-generated method stub
		HashMap<String,String> result = new HashMap<String,String>();
		try {
			this.getCurrentSession().save(standard);
			result.put("message", "产品规格同步数据库成功");
			result.put("result", "success");
		} catch (HibernateException e){
			result.put("message", "产品规格同步数据库失败");
			result.put("result", "fault");
			e.printStackTrace();
		}
		return result;
	}

	public long getProductId(String pid) {
		// TODO Auto-generated method stub
		String hql = "from Product where pid=:pid";
		Query<Product> query = this.getCurrentSession().createQuery(hql,Product.class);
		query.setParameter("pid", pid);
		if (query.getResultList().size() >= 1) {
			return query.getResultList().get(0).getId();
		}
		return 0;
	}

	public long getProductCount() {
		// TODO Auto-generated method stub
		String hql = "select count(*) from Product";
		Query<Long> query = this.getCurrentSession().createQuery(hql,Long.class);
		long count = query.getSingleResult();
		Log.log("Count:"+count);
		return count;
	}

	public JSON getProductList(HashMap<String,String> data) {
		// TODO Auto-generated method stub
		int start;
		try {
			start = Integer.parseInt(data.get("start"));
		} catch (NumberFormatException e) {
			start = 1;
			e.printStackTrace();
		}
		int limit;
		try {
			limit = Integer.parseInt(data.get("limit"));
		} catch (NumberFormatException e) {
			limit = 15;
			e.printStackTrace();
		}
		ProductPage page = new ProductPage();
		Query<Product> query = page.createQuery(this.getCurrentSession(), start, limit);
		StringBuilder builder = new StringBuilder();
		builder.append("{");
		if (query.getResultList().size() > 0) {
			JSONArray ja = (JSONArray) JSONArray.toJSON(query.getResultList());
			Log.log(ja.toString());
			String json = JSON.toJSONString(query.getResultList());
			builder.append("\"rows\"").append(":").append(json).append(",");
		}
		builder.append("\"results\"").append(":").append(this.getProductCount()).append("}");
		Log.log(builder.toString());
		return (JSON) JSON.parse(builder.toString());
	}
}
