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
	
	private int PDoC=8;
	private int round=1;
	private URL url = null;
	private DOMAnalyzer domAnalyzer = null;
	private BrowserCanvas browserCanvas = null;
	private Viewport viewport = null;
	private ImageOut imgOut=null;
	
	public Vips(String urlStr) {
		setUrl(urlStr);
		getDomTree(url);
		getViewport();
		imgOut=new ImageOut(browserCanvas.getImage());
		imgOut.outImg();
	}
	
	public void service() {
		System.out.println("-----------------------------Block Extraction------------------------------------");
		BlockExtraction be=new BlockExtraction(viewport);
		BlockVo block=be.service();
		List<BlockVo> blockList = be.getList();
		int i=0;
		while (checkDoC(blockList)&&i<round) {
			System.out.println("blockList.size::"+blockList.size());
			imgOut.outBlock(blockList, "Block-"+i);
			
			System.out.println("-----------------------------Separator Detection---------------------------------"+i);
			SeparatorDetection sd = new SeparatorDetection(viewport.getWidth(), viewport.getHeight());
			List<SeparatorVo> verticaList = new ArrayList<>();
			verticaList.addAll(sd.service(blockList, SeparatorVo.TYPE_VERTICAL));
			imgOut.outSeparator(verticaList, "vertica-"+i);
			
			List<SeparatorVo> horizList = new ArrayList<>();
			horizList.addAll(sd.service(blockList, SeparatorVo.TYPE_HORIZ));
			imgOut.outSeparator(horizList, "horizontal-"+i);
			
			System.out.println("-----------------------Setting Weights for Separators----------------------------"+i);
			List<BlockVo> hrList = be.getHrList();
			SeparatorWeight sw = new SeparatorWeight();
			sw.service(horizList, hrList);
			sw.service(verticaList, hrList);
			
			System.out.println("-----------------------Content Structure Construction----------------------------"+i);
			List<SeparatorVo> sepList = new ArrayList<>();
			sepList.addAll(horizList);
//			sepList.addAll(verticaList);
			Collections.sort(sepList);
			ContentStructureConstruction csc = new ContentStructureConstruction();
			csc.service(sepList, block);
			BlockVo.refreshBlock(block);
			blockList.clear();
			be.filList(block);
			blockList=be.getList();
			i++;
		}
		
	}

	private boolean checkDoC(List<BlockVo> blocks) {
		for (BlockVo blockVo : blocks) {
			if (blockVo.getDoC()<PDoC) {
				return true;
			}
		}
		return false;
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

	public void setRound(int round) {
		this.round = round;
	}
	
}
