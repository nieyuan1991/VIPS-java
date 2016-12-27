package com.nie.rule;

import com.nie.vo.BlockVo;
import com.nie.vo.SeparatorVo;

public class SeparatoRule {

	/**
	 * 水平分隔符:block在分隔符内
	 * @param block
	 * @param sep
	 * @return
	 */
	public static boolean horizontalRule1(BlockVo block, SeparatorVo sep) {
		int y = block.getY();
		if (y > sep.getY() && (block.getHeight() + y) < (sep.getHeight() + sep.getY())) {
			return true;
		}
		return false;
	}
	
	/**
	 * 水平分隔符: block穿过分隔符
	 * @param block
	 * @param sep
	 * @return
	 */
	public static boolean horizontalRule2(BlockVo block, SeparatorVo sep) {
		int y = block.getY();
		if (y < sep.getY() && (block.getHeight() + y) > (sep.getHeight() + sep.getY())) {
			return true;
		}
		return false;
	}
	
	/**
	 * 水平分隔符 :block下部分与分隔符重合
	 * @param block
	 * @param sep
	 * @return
	 */
	public static boolean horizontalRule3(BlockVo block, SeparatorVo sep) {
		int y = block.getY();
		int LBY=y + block.getHeight();
		int sepRLY=sep.getY() + sep.getHeight();
		if (y < sep.getY() && LBY > sep.getY() && LBY < sepRLY) {
			return true;
		}
		return false;
	}
	
	/**
	 * 水平分隔符: block上部分与分隔符重合
	 * @param block
	 * @param sep
	 * @return
	 */
	public static boolean horizontalRule4(BlockVo block, SeparatorVo sep) {
		int y = block.getY();
		int LBY=y + block.getHeight();
		int sepLBY=sep.getY() + sep.getHeight();
		if (y > sep.getY() && y < sepLBY && LBY > sepLBY) {
			return true;
		}
		return false;
	}
	
	/**
	 * 垂直分隔符: block在分隔符内
	 * @param block
	 * @param sep
	 * @return
	 */
	public static boolean verticalRule1(BlockVo block, SeparatorVo sep) {
		int x = block.getX();
		if (x > sep.getX() && (block.getWidth() + x) < (sep.getWidth() + sep.getX())) {
			return true;
		}
		return false;
	}
	
	/**
	 * 垂直分隔符: block横穿分隔符
	 * @param block
	 * @param sep
	 * @return
	 */
	public static boolean verticalRule2(BlockVo block, SeparatorVo sep) {
		int x = block.getX();
		if (x < sep.getX() && (block.getWidth() + x) > (sep.getWidth() + sep.getX())) {
			return true;
		}
		return false;
	}
	
	/**
	 * 垂直分隔符: block右边与分隔符重叠
	 * @param block
	 * @param sep
	 * @return
	 */
	public static boolean verticalRule3(BlockVo block, SeparatorVo sep) {
		int x = block.getX();
		int rightX=x + block.getWidth();
		int sepRightX=sep.getX() + sep.getWidth();
		if (x < sep.getX() && rightX > sep.getX() && rightX < sepRightX) {
			return true;
		}
		return false;
	}
	
	/**
	 * 垂直分隔符: block左边与分隔符重叠
	 * @param block
	 * @param sep
	 * @return
	 */
	public static boolean verticalRule4(BlockVo block, SeparatorVo sep) {
		int x = block.getX();
		int rightX=x + block.getWidth();
		int sepRightX=sep.getX() + sep.getWidth();
		if (x > sep.getX() && x < sepRightX && rightX > sepRightX) {
			return true;
		}
		return false;
	}
}
