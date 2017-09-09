package com.fuyao.page;

import org.hibernate.Session;
import org.hibernate.query.Query;

public class CommonPage extends Page {

	@SuppressWarnings("unchecked")
	@Override
	public <T> Query<T> createQuery(Session session, String hql) {
		// TODO Auto-generated method stub
		return session.createQuery(hql);
	}

	@Override
	protected int getStartIndex(int start, int limit) {
		// TODO Auto-generated method stub
		return start * limit;
	}
	
	public <T> Query<T> createQuery(Session session, String hql, int start, int limit) {
		Query<T> query = createQuery(session, hql);
		query.setFirstResult(getStartIndex(start,limit));
		query.setMaxResults(limit);
		return query;
	}

}
