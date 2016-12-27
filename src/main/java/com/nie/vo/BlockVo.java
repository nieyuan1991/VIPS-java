package com.nie.vo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.fit.cssbox.layout.Box;

public class BlockVo {

	private String id;
	private int x;
	private int y;
	private int width;
	private int height;
	private List<Box> boxs = null;
	private BlockVo parent = null;
	private List<BlockVo> children = null;
	private boolean isVisualBlock = true;
	private boolean isDividable = true;
	private int DoC=0;
	
	public BlockVo() {
		super();
		setId(UUID.randomUUID().toString());
		boxs=new ArrayList<>();
		children = new ArrayList<>();
	}
	
	public void refresh() {
		for (int i=0;i<boxs.size();i++) {
			Box box =boxs.get(i);
			if (i==0) {
				x=box.getAbsoluteContentX();
				y=box.getAbsoluteContentY();
				width=box.getAvailableWidth();
				height=box.getContentHeight();
			}else {
				int RBX=x+width;
				int RBY=y+height;
				int boxRBX=box.getAbsoluteContentX()+box.getAvailableWidth();
				int boxRBY=box.getAbsoluteContentY()+box.getContentHeight();
				RBX=boxRBX>RBX?boxRBX:RBX;
				RBY=boxRBY>RBY?boxRBY:RBY;
				x=box.getAbsoluteContentX()<x?box.getAbsoluteContentX():x;
				y=box.getAbsoluteContentY()<y?box.getAbsoluteContentY():y;
				width=RBX-x;
				height=RBY-y;
			}
		}
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public List<Box> getBoxs() {
		return boxs;
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
		return "{is:" + isVisualBlock +",ch:" + children +  "}";
	}
}
