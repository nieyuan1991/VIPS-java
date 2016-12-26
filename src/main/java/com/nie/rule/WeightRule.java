package com.nie.rule;

import java.util.List;

import org.fit.cssbox.layout.Box;
import org.fit.cssbox.layout.ElementBox;

import com.nie.vo.BlockVo;
import com.nie.vo.SeparatorVo;

public class WeightRule {
	private static int distance=50;
	
	/**
	 * The greater the distance between blocks on different side of the separator, 
	 * the higher the weight.
	 * @param sep
	 * @return
	 */
	public static void rule1(SeparatorVo sep) {
		if (sep.getType()==SeparatorVo.TYPE_HORIZ) {
			sep.setWeight(sep.getWeight()+sep.getHeight()/distance);
		}else if (sep.getType()==SeparatorVo.TYPE_VERTICAL) {
			sep.setWeight(sep.getWeight()+sep.getWeight()/distance);
		}
	}
	
	/**
	 * If a visual separator is overlapped with some certain HTML tags (e.g., the <HR> HTML tag), 
	 * its weight is set to be higher.
	 * @param sep
	 * @return
	 */
	public static void rule2(SeparatorVo sep,List<BlockVo> hrList) {
		for (BlockVo block : hrList) {
			Box box=block.getBox();
			int RBX=sep.getX()+sep.getWidth();
			int RBY=sep.getY()+sep.getHeight();
			int HRRBX=box.getAbsoluteContentX()+box.getWidth();
			int HRRBY=box.getAbsoluteContentY()+box.getHeight();
			if (sep.getX()<=box.getAbsoluteContentX()&&sep.getY()<=box.getAbsoluteContentY()
					&&RBX>HRRBX&&RBY>HRRBY) {
				sep.setWeight(sep.getWeight()+1);
			}
		}
	}
	
	/**
	 * If background colors of the blocks on two sides of the separator are different ,
	 * the weight will be increased.
	 * @param sep
	 * @return
	 */
	public static void rule3(SeparatorVo sep) {
		Box oneBox=sep.getOneSide().getBox();
		Box otherBox=sep.getOtherSide().getBox();
		if (oneBox instanceof ElementBox&&otherBox instanceof ElementBox) {
			String oneColor = ((ElementBox) oneBox).getStylePropertyValue("background-color");
			String otherColor = ((ElementBox) otherBox).getStylePropertyValue("background-color");
			if (!oneColor.equals(otherColor)) {
				sep.setWeight(sep.getWeight() + 1);
			} 
		}
	}
	
	/**
	 * For horizontal separators, 
	 * if the differences of font properties such as font size and font weight 
	 * are bigger on two sides of the separator,the weight will be increased. 
	 * Moreover,the weight will be increased if the font size of the block above the 
	 * separator is smaller than the font size of the block below the separator.
	 * @param sep
	 * @return
	 */
	public static void rule4(SeparatorVo sep) {
		if (sep.getType()==SeparatorVo.TYPE_HORIZ) {
			Box oneBox=sep.getOneSide().getBox();
			int oneSize=oneBox.getVisualContext().getFont().getSize();
			Box otherBox=sep.getOtherSide().getBox();
			int otherSize=otherBox.getVisualContext().getFont().getSize();
			if (oneSize<otherSize) {
				sep.setWeight(sep.getWeight()+1);
			}
			
			if (oneBox instanceof ElementBox&&otherBox instanceof ElementBox) {
				String oneWeight = ((ElementBox) oneBox).getStylePropertyValue("font-weight");
				String otherWeight = ((ElementBox) otherBox).getStylePropertyValue("font-weight");
				if (oneSize != otherSize && !oneWeight.equals(otherWeight)) {
					sep.setWeight(sep.getWeight() + 1);
				} 
			}
			
		}
	}
	
	/**
	 * For horizontal separators, 
	 * when the structures of the blocks on the two sides of the separator are very similar 
	 * (e.g. both are text), the weight of the separator will be decreased.
	 * @param sep
	 * @return
	 */
	public static void rule5(SeparatorVo sep) {
		if (sep.getType()==SeparatorVo.TYPE_HORIZ) {
			Box oneBox=sep.getOneSide().getBox();
			String oneName=oneBox.getNode().getNodeName();
			Box otherBox=sep.getOtherSide().getBox();
			String otherName=otherBox.getNode().getNodeName();
			if (oneName.equals(otherName)) {
				sep.setWeight(sep.getWeight()-1);
			}
		}
	}
}
