package com.developer.sunmaungoo.kmean;

public class Data {
	
	private Double[] itemData;
	
	private int centroidIndex;
	
	public Data(Double[] itemData,int centroidIndex) {
		
		this.itemData = itemData;
		
		this.centroidIndex = centroidIndex;
		
	}
	
	public Double[] getItemData() {
		return itemData;
	}
	
	public int getCentroidIndex() {
		return centroidIndex;
	}
}
