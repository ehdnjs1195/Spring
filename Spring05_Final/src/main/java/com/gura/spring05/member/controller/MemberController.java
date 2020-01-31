package com.gura.spring05.member.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.gura.spring05.member.dao.MemberDao;
import com.gura.spring05.member.dto.MemberDto;

@Controller
public class MemberController {
	// 의존 객체 주입받기 (DI)
	@Autowired
	private MemberDao dao;		//Dao의 타입이 여러개라면 @Resource(name="xxx") 으로 특정 타입을 받을 수 있다. 이름 부여는 @Repository("xxx") 어노테이션으로 빈의 이름을 부여할 수 있다.
	
	// 회원 목록 보기 요청(/member/list.do) 을 처리할 컨트롤러의 메서드
	@RequestMapping("/member/list")
	public ModelAndView list(ModelAndView mView) {
		//회원 목록을 얻어오려면?
		List<MemberDto> list=dao.getList();
		
		mView.addObject("list",list);
		mView.setViewName("member/list");	
		return mView;
	}
	
	//회원정보 삭제 요청 처리
	@RequestMapping("/member/delete")
	public String delete(@RequestParam int num) {	//int num 하면 알아서 int type으로 바꿔줌.
		//MemberDao 객체를 이용해서 회원정보 삭제
		dao.delete(num);
		//리다일렉트 응답
		return "redirect:/member/list.do";
	}
	
	@RequestMapping("/member/insertform")
	public String insertform() {
		//수행할 비즈니스 로직은 없다.
		return "member/insertform";
	}
	/*
	 * 	@ModelAttribute MemberDto dto 를 메소드 인자로 선언하면
	 * 	폼 전송되는 파라미터가 자동으로 MemberDto 객체에 setter 메서드를 통해서 들어가고
	 * 	그 객체가 메서드의 인자로 전달된다.
	 * 	단, 파라미터명과 Dto 의 필드명이 일치해야 한다.
	 */
	@RequestMapping("/member/insert")
	public ModelAndView insert(@ModelAttribute("dto") MemberDto dto, ModelAndView mView) {
		dao.insert(dto);
		/*
		 * 	@ModelAttribute("dto") MemberDto dto 의 의미는
		 * 	1. 전송되는 파라미터를 자동으로 추출해서 MemberDto 에 담아주기도 하고
		 * 	2. "dto" 라는 키값으로 MemberDto 객체를 request 영역에 담아주는 역할도 한다. =>view page에서 사용.
		 */
//		mView.addObject("dto", dto);
		mView.setViewName("member/insert");
		return mView;
	}
	
	@RequestMapping("/member/updateform")
	public ModelAndView updateform(@RequestParam int num, ModelAndView mView) {
		//수정할 회원의 정보를 얻어와서
		MemberDto dto=dao.getData(num);
		//"dto"라는 키 값으로 request 영역에 담기도록 하고
		mView.addObject("dto",dto);
		//view page 로 forward 이동해서 수정할 회원의 정보를 출력해 준다.
		mView.setViewName("member/updateform");
		return mView;
	}
	
	@RequestMapping("/member/update")
	public ModelAndView update(@ModelAttribute("dto") MemberDto dto, ModelAndView mView) {
		dao.update(dto);
//		mView.addObject("dto",dto);
		mView.setViewName("member/update");
		return mView;
	}
}
