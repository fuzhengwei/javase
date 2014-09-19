package com.stack.bean;

public class PageBean {

	private int firstResult = 1; 
	private int maxResult = 10; 

	public PageBean(){
		
	}
	
	public PageBean(int firstResult){
		this.firstResult = firstResult;
	}
	
	public PageBean(int firstResult,int maxResult){
		this.firstResult = firstResult;
		this.maxResult = maxResult;
	}
	
	public int getFirstResult() {
		if(firstResult > 1){
			return --firstResult * maxResult;
		}else{
			return 0;
		}
	}

	public void setFirstResult(int firstResult) {
		this.firstResult = firstResult;
	}

	public int getMaxResult() {
		if(firstResult > 1){
			return maxResult + --firstResult * maxResult;
		}else{
			return maxResult;
		}
	}

	public void setMaxResult(int maxResult) {
		this.maxResult = maxResult;
	}

}
