package com.nie.vo;

public class SeparatorVo implements Comparable<SeparatorVo>{

	private int start=0;
	private int end=0;
	private int weight=0;
	
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	@Override
	public int compareTo(SeparatorVo o) {
		return getWeight()-o.getWeight();
	}
	
}
