package com.nie.vips;

import java.util.List;

import static com.nie.rule.WeightRule.*;
import com.nie.vo.SeparatorVo;

public class SeparatorWeight {

	public void service(List<SeparatorVo> List) {
		for (SeparatorVo sep : List) {
			if (rule1(sep)) {
				sep.setWeight(sep.getWeight());
			}
			if (rule2(sep)) {
				sep.setWeight(sep.getWeight()+1);
			}
			if (rule3(sep)) {
				sep.setWeight(sep.getWeight()+1);
			}
			if (rule4(sep)) {
				sep.setWeight(sep.getWeight()+1);
			}
			if (rule5(sep)) {
				sep.setWeight(sep.getWeight()-1);
			}
		}
	}
}
