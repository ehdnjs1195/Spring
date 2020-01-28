package com.gura.spring02;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/*
 * 	@Controller 어노테이션
 * 	- 해당 클래스가 Spring MVC 프로젝트에서 Controller 가 될 수 있도록 한다.
 *  - component scan 을 통해서 spring bean container 에서 관리가 되는 bean 이 되어야 동작을 한다.
 * 	- 
 */

	//마치 service 메서드처럼 이 쪽으로 실행순서가 들어온다.
@Controller
public class HomeController {
	
	// /home.do 요청이 왔을 때 요청을 처리하게 하는   @RequestMapping 어노테이션
	@RequestMapping("/home.do")
	public String home() {
		/*
		 * 	"home" 을 리턴해주면 
		 * 
		 * 	"/WEB-INF/views/" + "home" + ".jsp" 와 같은 문자열이 만들어 지고 
		 * 
		 * 	/WEB-INF/views/home.jsp 페이지로 forward 이동되어서 응답된다.
		 */
		return "home";	//여기서 home은 ↑위에 경로에서의 home을 나타낸다.
	}
	
}
