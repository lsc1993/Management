package com.fuyao.dao.login;

import java.util.HashMap;

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

	public HashMap<String, String> login(ManagerLogin manager) {
		// TODO Auto-generated method stub
		HashMap<String,String> result = new HashMap<String,String>();
		String hql = "from ManagerLogin where account=:account and password=:password";
		Query<ManagerLogin> query = this.getCurrentSession().createQuery(hql,ManagerLogin.class);
		query.setParameter("account", manager.getAccount());
		query.setParameter("password", manager.getPassword());
		if(query.getResultList().size() == 1) {
			result.put("result", "success");  
		} else {
			result.put("result", "fault");
		}
		return result;
	}

}
