package com.fuyao.model.product;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="product_standards")
public class ProductStandard {
	private long id;
	private String Standard;
	private long pId;
	private float price;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id",nullable=false,length=11)
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	@Column(name="standard",nullable=false,length=32)
	public String getStandard() {
		return Standard;
	}
	public void setStandard(String standard) {
		Standard = standard;
	}
	
	@Column(name="pid",nullable=false,length=11)
	public long getpId() {
		return pId;
	}
	public void setpId(long pId) {
		this.pId = pId;
	}
	
	@Column(name="price",nullable=false)
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
}
