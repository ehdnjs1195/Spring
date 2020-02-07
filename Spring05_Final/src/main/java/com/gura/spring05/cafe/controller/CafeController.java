package com.gura.spring05.cafe.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.gura.spring05.cafe.dto.CafeDto;
import com.gura.spring05.cafe.service.CafeService;

@Controller
public class CafeController {
	@Autowired
	private CafeService service;
	
	//cafe 목록 보기 요청 처리
	@RequestMapping("/cafe/list")
	public ModelAndView list(ModelAndView mView, HttpServletRequest request) {
		//cafe 목록과 페이징 처리에 필요한 값들을 request 에 담아주는 서비스 메소드 호출하기
		service.list(request);
		mView.setViewName("cafe/list");
		return mView;
	}
	
	@RequestMapping("/cafe/detail")
	public ModelAndView detail(@RequestParam int num,@RequestParam int pageNum, ModelAndView mView) {
		service.showDetail(num, pageNum, mView);
		mView.setViewName("/cafe/detail");
		return mView;
	}
	
	@RequestMapping("/cafe/insertform")
	public ModelAndView authInsertform(HttpServletRequest request) {
		
		return new ModelAndView("cafe/insertform");
	}
	
	@RequestMapping(value = "/cafe/insert", method = RequestMethod.POST)
	public ModelAndView authInsert(@ModelAttribute CafeDto dto, HttpServletRequest request ) {
		service.addContent(dto, request);
		
		return new ModelAndView("redirect:/cafe/list.do");
	}
	
	@RequestMapping("/cafe/updateform")
	public ModelAndView authUpdateform(@RequestParam int num,@RequestParam int pageNum, HttpServletRequest request) {
		service.showContent(num, pageNum, request);
		
		return new ModelAndView("cafe/updateform");
	}
	
	@RequestMapping("/cafe/update")
	public ModelAndView authUpdate(@RequestParam int num,@RequestParam int pageNum, @ModelAttribute CafeDto dto, HttpServletRequest request) {
		service.updateContent(pageNum, dto, request);
		
		return new ModelAndView("redirect:/cafe/detail.do?num="+num+"&pageNum="+pageNum);
	}
	
	@RequestMapping("/cafe/delete")
	public ModelAndView authDelete(@RequestParam int num, HttpServletRequest request) {
		service.deleteContent(num, request);
		return new ModelAndView("redirect:/cafe/list.do");
	}
	
	//댓글 저장 요청 처리
	@RequestMapping(value = "/cafe/comment_insert", method = RequestMethod.POST)
	public ModelAndView authCommentInsert(HttpServletRequest request, @RequestParam int ref_group,@RequestParam int pageNum) {
		service.saveComment(request);
		return new ModelAndView("redirect:/cafe/detail.do?num="+ref_group+"&pageNum="+pageNum);
	}
}
