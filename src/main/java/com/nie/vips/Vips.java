package com.nie.vips;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

import javax.imageio.ImageIO;

import org.fit.cssbox.css.CSSNorm;
import org.fit.cssbox.css.DOMAnalyzer;
import org.fit.cssbox.io.DOMSource;
import org.fit.cssbox.io.DefaultDOMSource;
import org.fit.cssbox.io.DefaultDocumentSource;
import org.fit.cssbox.io.DocumentSource;
import org.fit.cssbox.layout.Box;
import org.fit.cssbox.layout.BrowserCanvas;
import org.fit.cssbox.layout.ElementBox;
import org.fit.cssbox.layout.TextBox;
import org.fit.cssbox.layout.Viewport;
import org.w3c.dom.Document;

import com.nie.out.ImageOut;
import com.nie.rule.ExtractRule;
import com.nie.vo.BlockVo;

public class Vips {
	
	private URL url = null;
	private DOMAnalyzer domAnalyzer = null;
	private BrowserCanvas browserCanvas = null;
	private Viewport viewport = null;
	private int count=0,round=0;
	
	private void init() {
		setUrl("http://china.huanqiu.com/article/2016-12/9839624.html?from=bdwz");
		getDomTree(url);
		getViewport();
		exportPageToImage();
	}
	
	public void extration() {
		init();
		Box body=viewport.getElementBoxByName("body", false);
		BlockVo block=new BlockVo();
		fillBlock(body, block);
		dividBlock(block);
		countVisualBlock(block);
		System.out.println("countVisualBlock::"+count);
		ImageOut out=new ImageOut();
		out.outBlock2Img(block,viewport.getWidth(),viewport.getHeight());
	}
	
	private void dividBlock(BlockVo block) {
		round++;
		System.out.println("------------------------------round::"+round);
		if (block.isDividable()&&ExtractRule.dividable(block)) {
			block.setVisualBlock(false);
			for (BlockVo b : block.getChildren()) {
				dividBlock(b);
			}
		}
	}
	
	private void fillBlock(Box box,BlockVo block) {
		block.setBox(box);
		if (! (box instanceof TextBox)) {
			for (Box b : ((ElementBox) box).getSubBoxList()) {
				block.getChildren().add(new BlockVo());
				block.getChildren().get(block.getChildren().size()-1).setParent(block);
				fillBlock(b, block.getChildren().get(block.getChildren().size()-1));
			} 
		}
	}
	
	private void setUrl(String urlStr)
	{
		try
		{
			if (urlStr.startsWith("http://") || urlStr.startsWith("https://"))
				url = new URL(urlStr);
			else
				url = new URL("http://" + urlStr);
		}
		catch (Exception e)
		{
			System.err.println("Invalid address: " + urlStr);
		}
	}
	
	private void getDomTree(URL urlStream)
	{
		DocumentSource docSource = null;
		try
		{
			docSource = new DefaultDocumentSource(urlStream);
			DOMSource parser = new DefaultDOMSource(docSource);

			Document domTree = parser.parse();
			domAnalyzer = new DOMAnalyzer(domTree, url);
			domAnalyzer.attributesToStyles();
			domAnalyzer.addStyleSheet(null, CSSNorm.stdStyleSheet(), DOMAnalyzer.Origin.AGENT);
			domAnalyzer.addStyleSheet(null, CSSNorm.userStyleSheet(), DOMAnalyzer.Origin.AGENT);
			domAnalyzer.getStyleSheets();
		}
		catch (Exception e)
		{
			System.err.print(e.getMessage());
		}
	}
	
	private void getViewport()
	{
		browserCanvas = new BrowserCanvas(domAnalyzer.getRoot(),
				domAnalyzer, new java.awt.Dimension(1200, 600), url);
		viewport = browserCanvas.getViewport();
	}
	
	private void exportPageToImage()
	{
		try
		{
			BufferedImage page = browserCanvas.getImage();
			String filename = System.getProperty("user.dir") + "/page.png";
			ImageIO.write(page, "png", new File(filename));
		} catch (Exception e)
		{
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private void countVisualBlock(BlockVo block) {
		if (block.isVisualBlock()) {
			count++;
		}
		for (BlockVo blockVo : block.getChildren()) {
			countVisualBlock(blockVo);
		}
	}
}
