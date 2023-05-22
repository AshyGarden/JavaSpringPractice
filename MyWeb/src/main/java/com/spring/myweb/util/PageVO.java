package com.spring.myweb.util;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class PageVO {
	
	private int pageNum;
	private int cpp;
	
	//myPage에서 페이징을 구현하기 위한 필드추가
	private String loginId;
	
	//검색 요청에 필요한 필드를 추가
	private String keyword;
	private String condition;
	
	public PageVO() {
		this.pageNum = 1;
		this.cpp=20;
	}
	
	public int getPageStart() {//-> xml에 #{pageStart} 라고 적어야함! get(대문자)->(소문자)로 바뀜!
		/*
		 pageNum:1 -> return 0;
		 pageNum:2 -> return 10;
		 pageNum:3 -> return 20;
		 pageNum:n -> return (n-1)*cpp;
		 
		 */
		return (pageNum-1)*cpp;
	}
	
}
