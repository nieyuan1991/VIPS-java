package com.nie.rule;

import org.fit.cssbox.layout.Box;

import com.nie.vo.SeparatorVo;

public class SeparatoRule {

	/**
	 * �Ӿ�����ȫ�ڷָ����ڵ�
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
	 * �Ӿ������򴩹��ָ���
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
	 * �Ӿ����²��ͷָ����غ�
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
	 * �Ӿ����ϲ��ͷָ����غ�
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
	 * �Ӿ�����ȫ�ڷָ����ڵ�
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
	 * �Ӿ������򴩹��ָ���
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
	 * �Ӿ����²��ͷָ����غ�
	 * @param box
	 * @param sep
	 * @return
	 */
	public static boolean verticalRule3(Box box, SeparatorVo sep) {
		int x = box.getAbsoluteContentX();
		int LBY=x + box.getWidth();
		int sepRLY=sep.getX() + sep.getWidth();
		if (x < sep.getX() && LBY > sep.getX() && LBY < sepRLY) {
			return true;
		}
		return false;
	}
	
	/**
	 * �Ӿ����ϲ��ͷָ����غ�
	 * @param box
	 * @param sep
	 * @return
	 */
	public static boolean verticalRule4(Box box, SeparatorVo sep) {
		int x = box.getAbsoluteContentX();
		int LBY=x + box.getWidth();
		int sepLBY=sep.getX() + sep.getWidth();
		if (x > sep.getX() && x < sepLBY && LBY > sepLBY) {
			return true;
		}
		return false;
	}
}
