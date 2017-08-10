package com.fuyao.page;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.fuyao.model.product.Product;

public class ProductPage extends Page {

	@SuppressWarnings("unchecked")
	@Override
	public <T> Query<T> createQuery(Session session) {
		// TODO Auto-generated method stub
		String hql = "select p from Product p";
		Query<Product> query = session.createQuery(hql,Product.class);
		return (Query<T>) query;
	}

	public Query<Product> createQuery(Session session, int start) {
		Query<Product> query = createQuery(session);
		query.setFirstResult(start);
		query.setMaxResults(15);
		return query;
	}
	
	public Query<Product> createQuery(Session session, int start, int limit) {
		Query<Product> query = createQuery(session);
		query.setFirstResult(start);
		query.setMaxResults(limit);
		return query;
	}

	@Override
	protected int getStartIndex(int start) {
		// TODO Auto-generated method stub
		return start*15;
	}
}
