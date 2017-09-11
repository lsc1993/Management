package com.fuyao.dao.order;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.fuyao.model.order.Order;
import com.fuyao.service.order.OrderService.OrderStatus;

public interface IOrderDao {
	long getOrderCount();
	long getOrderCountByStatus(OrderStatus s);
	long getOrderCountById(OrderStatus s, String orderId);
	long getOrderCountByName(OrderStatus s, String name);
	long getOrderCountByTel(OrderStatus s, String tel);
	long getOrderCountByDate(OrderStatus s, Date startDate, Date endDate);
	List<Order> getOrderList(OrderStatus status, int start, int limit);
	HashMap<String,String> changeOrderStatus(long orderId, String status);
	List<Order> getOrderListById(OrderStatus status, int start, int limit, String id);
	List<Order> getOrderListByReceiver(OrderStatus status, int start, int limit, String receiver);
	List<Order> getOrderListByTel(OrderStatus status, int start, int limit, String tel);
	List<Order> getOrderListByDate(OrderStatus status, int start, int limit, Date startDate, Date endDate);
}
