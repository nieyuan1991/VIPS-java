package com.nie.rule;

import org.fit.cssbox.layout.Box;

import com.nie.vo.SeparatorVo;

public class SeparatoRule {

	/**
	 * 水平分隔符:box在分隔符内
	 * @param box
	 * @param sep
	 * @return
	 */
	public static boolean horizontalRule1(Box box, SeparatorVo sep) {
		int y = box.getAbsoluteContentY();
		if (y > sep.getY() && (box.getHeight() + y) < (sep.getHeight() + sep.getY())) {
			return true;
		}
		return false;
	}
	
	/**
	 * 水平分隔符: box穿过分隔符
	 * @param box
	 * @param sep
	 * @return
	 */
	public static boolean horizontalRule2(Box box, SeparatorVo sep) {
		int y = box.getAbsoluteContentY();
		if (y < sep.getY() && (box.getHeight() + y) > (sep.getHeight() + sep.getY())) {
			return true;
		}
		return false;
	}
	
	/**
	 * 水平分隔符 :box下部分与分隔符重合
	 * @param box
	 * @param sep
	 * @return
	 */
	public static boolean horizontalRule3(Box box, SeparatorVo sep) {
		int y = box.getAbsoluteContentY();
		int LBY=y + box.getHeight();
		int sepRLY=sep.getY() + sep.getHeight();
		if (y < sep.getY() && LBY > sep.getY() && LBY < sepRLY) {
			return true;
		}
		return false;
	}
	
	/**
	 * 水平分隔符: box上部分与分隔符重合
	 * @param box
	 * @param sep
	 * @return
	 */
	public static boolean horizontalRule4(Box box, SeparatorVo sep) {
		int y = box.getAbsoluteContentY();
		int LBY=y + box.getHeight();
		int sepLBY=sep.getY() + sep.getHeight();
		if (y > sep.getY() && y < sepLBY && LBY > sepLBY) {
			return true;
		}
		return false;
	}
	
	/**
	 * 垂直分隔符: box在分隔符内
	 * @param box
	 * @param sep
	 * @return
	 */
	public static boolean verticalRule1(Box box, SeparatorVo sep) {
		int x = box.getAbsoluteContentX();
		if (x > sep.getX() && (box.getWidth() + x) < (sep.getWidth() + sep.getX())) {
			return true;
		}
		return false;
	}
	
	/**
	 * 垂直分隔符: box横穿分隔符
	 * @param box
	 * @param sep
	 * @return
	 */
	public static boolean verticalRule2(Box box, SeparatorVo sep) {
		int x = box.getAbsoluteContentX();
		if (x < sep.getX() && (box.getWidth() + x) > (sep.getWidth() + sep.getX())) {
			return true;
		}
		return false;
	}
	
	/**
	 * 垂直分隔符: box右边与分隔符重叠
	 * @param box
	 * @param sep
	 * @return
	 */
	public static boolean verticalRule3(Box box, SeparatorVo sep) {
		int x = box.getAbsoluteContentX();
		int rightX=x + box.getWidth();
		int sepRightX=sep.getX() + sep.getWidth();
		if (x < sep.getX() && rightX > sep.getX() && rightX < sepRightX) {
			return true;
		}
		return false;
	}
	
	/**
	 * 垂直分隔符: box左边与分隔符重叠
	 * @param box
	 * @param sep
	 * @return
	 */
	public static boolean verticalRule4(Box box, SeparatorVo sep) {
		int x = box.getAbsoluteContentX();
		int rightX=x + box.getWidth();
		int sepRightX=sep.getX() + sep.getWidth();
		if (x > sep.getX() && x < sepRightX && rightX > sepRightX) {
			return true;
		}
		return false;
	}
}
