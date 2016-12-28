package com.nie.out;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import com.nie.vo.BlockVo;
import com.nie.vo.SeparatorVo;

public class ImageOut {

	private final String NAMEPRE=System.getProperty("user.dir")+"/q-";
	private int width;
	private int height;
	BufferedImage bi=null;
	
	public ImageOut(int width, int height) {
		super();
		this.width = width;
		this.height = height;
	}

	public void outImg(BufferedImage page) {
		this.bi=page;
		try{
			String filename =  NAMEPRE + "1page.png";
			ImageIO.write(page, "png", new File(filename));
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void outBlock(List<BlockVo> block,String fileName) {
		BufferedImage bi=new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
		Graphics2D g2d=bi.createGraphics();
		g2d.setColor(Color.white);
		g2d.fillRect(0, 0, bi.getWidth(), bi.getHeight());
		g2d.setColor(Color.black);
		for (BlockVo blockVo : block) {
			if (blockVo.isVisualBlock()) {
				g2d.fillRect(blockVo.getX(),blockVo.getY(),blockVo.getWidth(),blockVo.getHeight());
//				g2d.drawRoundRect(box.getAbsoluteContentX(), box.getAbsoluteContentY(), box.getAvailableWidth(), box.getContentHeight(), 1, 1);
			}
		}
		try {
			ImageIO.write(bi, "png", new File(NAMEPRE + fileName + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void outSeparator(List<SeparatorVo> list,String fileName) {
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
		Graphics2D g2d = bi.createGraphics();
		g2d.setColor(Color.white);
		g2d.fillRect(0, 0, bi.getWidth(), bi.getHeight());
		g2d.setColor(Color.blue);
		for (SeparatorVo sep : list) {
			g2d.fillRect(sep.getX(), sep.getY(), sep.getWidth(), sep.getHeight());
		}
		try {
			ImageIO.write(bi, "png", new File(NAMEPRE + fileName + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
}
