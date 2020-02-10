package com.gura.spring05.cafe.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

import com.gura.spring05.cafe.dto.CafeDto;

public interface CafeService {
	public void list(HttpServletRequest request);
	public void showDetail(int num, int pageNum, ModelAndView mView, HttpServletRequest request);
	public void addContent(CafeDto dto, HttpServletRequest request);
	public void showContent(int num, int pageNum, HttpServletRequest request);
	public void updateContent(int pageNum, CafeDto dto, HttpServletRequest request);
	public void deleteContent(int num, HttpServletRequest request);
	//댓글 저장하는 메소드
	public void saveComment(HttpServletRequest request);
	//댓글 삭제하는 메소드
	public void deleteComment(int num);
}
