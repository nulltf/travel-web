package com.trip.vo;

public class BlogSearchVo extends BaseSearchVO {
	
	private String content ;
	
	private int id ;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
