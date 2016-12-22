package com.nie.out;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import org.fit.cssbox.layout.Box;

import com.nie.vo.BlockVo;
import com.nie.vo.SeparatorVo;

public class ImageOut {

	private static final String NAMEPRE=System.getProperty("user.dir");
	public static void outImg(BufferedImage page) {
		try{
			String filename =  NAMEPRE + "/page.png";
			ImageIO.write(page, "png", new File(filename));
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void outBlock(List<BlockVo> block,int width,int height) {
		System.out.println(width+"::"+height);
		BufferedImage bi=new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
		Graphics2D g2d=bi.createGraphics();
		g2d.setColor(Color.white);
		g2d.fillRect(0, 0, bi.getWidth(), bi.getHeight());
		g2d.setColor(Color.black);
		for (BlockVo blockVo : block) {
			if (blockVo.isVisualBlock()) {
				Box box = blockVo.getBox();
				g2d.fillRect(box.getAbsoluteContentX(), box.getAbsoluteContentY(), box.getAvailableWidth(), box.getContentHeight());
			}
		}
//		g2d.setColor(Color.black);
		try {
			ImageIO.write(bi, "png", new File(NAMEPRE + "/block.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void outSeparator(List<SeparatorVo> list,int width,int height) {
		BufferedImage bi=new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
		Graphics2D g2d=bi.createGraphics();
		g2d.setColor(Color.white);
		g2d.fillRect(0, 0, bi.getWidth(), bi.getHeight());
		g2d.setColor(Color.blue);
		for (SeparatorVo sep : list) {
			g2d.fillRect(sep.getX(), sep.getY(), sep.getWidth(), sep.getHeight());
		}
		try {
			ImageIO.write(bi, "png", new File(NAMEPRE + "/Separator.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
