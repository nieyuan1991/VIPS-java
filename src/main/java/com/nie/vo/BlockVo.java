package com.nie.vo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.fit.cssbox.layout.Box;

public class BlockVo {

	private String id;
	private Box box = null;
	private BlockVo parent = null;
	private List<BlockVo> children = null;
	private boolean isVisualBlock = true;
	private boolean isDividable = true;
	private int DoC=0;
	
	public BlockVo() {
		super();
		setId(UUID.randomUUID().toString());
		children = new ArrayList<>();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Box getBox() {
		return box;
	}
	public void setBox(Box box) {
		this.box = box;
	}
	public BlockVo getParent() {
		return parent;
	}
	public void setParent(BlockVo parent) {
		this.parent = parent;
	}
	public List<BlockVo> getChildren() {
		return children;
	}
	public void setChildren(List<BlockVo> children) {
		this.children = children;
	}
	public boolean isVisualBlock() {
		return isVisualBlock;
	}
	public void setVisualBlock(boolean isVisualBlock) {
		this.isVisualBlock = isVisualBlock;
	}
	public int getDoC() {
		return DoC;
	}
	public void setDoC(int doC) {
		DoC = doC;
	}
	public boolean isDividable() {
		return isDividable;
	}
	public void setDividable(boolean isDividable) {
		this.isDividable = isDividable;
	}
	@Override
	public String toString() {
		return "{children:" + children + ", isVisualBlock:" + isVisualBlock + ", DoC:"+DoC+"}";
	}
	
}
