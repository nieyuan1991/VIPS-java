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
		List<SeparatorVo> temp=new ArrayList<>();
		temp.addAll(sepList);
		int maxWeight=temp.get(temp.size()-1).getWeight();
		System.out.println("maxWeight::"+maxWeight);
		for (SeparatorVo sep : temp) {
			if (maxWeight==sep.getWeight()) {
				break;
			}
			if (sep.getOneSide()!=null&&sep.getOtherSide()!=null) {
				idSet.add(sep.getOneSide().getId());
				idSet.add(sep.getOtherSide().getId());
			}
			sepList.remove(sep);
		}
		changeBlockById(block);
	}
	
	private void changeBlockById(BlockVo block) {
		Set<String> temp=new HashSet<>();
		temp.addAll(idSet);
		for (String id : temp) {
			if (block.getId().equals(id)&&block.getParent()!=null) {
				block.getParent().setVisualBlock(true);
				block.setVisualBlock(false);
				idSet.remove(id);
				break;
			}
		}
		for (BlockVo bVo : block.getChildren()) {
			changeBlockById(bVo);
		}
	}
}
