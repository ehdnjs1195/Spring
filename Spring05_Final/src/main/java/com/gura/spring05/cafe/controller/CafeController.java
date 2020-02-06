package com.gura.spring05.cafe.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
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
	public ModelAndView detail(@ModelAttribute("dto") CafeDto dto, HttpServletRequest request, ModelAndView mView) {
		
		return mView;
	}
}
