package com.fuyao.dao.order;

import java.util.List;

import com.fuyao.model.order.Order;
import com.fuyao.service.order.OrderService.OrderStatus;

public interface IOrderDao {
	long getOrderCount(long uid);
	List<Order> getOrderList(OrderStatus status, int start, int limit);
}
