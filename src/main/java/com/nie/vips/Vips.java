package com.nie.vips;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.fit.cssbox.css.CSSNorm;
import org.fit.cssbox.css.DOMAnalyzer;
import org.fit.cssbox.io.DOMSource;
import org.fit.cssbox.io.DefaultDOMSource;
import org.fit.cssbox.io.DefaultDocumentSource;
import org.fit.cssbox.io.DocumentSource;
import org.fit.cssbox.layout.BrowserCanvas;
import org.fit.cssbox.layout.Viewport;
import org.w3c.dom.Document;

import com.nie.out.ImageOut;
import com.nie.vo.BlockVo;
import com.nie.vo.SeparatorVo;

public class Vips {
	
//	private int PDoC=8;
	private URL url = null;
	private DOMAnalyzer domAnalyzer = null;
	private BrowserCanvas browserCanvas = null;
	private Viewport viewport = null;
	private ImageOut imgOut=null;
	
	public Vips() {
		setUrl("http://china.huanqiu.com/article/2016-12/9839624.html?from=bdwz");
		getDomTree(url);
		getViewport();
		imgOut=new ImageOut(viewport.getWidth(), viewport.getHeight());
		imgOut.outImg(browserCanvas.getImage());
	}
	
	public void service() {
		System.out.println("-----------------------------Block Extraction------------------------------------");
		BlockExtraction be=new BlockExtraction(viewport);
		BlockVo block=be.service();
		List<BlockVo> blocks=be.getList();
		imgOut.outBlock(blocks,"Block1");
		System.out.println("-----------------------------Separator Detection---------------------------------");
		SeparatorDetection sd=new SeparatorDetection(viewport.getWidth(), viewport.getHeight());
		List<SeparatorVo> horizList=new ArrayList<>();
		horizList.addAll(sd.service(blocks, SeparatorVo.TYPE_HORIZ));
		imgOut.outSeparator(horizList,"horizontal");
		List<SeparatorVo> verticaList=new ArrayList<>();
		verticaList.addAll(sd.service(blocks, SeparatorVo.TYPE_VERTICAL));
		imgOut.outSeparator(verticaList,"vertica");
		System.out.println("-----------------------Setting Weights for Separators----------------------------");
		List<BlockVo> hrList=be.getHrList();
		SeparatorWeight sw=new SeparatorWeight();
		sw.service(horizList, hrList);
		sw.service(verticaList, hrList);
		System.out.println("-----------------------Content Structure Construction----------------------------");
		List<SeparatorVo> sepList=new ArrayList<>();
		sepList.addAll(horizList);
		sepList.addAll(verticaList);
		Collections.sort(sepList);
		
		ContentStructureConstruction csc=new ContentStructureConstruction();
		csc.service(sepList, block);
		System.out.println(block);
		
		be.getList().clear();
		be.filList(block);
		blocks=be.getList();
		System.out.println("countVisualBlock::"+blocks.size());
		imgOut.outBlock(blocks,"Block2");
		imgOut.outSeparator(sepList, "separator");
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
	
}
