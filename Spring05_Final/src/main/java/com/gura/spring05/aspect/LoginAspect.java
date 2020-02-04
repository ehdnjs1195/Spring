package com.gura.spring05.aspect;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

@Aspect
@Component
public class LoginAspect {

	@Around("execution(org.springframework.web.servlet.ModelAndView auth*(..))")		//ModelAndView 리턴타입, auth로 시작하는 메서드, 인자는 상관 없음
	public Object loginCheck(ProceedingJoinPoint joinPoint) throws Throwable {
		//aop 가 적용된 메소드에 전달된 값을 Object[] 로 얻어오기
		Object[] args=joinPoint.getArgs();
		for(Object tmp:args) {
			System.out.println(tmp);
		}
		//로그인 여부
		boolean isLogin=false;
		HttpServletRequest request=null;
		for(Object tmp:args) {
			//인자로 전달된 값중에 HttpServletRequest type 을 찾아서		=> auth* 메서드에서 인자로 HttpServletRequest type을 갖고 있어야 한다.
			if(tmp instanceof HttpServletRequest) {
				//원래 type 으로 casting
				request=(HttpServletRequest)tmp;
				//HttpSession 객체 얻어내기 
				HttpSession session=request.getSession();
				//세션에 "id" 라는 키값으로 저장된게 있는지 확인(로그인 여부)
				if(session.getAttribute("id") != null) {
					isLogin=true;
				}
			}
		}
		//로그인 했는지 여부
		if(isLogin){
			// aop 가 적용된 메소드를 실행하고 
			Object obj=joinPoint.proceed();		//proceed() 하면 authInfo() 메서드가 호출된다. 즉, 로그인 되어있는 상태일 때만. 아니라면 건너뛰기 때문에 authInfo() 메서드가 수행되지 못한다.
			// 리턴되는 값을 리턴해 주기 
			return obj;
		}
		//원래 가려던 url 정보 읽어오기 
		String url=request.getRequestURI();
		//GET 방식 전송 파라미터를 query string 으로 얻어오기
		String query=request.getQueryString();
		
		String encodedUrl=null;
		if(query==null) {//전달된 파라미터가 없다면 
			encodedUrl=URLEncoder.encode(url);
		}else {
			encodedUrl=URLEncoder.encode(url+"?"+query);
		}
		//ModelAndView 객체를 생성해서 	
		ModelAndView mView=new ModelAndView();
		//로그인 폼으로 리다일렉트 시키도록 view page 설정
		mView.setViewName
		("redirect:/users/loginform.do?url="+encodedUrl);
		
		//여기서 생성한 객체를 리턴해 준다. 
		return mView;
	}
}
