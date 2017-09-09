package com.fuyao.dao.login;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.fuyao.model.login.ManagerLogin;

@Repository("managerLoginDao")
public class ManagerLoginDao implements IManagerLoginDao {
	
	@Resource
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	public ManagerLogin login(ManagerLogin manager) {
		// TODO Auto-generated method stub
		String hql = "from ManagerLogin where account=:account and password=:password";
		Query<ManagerLogin> query = this.getCurrentSession().createQuery(hql,ManagerLogin.class);
		query.setParameter("account", manager.getAccount());
		query.setParameter("password", manager.getPassword());
		if(query.getResultList().size() == 1) {
			return query.getResultList().get(0);
		} else {
			return null;
		}
	}

}
