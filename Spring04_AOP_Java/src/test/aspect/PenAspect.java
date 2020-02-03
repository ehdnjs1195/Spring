package test.aspect;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect		//aspect 역할을 할 수 있도록
@Component	//bean 이 될 수 있도록
public class PenAspect {
	/*
	 * 	spring 이 관리하는 객체의 메서드 중에서 
	 * 	(1)리턴type 은 void, (2)메서드명은 write
	 * 	메서드에 전달되는 인자가 없는 메서드의((3)인자의 유무)
	 *  수행 이전에 할 작업.
	 *  
	 *  aspectj expression(Before에 들어가는 문법! 공부는 1시간이면 충분!)
	 */
	@Before("execution(void write*())")	//스프링이 관리하고 있는 객체 메서드들 중에서 "execution(xxx)"모양의 갖고 있는 메서드가 수행되기 이전에! 이것을 적용해라. 이런 뜻!
	public void prepare() {
		System.out.println("Pen 을 준비해요!");
	}	//before는 해당 메서드가 호출될 때 바로 직전에 적용. after는 해당 메서드가 return 되고난 바로 직후에 적용.
	
//	@After("execution(void write*())")
//	public void end() {
//		System.out.println("다쓰고 버려요.");
//	}
}
