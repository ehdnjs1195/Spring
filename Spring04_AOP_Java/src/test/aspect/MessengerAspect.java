package test.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MessengerAspect {
	/*
	 * 	1. 리턴 type 은 상관없다(*)
	 * 	2. 메서드 명이 send 로 시작하는 메서드
	 * 	3. 메서드에 전달되는 인자의 개수도 상관 없다.(..)
	 * 
	 * 	위의 3가지 조건을 모두 만족시키는 메서드에 아래의 aop 가 적용된다.
	 */
	@Around("execution(* send*(..))")					//메서드 호출 직전 조작
	public void around(ProceedingJoinPoint joinPoint) throws Throwable {
		// aop 가 적용된 메서드 수행 직전
		System.out.println("--수행 이전--");
		
		// aop 가 적용된 메소드에 전달된 인자를 Object[] 로 얻어낼 수 있다.
		Object[] args=joinPoint.getArgs();
		// 반복문 돌면서 찾고싶은 type 을 찾는다.
		for(Object tmp:args) {
			if(tmp instanceof String) {// 만일 String type 이면   instanceof 연산자를 이용하면 그 객체의 원래 타입을 알 수 있다.	=>의문점. Object 배열인데 왜 타입을 확인하는가. =>메서드에 전달되는 인자들의 타입이 각기 다를 수 있기 때문에.
				//원래 type 으로 casting
				String msg=(String)tmp;
				System.out.println("aop 에서 읽어낸 내용: "+msg);
				if(msg.contains("idiot")) {
					System.out.println("경고: Don't say IDIOT!!");
					return; //메서드를 여기서 종료 시킨다.	=>proceed()가 수행되지 않는다.
				}
			}
		}
		
		// aop 가 적용된 메서드 수행하고 리턴되는 값 받아오기(void 면 null 이다.)
		Object obj=joinPoint.proceed();	//proceed()를 호출하면 그 메서드가 호출 된다.
		
		// aop 가 적용된 메서드 수행 직후
		System.out.println("--수행 직후--");
	}
	
	@Around("execution(String getMessage())")			//메서드 호출 직후 조작
	public Object around2(ProceedingJoinPoint joinPoint) throws Throwable {
		//aop 가 적용된 메서드를 수행하고 리턴되는 값을 얻어낸다.
		Object obj=joinPoint.proceed();
		//리턴할 값을 조작하기
		obj="뭘 공부야 그냥 놀아!";
		//리턴되는 값을 다시 리턴해주기
		return obj;
	}
}
