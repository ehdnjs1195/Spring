package com.gura.spring03.todo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
	
	@RequestMapping("/todo/list2") // .do는 생략 가능!(앞으로 .do는 생략하겠음!)
	public ModelAndView list2() {	// Model and View_page_info => request에 담을 모델, view page에 보낼 정보를 모두 갖는 타입. 한 번에 담을 수 있다. 
		
		//Model
		List<String> todoList=new ArrayList<>();
		todoList.add("html 공부하기");
		todoList.add("css 공부하기");
		todoList.add("javascript 공부하기");
		
		//Model 과 view page 정보를 담을 수 있는 객체 생성 
		ModelAndView mView=new ModelAndView();
		//ModelAndView 객체에 .addObject(key,value) 로 담은 객체는 자동으로 request 객체에 담긴다.
		mView.addObject("todoList", todoList);
		//view page 정보도 .setViewName() 메소드를 이용해서 담는다.
		mView.setViewName("todo/list");			//view page가 어디인지 =>/todo/list2 요청이 오면, /todo/list로 forward 응답하겠다!
		//Model 과 view page 정보가 담긴 객체를 리턴해준다.
		return mView;	// 결과적으로 파라미터로 request를 선언해 줄 필요가 없다! ModelAndView 가 알아서 담아주기 때문!
	}
}
