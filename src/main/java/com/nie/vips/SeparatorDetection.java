package com.nie.vips;

import java.util.ArrayList;
import java.util.List;

import org.fit.cssbox.layout.Box;

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
		setWidth(width);
		setHeight(height);
	}

	public List<SeparatorVo> service(List<BlockVo> blocks,int type) {
		setType(type);
		onsStep();
		twoStep(blocks);
		threeStep();
		System.out.println("SeparatorVo.list.size::"+list.size());
		System.out.println(list);
		return list;
	}
	
	private void onsStep() {
		SeparatorVo separator=new SeparatorVo(0,0,width,height,type);
		list.add(separator);
	}

	private void twoStep(List<BlockVo> blocks) {
		for (BlockVo block:blocks) {
			if (block.isVisualBlock()&&list.size()>0) {
				if (type == SeparatorVo.TYPE_HORIZ) {
					horizontalRule(block.getBox());
				} else {
					verticalRule(block.getBox());
				}
			} 
		}
	}

	private void threeStep() {
		List<SeparatorVo> temp=new ArrayList<>();
		temp.addAll(list);
		if (type==SeparatorVo.TYPE_HORIZ) {
			for (SeparatorVo sep : temp) {
				if (sep.getX()==0&&(sep.getY()==0||sep.getY()==height)) {
					list.remove(sep);
				}
			}
		}else {
			for (SeparatorVo sep : temp) {
				if (sep.getY()==0&&(sep.getX()==0||sep.getX()==width)) {
					list.remove(sep);
				}
			}
		}
	}

	private void horizontalRule(Box box) {
		List<SeparatorVo> temp=new ArrayList<>();
		temp.addAll(list);
		for (SeparatorVo sep : temp) {
			if (horizontalRule1(box, sep)) {
				int y=box.getAbsoluteContentY()+box.getHeight();
				SeparatorVo separator=new SeparatorVo(0,y,width,(sep.getY()+sep.getHeight())-y,type);
				if (separator.getHeight()!=0) {
					list.add(separator);
				}
				
				SeparatorVo separ=list.get(list.indexOf(sep));
				separ.setHeight(box.getAbsoluteContentY()-separ.getY());
				if (separ.getHeight()==0) {
					list.remove(separ);
				}
				
			}else if (horizontalRule2(box, sep)) {
				list.remove(sep);
			}else if (horizontalRule3(box, sep)) {
				SeparatorVo separator=list.get(list.indexOf(sep));
				int originalY=separator.getY();
				separator.setY(box.getAbsoluteContentY()+box.getHeight());
				separator.setHeight(separator.getHeight()-(separator.getY()-originalY));
			}else if (horizontalRule4(box, sep)) {
				SeparatorVo separator=list.get(list.indexOf(sep));
				separator.setHeight(box.getAbsoluteContentY()-separator.getY());
			}else {
				continue;
			}
		}
	}
	
	private void verticalRule(Box box) {
		System.out.println(list.size());
		List<SeparatorVo> temp=new ArrayList<>();
		temp.addAll(list);
		for (SeparatorVo sep : temp) {
			if (horizontalRule1(box, sep)) {
				int x=box.getAbsoluteContentX()+box.getWidth();
				SeparatorVo separator=new SeparatorVo(x,0,(sep.getX()+sep.getWidth())-x,height,type);
				if (separator.getWidth()!=0) {
					list.add(separator);
				}
				
				SeparatorVo separ=list.get(list.indexOf(sep));
				separ.setWidth(box.getAbsoluteContentX()-separ.getX());
				if (separ.getWidth()==0) {
					list.remove(separ);
				}
				
			}else if (horizontalRule2(box, sep)) {
				list.remove(sep);
			}else if (horizontalRule3(box, sep)) {
				SeparatorVo separator=list.get(list.indexOf(sep));
				int originalX=separator.getX();
				separator.setX(box.getAbsoluteContentX()+box.getWidth());
				separator.setWidth(separator.getWidth()-(separator.getX()-originalX));
			}else if (horizontalRule4(box, sep)) {
				SeparatorVo separator=list.get(list.indexOf(sep));
				separator.setWidth(box.getAbsoluteContentX()-separator.getX());
			}else {
				continue;
			}
		}
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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
}
