package com.fuyao.model.product;

import java.util.ArrayList;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="product")
public class Product {
	private int id;
	private String pId;
	private String name;
	private String type;
	private float price;
	private int count;
	private String describe;
	private ArrayList<ProductStandard> standards;
	private ArrayList<ProductImages> images;
	private Date date;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id",nullable=false,length=11)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name="pid",nullable=false,length=32)
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	
	@Column(name="pname",nullable=false,length=48)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name="ptype",nullable=false,length=20)
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	@Column(name="price",nullable=false)
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	
	@Column(name="count",nullable=false)
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	@Column(name="describe",nullable=false)
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	
	/*@OneToMany(cascade=CascadeType.ALL,mappedBy="product")
	public ArrayList<ProductStandard> getStandards() {
		return standards;
	}
	public void setStandards(ArrayList<ProductStandard> standards) {
		this.standards = standards;
	}
	
	@OneToMany(cascade=CascadeType.ALL,mappedBy="product")
	public ArrayList<ProductImages> getImages() {
		return images;
	}
	public void setImages(ArrayList<ProductImages> images) {
		this.images = images;
	}*/
	
	@Column(name="datatime",nullable=false)
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}     
}
