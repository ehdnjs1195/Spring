package com.gura.spring05.exception;

import org.springframework.dao.DataAccessException;
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
	
	/*
	 * 	@Repository 어노테이션이 작성된 Dao 에서 DB 관련 Exception 이 발생하면
	 * 	Spring 프레임 워크가 DataAccessException type 의 예외를 발생시킨다.
	 * 	예외 객체는 메서드의 인자로 전달되고 해당 예외 객체는 getMessage() 라는 
	 * 	getter 메서드가 존재한다.
	 *  해당 메서드를 호출하면 예외 메세지를 리턴해준다.
	 */
	@ExceptionHandler(DataAccessException.class)
	public ModelAndView dataAccess(DataAccessException dae) {
		ModelAndView mView=new ModelAndView();
		// "exception" 이라는 키값으로 예외 객체를 담는다.
		mView.addObject("exception", dae);		//=> view page에서 ${exception.getMessage()} 혹은  ${exception.message} 라고 사용할 수 있다.
		// view page 설정
		mView.setViewName("error/data_access");		
		return mView;
	}
}

//파일 삭제할 때 예외를 발생시키는 방법
// 다른 아이디로 접속한 뒤에 url에 직접 /file/delete.do?num=55 치면 된다.
