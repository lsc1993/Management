package com.fuyao.model.product;

public class Product {
	private String id;
	private String pId;
	private String name;
	private String type;
	private float price;
	private int count;
	private String describe;
	private ProductStandard standard;
	private ProductImages images;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	public ProductStandard getStandard() {
		return standard;
	}
	public void setStandard(ProductStandard standard) {
		this.standard = standard;
	}
	public ProductImages getImages() {
		return images;
	}
	public void setImages(ProductImages images) {
		this.images = images;
	}
}
