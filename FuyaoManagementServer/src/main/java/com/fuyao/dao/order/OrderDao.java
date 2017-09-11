package com.fuyao.dao.order;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.fuyao.model.order.Order;
import com.fuyao.page.CommonPage;
import com.fuyao.service.order.OrderService.OrderStatus;

@Repository("orderDao")
public class OrderDao implements IOrderDao {
	
	@Resource
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	private Session getCurrentSession() {
		return this.sessionFactory.getCurrentSession();
	}

	public long getOrderCount() {
		// TODO Auto-generated method stub
		String hql = "select count(id) from Order";
		Query<Long> query = this.getCurrentSession().createQuery(hql,Long.class);
		return query.getSingleResult();
	}
	
	public long getOrderCountByStatus(OrderStatus s) {
		// TODO Auto-generated method stub
		String hql = "select count(id) from Order where status=:status";
		Query<Long> query = this.getCurrentSession().createQuery(hql,Long.class);
		query.setParameter("status", s.getStatus());
		return query.getSingleResult();
	}
	
	public long getOrderCountById(OrderStatus s, String orderId) {
		// TODO Auto-generated method stub
		String hql = "select count(id) from Order where status=:status and orderId=:orderId";
		Query<Long> query = this.getCurrentSession().createQuery(hql,Long.class);
		query.setParameter("status", s.getStatus());
		query.setParameter("orderId", orderId);
		return query.getSingleResult();
	}
	
	public long getOrderCountByName(OrderStatus s, String name) {
		// TODO Auto-generated method stub
		String hql = "select count(id) from Order where status=:status and receiver=:receiver";
		Query<Long> query = this.getCurrentSession().createQuery(hql,Long.class);
		query.setParameter("status", s.getStatus());
		query.setParameter("receiver", name);
		return query.getSingleResult();
	}
	
	public long getOrderCountByTel(OrderStatus s, String tel) {
		// TODO Auto-generated method stub
		String hql = "select count(id) from Order where status=:status and phone=:phone";
		Query<Long> query = this.getCurrentSession().createQuery(hql,Long.class);
		query.setParameter("status", s.getStatus());
		query.setParameter("phone", tel);
		return query.getSingleResult();
	}
	
	public long getOrderCountByDate(OrderStatus s, Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		String hql = "select count(id) from Order where status=:status and date>=:startDate and date<=:endDate";
		Query<Long> query = this.getCurrentSession().createQuery(hql,Long.class);
		query.setParameter("status", s.getStatus());
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		return query.getSingleResult();
	}

	public List<Order> getOrderList(OrderStatus status, int start, int limit) {
		// TODO Auto-generated method stub
		String hql = null;
		Query<Order> query = null;
		CommonPage page = new CommonPage();
		switch(status) {
			case CANCEL:
			case WAITPAY:
			case WAITSEND:
			case WAITRECEIVE:
			case COMPLETE:
				hql = "from Order where status=:status order by uid";
				query = page.createQuery(this.getCurrentSession(), hql, start, limit);
				query.setParameter("status", status.getStatus());
				break;
			case ALL:
				hql = "from Order order by uid";
				query = page.createQuery(this.getCurrentSession(), hql, start, limit);
				break;
			default:
				hql = "from Order order by uid";
				query = page.createQuery(this.getCurrentSession(), hql, start, limit);
				break;
		}
		return query.getResultList();
	}

	public HashMap<String, String> changeOrderStatus(long orderId, String status) {
		// TODO Auto-generated method stub
		HashMap<String, String> result = new HashMap<String, String>();
		String hql = "update Order set order_status=:status where id=:id";
		Query<?> query = this.getCurrentSession().createQuery(hql);
		query.setParameter("status", status);
		query.setParameter("id", orderId);
		int updateCount = query.executeUpdate();
		if (updateCount == 1) {
			result.put("result", "success");
			result.put("message", "更新成功");
		} else {
			result.put("result", "fault");
			result.put("message", "更新失败");
		}
		return result;
	}

	public List<Order> getOrderListById(OrderStatus status, int start, int limit, String id) {
		// TODO Auto-generated method stub
		String hql = null;
		Query<Order> query = null;
		CommonPage page = new CommonPage();
		hql = "from Order where status=:status and orderId=:orderId";
		query = page.createQuery(this.getCurrentSession(), hql, start, limit);
		query.setParameter("status", status.getStatus());
		query.setParameter("orderId", id);
		return query.getResultList();
	}

	public List<Order> getOrderListByReceiver(OrderStatus status, int start, int limit, String receiver) {
		// TODO Auto-generated method stub
		String hql = null;
		Query<Order> query = null;
		CommonPage page = new CommonPage();
		hql = "from Order where status=:status and receiver=:receiver";
		query = page.createQuery(this.getCurrentSession(), hql, start, limit);
		query.setParameter("status", status.getStatus());
		query.setParameter("receiver", receiver);
		return query.getResultList();
	}

	public List<Order> getOrderListByTel(OrderStatus status, int start, int limit, String tel) {
		// TODO Auto-generated method stub
		String hql = null;
		Query<Order> query = null;
		CommonPage page = new CommonPage();
		hql = "from Order where status=:status and tel=:tel";
		query = page.createQuery(this.getCurrentSession(), hql, start, limit);
		query.setParameter("status", status.getStatus());
		query.setParameter("tel", tel);
		return query.getResultList();
	}

	public List<Order> getOrderListByDate(OrderStatus status, int start, int limit, Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		String hql = null;
		Query<Order> query = null;
		CommonPage page = new CommonPage();
		hql = "from Order where status=:status and date<=:endDate and date>=:startDate";
		query = page.createQuery(this.getCurrentSession(), hql, start, limit);
		query.setParameter("status", status.getStatus());
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		return query.getResultList();
	}
}
