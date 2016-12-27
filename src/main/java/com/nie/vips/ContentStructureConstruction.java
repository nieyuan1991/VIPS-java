package com.nie.vips;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.nie.vo.BlockVo;
import com.nie.vo.SeparatorVo;

public class ContentStructureConstruction {

	private Set<String> idSet=null;
	
	
	public ContentStructureConstruction() {
		super();
		this.idSet =new HashSet<>();
	}

	public void service(List<SeparatorVo> sepList,BlockVo block){
		if (sepList.size()>0) {
			List<SeparatorVo> temp = new ArrayList<>();
			temp.addAll(sepList);
			int maxWeight = temp.get(temp.size() - 1).getWeight();
			System.out.println("maxWeight::" + maxWeight);
			for (SeparatorVo sep : temp) {
				if (maxWeight == sep.getWeight()) {
					break;
				}
				if (sep.getOneSide() != null && sep.getOtherSide() != null) {
					String oneId=sep.getOneSide().getId();
					String otherId=sep.getOtherSide().getId();
					loopBlock(block, oneId, otherId);
				}
				sepList.remove(sep);
			}
		}
	}

	private void loopBlock(BlockVo block, String oneId, String otherId) {
		if (!mergeBlock(block.getChildren(), oneId, otherId)) {
			for (BlockVo childBlock : block.getChildren()) {
				loopBlock(childBlock, oneId, otherId);
			}
		}
	}
	
	private boolean mergeBlock(List<BlockVo> blocks,String oneId,String otherId) {
		for (BlockVo block : blocks) {
			if (block.getId().equals(oneId)) {
				List<BlockVo> childTemp = new ArrayList<>();
				childTemp.addAll(block.getParent().getChildren());
				for (BlockVo bVo : childTemp) {
					if (bVo.getId().equals(otherId)) {
						block.getBoxs().addAll(bVo.getBoxs());
						block.getChildren().addAll(bVo.getChildren());
						block.getParent().getChildren().remove(bVo);
						return true;
					}
				} 
				return false;
			}
		}
		return false;
	}
	
}
