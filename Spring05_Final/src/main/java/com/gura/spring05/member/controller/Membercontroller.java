package com.gura.spring05.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Membercontroller {
	// 회원 목록 보기 요청(/member/list.do) 을 처리할 컨트롤러의 메서드
	@RequestMapping("/member/list")
	public ModelAndView list(ModelAndView mView) {
		
		mView.setViewName("member/list");	
		return mView;
	}
}
