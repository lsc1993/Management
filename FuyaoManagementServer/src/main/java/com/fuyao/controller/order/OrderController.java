package com.fuyao.controller.order;

import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.fuyao.service.order.OrderService;

@Controller
@RequestMapping("/order")
public class OrderController {
	
	@Resource
	private OrderService orderService;

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}
	
	@ResponseBody
	@RequestMapping(value="/list",method=RequestMethod.POST)
	private JSON getOrderList(HttpServletRequest request) {
		return orderService.getOrderList(request);
	}
	
	@ResponseBody
	@RequestMapping(value="/change_status",method=RequestMethod.POST)
	private HashMap<String,String> changeOrderStatus(@RequestBody HashMap<String,String> data) {
		return orderService.changeOrderStatus(data);
	}
	
	@ResponseBody
	@RequestMapping(value="/find",method=RequestMethod.POST)
	private JSON findOrderList(HttpServletRequest request) {
		return orderService.getOrderListByFind(request);
	}
}
