package com.gura.spring03.todo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

//1. Controller 어노테이션(이게 상속이나 구현 등의 작업을 할 필요없게 해준다.)
@Controller
public class TodoController {

	//2. RequestMapping 어노테이션으로 요청 맵핑하기
	@RequestMapping("/todo/list.do")
	public String todo(HttpServletRequest request) {
		
		//3. 할일 목록을 얻어오는 비즈니스 로직 처리하기
		List<String> todoList=new ArrayList<>();
		todoList.add("html 공부하기");
		todoList.add("css 공부하기");
		todoList.add("javascript 공부하기");
		//4. 비즈니스 로직 처리 결과 모델을 request 에 담기
		request.setAttribute("todoList", todoList);
		//5. view page 의 정보를 리턴하면 해당페이지로 forward 이동으로 응답이 된다.
		return "/todo/list";
	}
}
