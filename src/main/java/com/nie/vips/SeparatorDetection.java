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
		System.out.println("SeparatorVo.list.size::"+list.size());
//		System.out.println(list);
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
		for (BlockVo block:blocks) {
			if (block.isVisualBlock()&&list.size()>0) {
				if (type == SeparatorVo.TYPE_HORIZ) {
					horizontalDetection(block);
				} else {
					verticalDetection(block);
				}
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
	private void horizontalDetection(BlockVo block) {
		List<SeparatorVo> temp=new ArrayList<>();
		temp.addAll(list);
		for (SeparatorVo sep : temp) {
			if (horizontalRule1(block, sep)) {
				int y=block.getY()+block.getHeight();
				SeparatorVo newSep=new SeparatorVo(0,y,width,(sep.getY()+sep.getHeight())-y,type);
				if (newSep.getHeight()!=0) {
					newSep.setOneSide(block);
					list.add(newSep);
				}
				
				SeparatorVo separator=list.get(list.indexOf(sep));
				separator.setHeight(block.getY()-separator.getY());
				if (separator.getHeight()==0) {
					list.remove(separator);
				}else {
					separator.setOtherSide(block);
				}
				
			}else if (horizontalRule2(block, sep)) {
				list.remove(sep);
			}else if (horizontalRule3(block, sep)) {
				SeparatorVo separator=list.get(list.indexOf(sep));
				int originalY=separator.getY();
				separator.setY(block.getY()+block.getHeight());
				separator.setHeight(separator.getHeight()+originalY-separator.getY());
				separator.setOneSide(block);
			}else if (horizontalRule4(block, sep)) {
				SeparatorVo separator=list.get(list.indexOf(sep));
				separator.setHeight(block.getY()-separator.getY());
				separator.setOtherSide(block);
			}else {
				continue;
			}
		}
	}
	
	/**
	 * 检测垂直分隔符
	 * @param block
	 */
	private void verticalDetection(BlockVo block) {
		List<SeparatorVo> temp=new ArrayList<>();
		temp.addAll(list);
		for (SeparatorVo sep : temp) {
			if (verticalRule1(block, sep)) {
				int x=block.getX()+block.getWidth();
				SeparatorVo newSep=new SeparatorVo(x,0,(sep.getX()+sep.getWidth())-x,height,type);
				if (newSep.getWidth()!=0) {
					newSep.setOneSide(block);
					list.add(newSep);
				}
				
				SeparatorVo separator=list.get(list.indexOf(sep));
				separator.setWidth(block.getX()-separator.getX());
				if (separator.getWidth()==0) {
					list.remove(separator);
				}else {
					separator.setOtherSide(block);
				}
				
			}else if (verticalRule2(block, sep)) {
				list.remove(sep);
			}else if (verticalRule3(block, sep)) {
				SeparatorVo separator=list.get(list.indexOf(sep));
				int originalX=separator.getX();
				separator.setX(block.getX()+block.getWidth());
				separator.setWidth(originalX+separator.getWidth()-separator.getX());
				separator.setOneSide(block);
			}else if (verticalRule4(block, sep)) {
				SeparatorVo separator=list.get(list.indexOf(sep));
				separator.setWidth(block.getX()-separator.getX());
				separator.setOtherSide(block);
			}else {
				continue;
			}
		}
	}
	
}
