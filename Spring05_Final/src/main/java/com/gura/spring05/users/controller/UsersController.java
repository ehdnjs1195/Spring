package com.gura.spring05.users.controller;

import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	
	@RequestMapping(value = "/users/signup", method = RequestMethod.POST )	//form 전송은 가리겠다. GET방식 요청을 하게되면 얘가 처리를 안한다. method를 명시 해두지 않으면 post 나 get 모두 받는다.  post방식으로 요청이 왔을 때만 처리하겠다. 만약 주소창에 직접 /users/signup.do 를 치면 (GET방식) 405페이지가 뜨게 된다.
	public ModelAndView signup(@ModelAttribute("dto") UsersDto dto, ModelAndView mView) {
		service.addUser(dto);
		mView.setViewName("users/insert");
		return mView;
	}
	
	//로그인 폼 요청 처리
	@RequestMapping("/users/loginform")
	public String loignform(HttpServletRequest request) {
		// "url" 이라는 파라미터가 넘어오는지 읽어와 본다.
		String url=request.getParameter("url");
		if(url==null){//만일 없으면  (url이 null인 경우는 필터를 거치지 않고 loginform.jsp로 왔을 때이다. ex) index.jsp에서 로그인 버튼 눌렀을 때)
			//로그인 성공후에 index.jsp 페이지로 보낼 수 있도록 구성한다.
			url=request.getContextPath()+"/";	//home.do로 감.
		}
		
		//아이디, 비밀번호가 쿠키에 저장되었는지 확인해서 저장되었으면 폼에 출력한다.
		Cookie[] cookies=request.getCookies();
		//저장된 아이디와 비밀번호를 담을 변수 선언하고 초기값으로 빈 문자열 대입
		String savedId="";
		String savedPwd="";
		if(cookies != null){
			for(Cookie tmp:cookies){
				if(tmp.getName().equals("savedId")){
					savedId=tmp.getValue();
				}else if(tmp.getName().equals("savedPwd")){
					savedPwd=tmp.getValue();
				}
			}
		}
		request.setAttribute("url", url);
		request.setAttribute("savedId", savedId);
		request.setAttribute("savedPwd", savedPwd);
		return "users/loginform";
	}
	
	//로그인 요청 처리
	@RequestMapping(value = "/users/login", method=RequestMethod.POST)
	public ModelAndView login(@ModelAttribute UsersDto dto, ModelAndView mView,
			HttpServletRequest request,
			HttpServletResponse response) {
		//목적지 정보
		String url=request.getParameter("url");
		if(url==null){
			url=request.getContextPath()+"/home.do";
		}
		//목적지 정보를 미리 인코딩 해놓는다.
		String encodedUrl=URLEncoder.encode(url);
		//view page 에 전달하기
		mView.addObject("url",url);
		mView.addObject("encodedUrl",encodedUrl);
		
		//4. 아이디 비밀번호 저장 체크박스를 체크 했는지 읽어와 본다.
		String isSave=request.getParameter("isSave");
		//아이디, 비밀번호를 쿠키에 저장하기
		Cookie idCook=new Cookie("savedId", dto.getId());
		Cookie pwdCook=new Cookie("savedPwd", dto.getPwd());
		if(isSave !=null){	//null 이 아니면 체크한 것이다.
			idCook.setMaxAge(60*60*24*30); //한 달.
			pwdCook.setMaxAge(60*60*24*30);
			
		}else{
			idCook.setMaxAge(0);	//MaxAge를 0로 해두면 쿠키가 지워진다.
			pwdCook.setMaxAge(0);
		}
		//응답할 때 쿠키도  심어지도록
		response.addCookie(idCook);
		response.addCookie(pwdCook);	//response에 담아두면 view page로 넘어갈 때 같이 넘어간다.
		
		service.validUser(dto, request.getSession(), mView);	//request를 통해 session을 얻어낸다.
		
		mView.setViewName("users/login");	
		return mView;
	}
}
