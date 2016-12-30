package com.nie.vo;

public class SeparatorVo implements Comparable<SeparatorVo>{

	public static final int TYPE_HORIZ=1;
	public static final int TYPE_VERTICAL=2;
	
	private int x=0;
	private int y=0;
	private int width=0;
	private int height=0;
	private int weight=7;
	private int type=0;
	private BlockVo oneSide;		//上边or左边
	private BlockVo otherSide;		//下边or右边
	
	public SeparatorVo(int x, int y, int width, int height, int type) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.type = type;
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
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	public BlockVo getOneSide() {
		return oneSide;
	}

	public void setOneSide(BlockVo oneSide) {
		this.oneSide = oneSide;
	}

	public BlockVo getOtherSide() {
		return otherSide;
	}

	public void setOtherSide(BlockVo otherSide) {
		this.otherSide = otherSide;
	}

	@Override
	public int compareTo(SeparatorVo o) {
		return getWeight()-o.getWeight();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof SeparatorVo) {
			SeparatorVo sep=(SeparatorVo) obj;
			if (sep.getType()==SeparatorVo.TYPE_VERTICAL) {
				if (sep.getX()==x&&sep.getWidth()==width) {
					return true;
				}
			}
		}
		return super.equals(obj);
	}

	@Override
	public String toString() {
		return "SeparatorVo [x=" + x + ", y=" + y + ", width=" + width + ", height=" + height + ", weight=" + weight
				+ ", type=" + type + "]";
	}
	
}
