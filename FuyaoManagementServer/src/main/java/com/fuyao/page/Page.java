package com.fuyao.page;

import org.hibernate.Session;
import org.hibernate.query.Query;

public abstract class Page {
	abstract public <T> Query<T> createQuery(Session session, String hql);
	abstract protected int getStartIndex(int start, int limit);
}
