package com.nie.rule;

import com.nie.vo.SeparatorVo;

public class WeightRule {
	private static int distance=50;
	
	/**
	 * The greater the distance between blocks on different side of the separator, 
	 * the higher the weight.
	 * @param sep
	 * @return
	 */
	public static boolean rule1(SeparatorVo sep) {
		if (sep.getType()==SeparatorVo.TYPE_HORIZ) {
			if (sep.getHeight()>distance) {
				return true;
			}
		}else if (sep.getType()==SeparatorVo.TYPE_VERTICAL) {
			if (sep.getWidth()>distance) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * If a visual separator is overlapped with some certain HTML tags (e.g., the <HR> HTML tag), 
	 * its weight is set to be higher.
	 * @param sep
	 * @return
	 */
	public static boolean rule2(SeparatorVo sep) {
		return false;
	}
	
	/**
	 * If background colors of the blocks on two sides of the separator are different ,
	 * the weight will be increased.
	 * @param sep
	 * @return
	 */
	public static boolean rule3(SeparatorVo sep) {
		return false;
	}
	
	/**
	 * For horizontal separators, 
	 * if the differences of font properties such as font size and font weight 
	 * are bigger on two sides of the separator, 
	 * the weight will be increased. Moreover, 
	 * the weight will be increased if the font size of the block above the 
	 * separator is smaller than the font size of the block below the separator.
	 * @param sep
	 * @return
	 */
	public static boolean rule4(SeparatorVo sep) {
		return false;
	}
	
	/**
	 * For horizontal separators, 
	 * when the structures of the blocks on the two sides of the separator are very similar 
	 * (e.g. both are text), the weight of the separator will be decreased.
	 * @param sep
	 * @return
	 */
	public static boolean rule5(SeparatorVo sep) {
		return false;
	}
}
