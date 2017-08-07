package com.fuyao.model.product;

import java.util.ArrayList;

public class ProductImages {
	private long id;
	private String pId;
	private ArrayList<String> images;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	public ArrayList<String> getImages() {
		return images;
	}
	public void setImages(ArrayList<String> images) {
		this.images = images;
	}
}
