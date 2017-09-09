package com.fuyao.service.order;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.fuyao.dao.order.IOrderDao;
import com.fuyao.model.order.Order;
import com.fuyao.util.Log;

@Transactional
@Service("orderService")
public class OrderService {
	
	@Resource
	private IOrderDao orderDao;

	public void setOrderDao(IOrderDao orderDao) {
		this.orderDao = orderDao;
	}
	
	public JSON getOrderList(HashMap<String,String> data) {
		int start,limit;
		try {
			start = Integer.parseInt(data.get("start"));
		} catch (NumberFormatException e) {
			start = 0;
			e.printStackTrace();
		}
		try {
			limit = Integer.parseInt(data.get("limit"));
		} catch (NumberFormatException e) {
			limit = 10;
			e.printStackTrace();
		}
		
		OrderStatus s = OrderStatus.valueOf(data.get("status"));
		Log.log(s.getStatus());
		List<Order> orderList = orderDao.getOrderList(s, start, limit);
		int length = orderList.size();
		StringBuilder builder = new StringBuilder();
		builder.append("{").append("\"rows\":").
				append(JSON.toJSONString(orderList)).
				append(",").append("\"result\":").append(length).
				append("}");
		Log.log("orderList:" + builder.toString());
		return (JSON) JSON.parse(builder.toString());
	}
	
	public enum OrderStatus{
		WAITPAY("待付款"),CANCEL("已取消"),WAITSEND("待发货"),WAITRECEIVE("待收货"),COMPLETE("交易完成"),ALL("全部订单");
		
		private String status;
		
		private OrderStatus(String status) {
			this.status = status;
		}
		
		public String getStatus() {
			return status;
		}
		
		@Override
		public String toString() {
			return this.status;
		}
	}
}
