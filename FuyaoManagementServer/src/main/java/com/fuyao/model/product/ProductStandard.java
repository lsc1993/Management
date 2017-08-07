package com.fuyao.model.product;

import java.util.ArrayList;

public class ProductStandard {
	private long id;
	private String pId;
	private ArrayList<String> standards;
	
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
	public ArrayList<String> getStandards() {
		return standards;
	}
	public void setStandards(ArrayList<String> standards) {
		this.standards = standards;
	}
}
