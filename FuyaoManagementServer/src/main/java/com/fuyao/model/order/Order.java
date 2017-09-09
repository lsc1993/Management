package com.fuyao.model.order;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user_order")
public class Order {
	
	private long id;
	private String orderId;
	private long uid;
	private String pid;
	private long sid;
	private long aid;
	private String name;
	private String imgurl;
	private float total;
	private float sendCost;
	private float pTotal;
	private int pCount;
	private String standard;
	private String discount;
	private String buyerMsg;
	private String sendWay;
	private String receiver;
	private String phone;
	private String address;
	private String postcode;
	private Date date;
	private String status;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id",nullable=false,length=11)
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	@Column(name="order_id",nullable=false,length=32)
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	@Column(name="uid",nullable=false,length=11)
	public long getUid() {
		return uid;
	}
	public void setUid(long uid) {
		this.uid = uid;
	}
	
	@Column(name="pid",nullable=false,length=32)
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	
	@Column(name="sid",nullable=false,length=11)
	public long getSid() {
		return sid;
	}
	public void setSid(long sid) {
		this.sid = sid;
	}
	
	@Column(name="aid",nullable=false,length=11)
	public long getAid() {
		return aid;
	}
	public void setAid(long aid) {
		this.aid = aid;
	}
	
	@Column(name="pname",nullable=false,length=48)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name="imgurl",nullable=false,length=128)
	public String getImgurl() {
		return imgurl;
	}
	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}
	
	@Column(name="total",nullable=false)
	public float getTotal() {
		return total;
	}
	public void setTotal(float total) {
		this.total = total;
	}
	@Column(name="send_cost",nullable=false,length=24)
	public float getSendCost() {
		return sendCost;
	}
	public void setSendCost(float sendCost) {
		this.sendCost = sendCost;
	}
	
	@Column(name="ptotal",nullable=false)
	public float getPTotal() {
		return pTotal;
	}
	public void setPTotal(float pTotal) {
		this.pTotal = pTotal;
	}
	
	@Column(name="pcount",nullable=false)
	public int getPCount() {
		return pCount;
	}
	public void setPCount(int count) {
		this.pCount = count;
	}
	
	@Column(name="standard",nullable=false,length=32)
	public String getStandard() {
		return standard;
	}
	public void setStandard(String standard) {
		this.standard = standard;
	}
	
	@Column(name="discount",nullable=true)
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	
	@Column(name="buyer_msg",nullable=true)
	public String getBuyerMsg() {
		return buyerMsg;
	}
	public void setBuyerMsg(String buyerMsg) {
		this.buyerMsg = buyerMsg;
	}
	
	@Column(name="send_way",nullable=false,length=30)
	public String getSendWay() {
		return sendWay;
	}
	public void setSendWay(String sendWay) {
		this.sendWay = sendWay;
	}
	
	@Column(name="receiver",nullable=false,length=30)
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	
	@Column(name="phone",nullable=false,length=25)
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Column(name="address",nullable=false,length=80)
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	@Column(name="postcode",nullable=true,length=10)
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	
	@Column(name="order_time",nullable=false)
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	@Column(name="order_status",nullable=false,length=10)
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
