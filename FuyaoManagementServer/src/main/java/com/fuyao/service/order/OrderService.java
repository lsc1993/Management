package com.fuyao.service.order;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
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
	
	public JSON getOrderList(HttpServletRequest request) {
		int start,limit;
		try {
			start = Integer.parseInt(request.getParameter("start"));
		} catch (NumberFormatException e) {
			start = 0;
			e.printStackTrace();
		}
		try {
			limit = Integer.parseInt(request.getParameter("limit"));
		} catch (NumberFormatException e) {
			limit = 10;
			e.printStackTrace();
		}
		
		OrderStatus s = OrderStatus.valueOf(request.getParameter("status"));
		Log.log(s.getStatus() + start + " " + limit);
		List<Order> orderList = orderDao.getOrderList(s, start, limit);
		long length = orderDao.getOrderCountByStatus(s);
		StringBuilder builder = new StringBuilder();
		builder.append("{").append("\"rows\":").
				append(JSON.toJSONString(orderList, SerializerFeature.WriteDateUseDateFormat)).
				append(",").append("\"results\":").append(length).
				append("}");
		Log.log("orderList:" + builder.toString());
		return (JSON) JSON.parse(builder.toString());
	}
	
	public HashMap<String,String> changeOrderStatus(HashMap<String,String> data) {
		HashMap<String,String> result = new HashMap<String,String>();
		long id;
		try {
			id = Long.parseLong(data.get("id"));
		} catch (NumberFormatException e) {
			e.printStackTrace();
			result.put("result", "fault");
			result.put("message", "没有订单记录");
			return result;
		}
		
		OrderStatus s = OrderStatus.valueOf(data.get("status"));
		String status = s.getStatus();
		return orderDao.changeOrderStatus(id, status);
	}
	
	public JSON getOrderListByFind(HttpServletRequest request) {
		int start,limit;
		try {
			start = Integer.parseInt(request.getParameter("start"));
		} catch (NumberFormatException e) {
			start = 0;
			e.printStackTrace();
		}
		try {
			limit = Integer.parseInt(request.getParameter("limit"));
		} catch (NumberFormatException e) {
			limit = 10;
			e.printStackTrace();
		}
		OrderStatus s = OrderStatus.valueOf(request.getParameter("status"));
		Log.log(s.getStatus() + start + " " + limit);
		
		
		StringBuilder builder = new StringBuilder();
		String orderId = request.getParameter("orderId");
		String receiver = request.getParameter("receiver");
		String tel = request.getParameter("tel");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		startDate += " 00:00:00";
		endDate += " 23:59:59";
		Log.log(orderId + " " + receiver + " " + tel + " " + startDate + " " + endDate);
		long length = 0;
		List<Order> orderList = null;
		if(!"".equals(orderId) && null != orderId) {
			orderList = orderDao.getOrderListById(s, start, limit, orderId);
			length = orderDao.getOrderCountById(s, orderId);
		} else if (!"".equals(receiver) && null != receiver) {
			orderList = orderDao.getOrderListByReceiver(s, start, limit, receiver);
			length = orderDao.getOrderCountByName(s, receiver);
		} else if (!"".equals(tel) && null != tel) {
			orderList = orderDao.getOrderListByReceiver(s, start, limit, tel);
			length = orderDao.getOrderCountByName(s, tel);
		} else {
			try {
				orderList = orderDao.getOrderListByDate(s, start, limit, sdf.parse(startDate), sdf.parse(endDate));
				length = orderDao.getOrderCountByDate(s, sdf.parse(startDate.toString()), sdf.parse(endDate.toString()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				length = 0;
				orderList = new ArrayList<Order>();
			}
			
		}
		
		builder.append("{").append("\"rows\":").
		append(JSON.toJSONString(orderList, SerializerFeature.WriteDateUseDateFormat)).
		append(",").append("\"results\":").append(length).
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
