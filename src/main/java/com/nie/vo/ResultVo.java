package com.nie.vo;

import java.util.List;

public class ResultVo {

	private List<BlockVo> list;
	private List<SeparatorVo> horizList;
	private List<SeparatorVo> verticaList;
	
	public List<BlockVo> getList() {
		return list;
	}
	public void setList(List<BlockVo> list) {
		this.list = list;
	}
	public List<SeparatorVo> getHorizList() {
		return horizList;
	}
	public void setHorizList(List<SeparatorVo> horizList) {
		this.horizList = horizList;
	}
	public List<SeparatorVo> getVerticaList() {
		return verticaList;
	}
	public void setVerticaList(List<SeparatorVo> verticaList) {
		this.verticaList = verticaList;
	}
	
	
}
