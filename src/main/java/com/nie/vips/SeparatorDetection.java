package com.nie.vips;

import java.util.ArrayList;
import java.util.List;

import static com.nie.rule.SeparatoRule.*;
import com.nie.vo.BlockVo;
import com.nie.vo.SeparatorVo;

public class SeparatorDetection {

	private int width;
	private int height;
	private int type=0;
	private List<SeparatorVo> list=null;
	
	public SeparatorDetection(int width, int height) {
		super();
		list=new ArrayList<>();
		this.width=width;
		this.height=height;
	}

	public List<SeparatorVo> service(List<BlockVo> blocks,int type) {
		this.type=type;
		list.clear();
		onsStep();
		twoStep(blocks);
		threeStep();
		System.out.println(type+"-SeparatorVo.list.size::"+list.size());
		return list;
	}
	
	/**
	 * Initialize the separator list. The list starts with only one separator (P be , P ee ) whose
	 * start pixel and end pixel are corresponding to the borders of the pool.
	 */
	private void onsStep() {
		SeparatorVo separator=new SeparatorVo(0,0,width,height,type);
		list.add(separator);
	}

	/**
	 * For every block in the pool, the relation of the block with each separator is evaluated
	 * a) If the block is contained in the separator, split the separator;
	 * b) If the block crosses with the separator, update the separator’s parameters;
	 * c) If the block covers the separator, remove the separator.
	 * @param blocks
	 */
	private void twoStep(List<BlockVo> blocks) {
		if (list.size()>0) {
			if (type == SeparatorVo.TYPE_HORIZ) {
				horizontalDetection(blocks);
			} else {
				verticalDetection2(blocks);
//				verticalDetection(blocks);
			}
		} 
	}

	/**
	 * Remove the four separators that stand at the border of the pool
	 */
	private void threeStep() {
		List<SeparatorVo> temp=new ArrayList<>();
		temp.addAll(list);
		if (type==SeparatorVo.TYPE_HORIZ) {
			for (SeparatorVo sep : temp) {
				if (sep.getX()==0&&(sep.getY()==0||sep.getY()+sep.getHeight()==height)) {
					list.remove(sep);
				}
			}
		}else {
			for (SeparatorVo sep : temp) {
				if (sep.getY()==0&&(sep.getX()==0||sep.getX()+sep.getWidth()==width)) {
					list.remove(sep);
				}
			}
		}
	}

	/**
	 * 检测水平分隔符
	 * @param block
	 */
	private void horizontalDetection(List<BlockVo> blocks) {
		for (BlockVo block:blocks) {
			List<SeparatorVo> temp = new ArrayList<>();
			temp.addAll(list);
			for (SeparatorVo sep : temp) {
				if (horizontalRule1(block, sep)) {
					int y = block.getY() + block.getHeight();
					SeparatorVo newSep = new SeparatorVo(0, y, width, (sep.getY() + sep.getHeight()) - y, type);
					if (newSep.getHeight() != 0) {
						newSep.setOneSide(block);
						list.add(newSep);
					}

					SeparatorVo separator = list.get(list.indexOf(sep));
					separator.setHeight(block.getY() - separator.getY());
					if (separator.getHeight() == 0) {
						list.remove(separator);
					} else {
						separator.setOtherSide(block);
					}

				} else if (horizontalRule2(block, sep)) {
					list.remove(sep);
				} else if (horizontalRule3(block, sep)) {
					SeparatorVo separator = list.get(list.indexOf(sep));
					int originalY = separator.getY();
					separator.setY(block.getY() + block.getHeight());
					separator.setHeight(separator.getHeight() + originalY - separator.getY());
					separator.setOneSide(block);
				} else if (horizontalRule4(block, sep)) {
					SeparatorVo separator = list.get(list.indexOf(sep));
					separator.setHeight(block.getY() - separator.getY());
					separator.setOtherSide(block);
				} else {
					continue;
				}
			} 
		}
	}
	
	/**
	 * 检测垂直分隔符
	 * @param block
	 */
//	private void verticalDetection(List<BlockVo> blocks) {
//		for (BlockVo block:blocks) {
//			List<SeparatorVo> temp = new ArrayList<>();
//			temp.addAll(list);
//			for (SeparatorVo sep : temp) {
//				if (verticalRule1(block, sep)) {
//					int x = block.getX() + block.getWidth();
//					SeparatorVo newSep = new SeparatorVo(x, 0, (sep.getX() + sep.getWidth()) - x, height, type);
//					if (newSep.getWidth() != 0) {
//						newSep.setOneSide(block);
//						list.add(newSep);
//					}
//
//					SeparatorVo separator = list.get(list.indexOf(sep));
//					separator.setWidth(block.getX() - separator.getX());
//					if (separator.getWidth() == 0) {
//						list.remove(separator);
//					} else {
//						separator.setOtherSide(block);
//					}
//
//				} else if (verticalRule2(block, sep)) {
//					int boxRBY = block.getY() + block.getHeight();
//					int sepRBY = sep.getY() + sep.getHeight();
//					SeparatorVo newSep = new SeparatorVo(sep.getX(), boxRBY, sep.getWeight(), sepRBY - boxRBY, type);
//					if (newSep.getHeight() > 100) {
//						list.add(newSep);
//					}
//
//					sep.setHeight(block.getY() - sep.getY());
//					if (sep.getHeight() <=100) {
//						list.remove(sep);
//					}
//				} else if (verticalRule3(block, sep)) {
//					SeparatorVo separator = list.get(list.indexOf(sep));
//					int originalX = separator.getX();
//					separator.setX(block.getX() + block.getWidth());
//					separator.setWidth(originalX + separator.getWidth() - separator.getX());
//					separator.setOneSide(block);
//				} else if (verticalRule4(block, sep)) {
//					SeparatorVo separator = list.get(list.indexOf(sep));
//					separator.setWidth(block.getX() - separator.getX());
//					separator.setOtherSide(block);
//				} else {
//					continue;
//				}
//			} 
//		}
//	}
//	
	/**
	 * 检测垂直分隔符
	 * @param block
	 */
	private void verticalDetection2(List<BlockVo> blocks) {
		list.clear();
		for (BlockVo b1 : blocks) {
			
			int leftMinW=Integer.MAX_VALUE;
			int leftX=0;
			int leftY=0;
			int leftW=0;
			int leftH=0;
			
			int rightMinW=Integer.MAX_VALUE;
			int rightX=0;
			int rightY=0;
			int rightW=0;
			int rightH=0;
			for (BlockVo b2 : blocks) {
				if (b1==b2) {
					continue;
				}
				//两个块在x值域上没交集,y的值域上有交集
				int RBX1=b1.getX()+b1.getWidth();
				int RBX2=b2.getX()+b2.getWidth();
				int RBY1=b1.getY()+b1.getHeight();
				int RBY2=b2.getY()+b2.getHeight();
				//2在1左侧
				if (RBX2<b1.getX()) {
					int X=b2.getX()+b2.getWidth();
					int W=b1.getX()-X;
					
					SeparatorVo sep=new SeparatorVo(0,0,b1.getX(),b1.getHeight(),SeparatorVo.TYPE_HORIZ);
					if (horizontalRule1(b2, sep)) {
						if (W<leftMinW) {
							leftMinW=W;
							leftX=X;
							leftY=b1.getY();
							leftW=W;
							leftH=b1.getHeight();
						}
					} else if (horizontalRule2(b2, sep)) {
						if (W<leftMinW) {
							leftMinW=W;
							leftX=X;
							leftY=b2.getY();
							leftW=W;
							leftH=b2.getHeight();
						}
					} else if (horizontalRule3(b2, sep)) {
						if (W<leftMinW) {
							leftMinW=W;
							leftX=X;
							leftY=b2.getY();
							leftW=W;
							leftH=RBY1-b2.getY();
						}
					} else if (horizontalRule4(b2, sep)) {
						if (W<leftMinW) {
							leftMinW=W;
							leftX=X;
							leftY=b1.getY();
							leftW=W;
							leftH=RBY2-b1.getY();
						}
					}
				}
				//2在1右侧
				else if (b2.getX()>RBX1) {
					int X=b1.getX()+b1.getWidth();
					int W=b2.getX()-X;
					SeparatorVo sep=new SeparatorVo(b1.getX()+b1.getWidth(),b1.getY(),width,height,SeparatorVo.TYPE_HORIZ);
					if (horizontalRule1(b2, sep)) {
						if (W<rightMinW) {
							rightMinW=W;
							rightX=X;
							rightY=b1.getY();
							rightW=W;
							rightH=b1.getHeight();
						}
					} else if (horizontalRule2(b2, sep)) {
						if (W<rightMinW) {
							rightMinW=W;
							rightX=X;
							rightY=b2.getY();
							rightW=W;
							rightH=b2.getHeight();
						}
					} else if (horizontalRule3(b2, sep)) {
						if (W<rightMinW) {
							rightMinW=W;
							rightX=X;
							rightY=b2.getY();
							rightW=W;
							rightH=RBY1-b2.getY();
						}
					} else if (horizontalRule4(b2, sep)) {
						if (W<rightMinW) {
							rightMinW=W;
							rightX=X;
							rightY=b1.getY();
							rightW=W;
							rightH=RBY2-b1.getY();
						}
					}
				}
			}
			if (leftMinW<Integer.MAX_VALUE) {
				SeparatorVo separator=new SeparatorVo(leftX,leftY,leftW,leftH,SeparatorVo.TYPE_VERTICAL);
				list.add(separator);
			}
			if (rightMinW<Integer.MAX_VALUE) {
				SeparatorVo separator=new SeparatorVo(rightX,rightY,rightW,rightH,SeparatorVo.TYPE_VERTICAL);
				list.add(separator);
			}
		}
		mergeSeparator();
	}
	
	private void mergeSeparator() {
		List<SeparatorVo> temp1=new ArrayList<>();
		temp1.addAll(list);
		for (int i=0;i<temp1.size();i++) {
			SeparatorVo sep1=temp1.get(i);
			for (int j = i+1; j < temp1.size(); j++) {
				SeparatorVo sep2=temp1.get(j);
				if (sep1.equals(sep2)&&(Math.abs(sep1.getY()-sep2.getY())<100)) {
					if (sep2.getY() > sep1.getY()) {
						sep1.setY(sep1.getY());
						sep1.setHeight(Math.abs(sep1.getY() - sep2.getY()) + sep2.getHeight());
					} else if (sep2.getY() < sep1.getY()) {
						sep1.setY(sep2.getY());
						sep1.setHeight(Math.abs(sep1.getY() - sep2.getY()) + sep1.getHeight());
					}
//					list.remove(sep2);
				} 
			}
		}
	}
}
