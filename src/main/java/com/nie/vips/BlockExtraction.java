package com.nie.vips;

import java.util.ArrayList;
import java.util.List;

import org.fit.cssbox.layout.Box;
import org.fit.cssbox.layout.ElementBox;
import org.fit.cssbox.layout.TextBox;
import org.fit.cssbox.layout.Viewport;

import com.nie.rule.BlockRule;
import com.nie.vo.BlockVo;

public class BlockExtraction {

	private Viewport viewport = null;
	private List<BlockVo> list=null;
	private List<BlockVo> hrList=null;
	BlockVo block=null;
//	private int round=0;
	
	public BlockExtraction(Viewport viewport) {
		super();
		this.viewport = viewport;
		list=new ArrayList<>();
		hrList=new ArrayList<>();
		block=new BlockVo();
	}

	public BlockVo service() {
		Box body=viewport.getElementBoxByName("body", false);
		fillBlock(body, block);
		dividBlock(block);
		filList(block);
		System.out.println("countVisualBlock::"+list.size());
		return block;
	}
	
	private void fillBlock(Box box,BlockVo block) {
		block.setBox(box);
		if (box.getNode().getNodeName().equals("hr")) {
			hrList.add(block);
		}
		if (! (box instanceof TextBox)) {
			for (Box b : ((ElementBox) box).getSubBoxList()) {
				block.getChildren().add(new BlockVo());
				block.getChildren().get(block.getChildren().size()-1).setParent(block);
				fillBlock(b, block.getChildren().get(block.getChildren().size()-1));
			} 
		}
	}
	
	private void dividBlock(BlockVo block) {
//		round++;
//		System.out.println("------------------------------round::"+round);
		if (block.isDividable()&&BlockRule.dividable(block)) {
			block.setVisualBlock(false);
			for (BlockVo b : block.getChildren()) {
				dividBlock(b);
			}
		}
	}
	
	public void filList(BlockVo block) {
		if (block.isVisualBlock()) {
			list.add(block);
		}else {
			for (BlockVo blockVo : block.getChildren()) {
				filList(blockVo);
			}
		}
	}

	public List<BlockVo> getList() {
		return list;
	}

	public List<BlockVo> getHrList() {
		return hrList;
	}
	
}
