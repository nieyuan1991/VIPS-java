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
	
	public BlockExtraction(Viewport viewport) {
		super();
		this.viewport = viewport;
		list=new ArrayList<>();
		hrList=new ArrayList<>();
		block=new BlockVo();
	}

	public BlockVo service() {
		Box body=viewport.getElementBoxByName("body", false);
		initBlock(body, block);
		dividBlock(block);
		BlockVo.refreshBlock(block);
		filList(block);
		return block;
	}
	
	private void initBlock(Box box,BlockVo block) {
		block.getBoxs().add(box);
		if (box.getNode().getNodeName().equals("hr")) {
			hrList.add(block);
		}
		if (! (box instanceof TextBox)) {
			for (Box b : ((ElementBox) box).getSubBoxList()) {
				BlockVo bVo=new BlockVo();
				bVo.setParent(block);
				block.getChildren().add(bVo);
				initBlock(b, bVo);
			} 
		}
	}
	
	private void dividBlock(BlockVo block) {
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
