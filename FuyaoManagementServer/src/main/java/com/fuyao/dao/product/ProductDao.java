package com.fuyao.dao.product;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.NoResultException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fuyao.model.product.Product;
import com.fuyao.model.product.ProductImages;
import com.fuyao.model.product.ProductStandard;
import com.fuyao.page.CommonPage;
import com.fuyao.service.product.ProductService.ProductStatus;
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
		CommonPage page = new CommonPage();
		String hql = "from Product";
		Query<Product> query = page.createQuery(this.getCurrentSession(), hql, start, limit);
		StringBuilder builder = new StringBuilder();
		builder.append("{");
		if (query.getResultList().size() > 0) {
			String json = JSON.toJSONString(query.getResultList(), SerializerFeature.WriteDateUseDateFormat);
			builder.append("\"rows\"").append(":").append(json).append(",");
		}
		builder.append("\"results\"").append(":").append(this.getProductCount()).append("}");
		Log.log(builder.toString());
		return (JSON) JSON.parse(builder.toString());
	}
	
	/*
	 * 获取产品图片
	 * @see com.fuyao.dao.product.IProductDao#getProductImages(long)
	 * @param pId 产品id
	 */
	public List<ProductImages> getProductImages(long pId) {
		// TODO Auto-generated method stub
		String hql = "from ProductImages where pid=:pid";
		Query<ProductImages> query = this.getCurrentSession().createQuery(hql, ProductImages.class);
		query.setParameter("pid", pId);
		return query.getResultList();
	}

	/*
	 * 获取产品规格
	 * @see com.fuyao.dao.product.IProductDao#getProductStandard(long)
	 * @param pId 产品id
	 */
	public List<ProductStandard> getProductStandard(long pId) {
		// TODO Auto-generated method stub
		String hql = "from ProductStandard where pid=:pid";
		Query<ProductStandard> query = this.getCurrentSession().createQuery(hql, ProductStandard.class);
		query.setParameter("pid", pId);
		return query.getResultList();
	}

	/*
	 * 获取产品详情
	 * @see com.fuyao.dao.product.IProductDao#getProduct(java.util.HashMap)
	 * @param data 产品查询参数
	 */
	public Product getProduct(String pId) {
		// TODO Auto-generated method stub
		String hql = "from Product where pid=:pid";
		Query<Product> query = this.getCurrentSession().createQuery(hql,Product.class);
		query.setParameter("pid", pId);
		Product p;
		try {
			p = query.getSingleResult();
		} catch (NoResultException e) {
			p = null;
			e.printStackTrace();  
		}
		return p;
	}

	public HashMap<String, String> changeProduct(Product product) {
		// TODO Auto-generated method stub
		HashMap<String,String> result = new HashMap<String,String>();
		this.getCurrentSession().update(product);
		result.put("result", "success");
		return result;
	}

	public HashMap<String, String> changeStandard(ProductStandard standard) {
		// TODO Auto-generated method stub
		HashMap<String,String> result = new HashMap<String,String>();
		this.getCurrentSession().saveOrUpdate(standard);
		result.put("result", "success");
		return result;
	}

	public HashMap<String, String> changeStatus(long id, ProductStatus status) {
		// TODO Auto-generated method stub
		HashMap<String, String> result = new HashMap<String, String>();
		String hql = "update Product set status=:status where id=:id";
		Query<?> query = this.getCurrentSession().createQuery(hql);
		query.setParameter("status", status.getStatus());
		query.setParameter("id", id);
		int updateCount = query.executeUpdate();
		if (updateCount == 1) {
			result.put("result", "success");
			result.put("message", "操作成功");
		} else {
			result.put("result", "fault");
			result.put("message", "操作失败，请重试");
		}
		return result;
	}
}
