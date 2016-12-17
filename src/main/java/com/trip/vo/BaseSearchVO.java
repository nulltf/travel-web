package com.trip.vo;

public class BaseSearchVO {

	
	private int    start  = 0;
	
	private int    size   = 0;
	
	private int    page   = 0;
	
	private int    displayRecord = 0;
	
	public int getStart() {
		if(page<=0)
			page = 1;
		start = (page-1)*displayRecord;
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getSize() {
		if(size<=0){
			if(displayRecord<=0)
				displayRecord = 20;
			size = displayRecord;
		}
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getDisplayRecord() {
		if(displayRecord<=0)
			displayRecord = 20;		
		return displayRecord;
	}
	public void setDisplayRecord(int displayRecord) {
		this.displayRecord = displayRecord;
	}
}
