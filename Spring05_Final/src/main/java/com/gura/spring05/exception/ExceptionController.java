package com.gura.spring05.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/*
 * 	Exception이 발생했을 때 처리하는 컨트롤러 만들기
 * 
 * 	- @ControllerAdVice 어노테이션을 클래스에 붙인다.
 * 
 * 	- 메서드에  @ExceptionHandler(예외 class type) 을 붙여서 예외를 처리한다.
 */
@ControllerAdvice
public class ExceptionController {
	// CanNotDeleteException type 의 예외가 발생하면 호출되는 메서드.
	@ResponseStatus(HttpStatus.FORBIDDEN)	// => 웹브라우저가 403(forbidden) 에러 응답이라고 인식한다. 안 써주면 그냥 정상 응답으로 간주한다. 200번이 정상 응답이다! console에서 확인, Network에서 확인 가능.
	@ExceptionHandler(CanNotDeleteException.class)
	public ModelAndView forbidden() {	//남의 파일을 지우려 할 때 예외를 발생시킨다. 그러면 수행하던 일이 멈추고 여기로 순서가 건너 뛴다. 방법은 throw를 해준다.
		
		ModelAndView mView=new ModelAndView();
		mView.addObject("msg", "동작그만. 첫 판부터 장난질이냐?");
		mView.setViewName("error/forbidden");
		return mView;
	}	
}
