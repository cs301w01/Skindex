package com.cs301w01.meatload.model;

public class Tag {
	
	private String name;
	private int pictureCount;
	
	public Tag(String name, int pictureCount) {
		
		this.name = name;
		this.pictureCount = pictureCount;
		
	}
	
	public String getName() {
		
		return this.name;
		
	}
	
	public int getPictureCount() {
		
		return this.pictureCount;
		
	}

}
