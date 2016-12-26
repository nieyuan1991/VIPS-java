package com.nie.vips;

import java.util.List;

import static com.nie.rule.WeightRule.*;

import com.nie.vo.BlockVo;
import com.nie.vo.SeparatorVo;

public class SeparatorWeight {

	public void service(List<SeparatorVo> list,List<BlockVo> hrList) {
		for (SeparatorVo sep : list) {
			if (sep.getOneSide()==null|| sep.getOtherSide()==null) {
				continue;
			}
			rule1(sep);
			rule2(sep,hrList);
			rule3(sep);
			rule4(sep);
			rule5(sep);
		}
	}
}
