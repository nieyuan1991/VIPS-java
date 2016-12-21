package test.nie.vips;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.fit.cssbox.layout.Box;
import org.junit.Test;

import com.nie.out.ImageOut;
import com.nie.vo.BlockVo;

public class ImageOutTest {

	@Test
	public void image() {
		BufferedImage bi=new BufferedImage(600, 1200, BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D g2d=bi.createGraphics();
		g2d.setColor(Color.white);
		recurBlock(5, g2d);
		g2d.setColor(Color.black);
		try {
			ImageIO.write(bi, "png", new File("test.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void recurBlock(int block, Graphics2D g2d) {
		if (block>0) {
			g2d.fillRect(0, block*10, 100, 5);
			block--;
		}
		for (int i=0;i<block;i++) {
			recurBlock(block, g2d);
		}
	}
}
