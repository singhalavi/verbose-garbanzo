package com.avi.redmart;

public class Product {
	private Long pid;
	private Long length;
	private Long width;
	private Long height;
	private Long weight;
	private int price;
	
	private Integer vw = null;
	
	public Long getPid() {
		return pid;
	}
	public void setPid(Long pid) {
		this.pid = pid;
	}
	public Long getLength() {
		return length;
	}
	public void setLength(Long length) {
		this.length = length;
	}
	public Long getWidth() {
		return width;
	}
	public void setWidth(Long width) {
		this.width = width;
	}
	public Long getHeight() {
		return height;
	}
	public void setHeight(Long height) {
		this.height = height;
	}
	public Long getWeight() {
		return weight;
	}
	public void setWeight(Long weight) {
		this.weight = weight;
	}
	public int getVw() {
		if(vw == null){
			vw = (int) (length*width*height);
			//System.out.println("VW = "+vw);
		}
		return vw;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "pid "+pid;
	}
	
}
