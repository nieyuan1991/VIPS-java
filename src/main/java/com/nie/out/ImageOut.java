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

	private final String NAMEPRE=System.getProperty("user.dir");
	private int width;
	private int height;
	
	public ImageOut(int width, int height) {
		super();
		this.width = width;
		this.height = height;
	}

	public void outImg(BufferedImage page) {
		try{
			String filename =  NAMEPRE + "/page.png";
			ImageIO.write(page, "png", new File(filename));
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void outBlock(List<BlockVo> block) {
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
		try {
			ImageIO.write(bi, "png", new File(NAMEPRE + "/block.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void outSeparator(List<SeparatorVo> list) {
		BufferedImage bi=new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
		Graphics2D g2d=bi.createGraphics();
		g2d.setColor(Color.white);
		g2d.fillRect(0, 0, bi.getWidth(), bi.getHeight());
		g2d.setColor(Color.blue);
		for (SeparatorVo sep : list) {
			g2d.fillRect(sep.getX(), sep.getY(), sep.getWidth(), sep.getHeight());
		}
		try {
			ImageIO.write(bi, "png", new File(NAMEPRE + "/Separator"+list.get(0).getType()+".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
