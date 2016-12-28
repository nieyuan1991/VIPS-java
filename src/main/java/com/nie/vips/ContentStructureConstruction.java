package com.nie.vips;

import java.util.ArrayList;
import java.util.List;

import com.nie.vo.BlockVo;
import com.nie.vo.SeparatorVo;

public class ContentStructureConstruction {

	public ContentStructureConstruction() {
		super();
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
					BlockVo one=sep.getOneSide();
					BlockVo other=sep.getOtherSide();
					BlockVo newBlock=new BlockVo();
					newBlock.setParent(one.getParent());
					newBlock.getBoxs().addAll(one.getBoxs());
					newBlock.getBoxs().addAll(other.getBoxs());
					newBlock.getChildren().addAll(one.getChildren());
					newBlock.getChildren().addAll(other.getChildren());
					newBlock.setDoC(sep.getWeight());
					newBlock.refresh();
					one.getParent().getChildren().add(newBlock);
					one.setVisualBlock(false);
					other.setVisualBlock(false);
					
					int sum=0;
					for (SeparatorVo separator : temp) {
						if (separator.getOneSide()==other) {
							separator.setOneSide(newBlock);
							sum++;
						}
						if (separator.getOtherSide()==one) {
							separator.setOtherSide(newBlock);
							sum++;
						}
						if (sum==2) {
							break;
						}
					}
				}
				sepList.remove(sep);
			}
		}
	}
	
//	private BlockVo mergeBlock(List<BlockVo> blockList,String id){
//		for (BlockVo bVo : blockList) {
//			if (bVo.getId().equals(id)) {
//				return bVo;
//			}else {
//				return mergeBlock(bVo.getChildren(), id);
//			}
//		}
//		return null;
//	}
}
