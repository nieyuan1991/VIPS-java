package com.nie.out;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.fit.cssbox.layout.Box;

import com.nie.vo.BlockVo;

public class ImageOut {

	public void outBlock2Img(BlockVo block,int width,int height) {
		System.out.println(width+"::"+height);
		BufferedImage bi=new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D g2d=bi.createGraphics();
		g2d.setColor(Color.white);
		recurBlock(block, g2d);
		g2d.setColor(Color.black);
		try {
			ImageIO.write(bi, "png", new File("test.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void recurBlock(BlockVo block, Graphics2D g2d) {
		if (block.isVisualBlock()) {
			Box box = block.getBox();
//			System.out.println(box.getAbsoluteContentX()+","+ box.getAbsoluteContentY()+","+  box.getWidth()+","+ box.getHeight());
			g2d.fillRect(box.getAbsoluteContentX(), box.getAbsoluteContentY(), box.getAvailableWidth(), box.getContentHeight());
		}
		for (BlockVo bVo : block.getChildren()) {
			recurBlock(bVo, g2d);
		}
	}
}
