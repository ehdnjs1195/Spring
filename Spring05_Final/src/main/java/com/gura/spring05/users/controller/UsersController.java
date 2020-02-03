package com.gura.spring05.users.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.gura.spring05.users.dto.UsersDto;
import com.gura.spring05.users.service.UsersService;

@Controller
public class UsersController {
	@Autowired
	private UsersService service;
	
	//회원 가입 폼 요청 처리
	@RequestMapping("/users/signup_form")
	public String signup_form() {
		return "users/signup_form";
	}
	/*
	 * 	[ JSON 문자열 응답하는 방법 ]
	 * 	1. pom.xml 에 jackson-databind dependency 명시
	 * 	2. controller 의 메소드에 @ResponseBody 어노테이션 붙이기
	 * 	3. List, Map, Dto 중에 하나를 리턴한다.
	 */
	@ResponseBody
	@RequestMapping("/users/checkid")	//http://localhost:8888/spring05/users/checkid.do?inputId=123 직접 url에 쳐보면 json이 출력되는 것을 확인할 수 있다.
	public Map<String, Object> checkid(@RequestParam String inputId){
		Map<String, Object> map=service.isExistId(inputId);
		return map;
	}
	
	@RequestMapping(value = "/users/signup", method = RequestMethod.POST )	//form 전송은 가리겠다. GET방식 요청을 하게되면 얘가 처리를 안한다. method를 명시 해두지 않으면 post 나 get 모두 받는다.  post방식으로 요청이 왔을 때만 처리하겠다. 만약 주소창에 직접 /users/signup.do 를 치면 (GET방식) 404페이지가 뜨게 된다.
	public ModelAndView signup(@ModelAttribute("dto") UsersDto dto, ModelAndView mView) {
		service.addUser(dto);
		mView.setViewName("users/insert");
		return mView;
	}
}
