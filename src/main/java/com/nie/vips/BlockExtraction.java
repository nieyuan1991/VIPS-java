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
//	private int round=0;
	
	public BlockExtraction(Viewport viewport) {
		super();
		this.viewport = viewport;
		list=new ArrayList<>();
	}

	public List<BlockVo> service() {
		Box body=viewport.getElementBoxByName("body", false);
		BlockVo block=new BlockVo();
		fillBlock(body, block);
		dividBlock(block);
		countVisualBlock(block);
		System.out.println("countVisualBlock::"+list.size());
		return list;
	}
	
	private void fillBlock(Box box,BlockVo block) {
		block.setBox(box);
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
	
	private void countVisualBlock(BlockVo block) {
		if (block.isVisualBlock()) {
			list.add(block);
		}
		for (BlockVo blockVo : block.getChildren()) {
			countVisualBlock(blockVo);
		}
	}
	
	
}
