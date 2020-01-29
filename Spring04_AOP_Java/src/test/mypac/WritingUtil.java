package test.mypac;

import org.springframework.stereotype.Component;

/*
 * 	[ 'bean 이 된다' 의미 ]
 * 	
 *  - 해당 클래스로 객체가 생성이 되고 스프링 bean 컨테이너에서 관리가 된다.
 */
// @Component 어노테이션을 붙여 놓으면 컴포넌트 스캔을 했을 때 bean 이 된다.(스프링이 이 객체를 관리한다!)
@Component
public class WritingUtil {
	public void write1() {
		System.out.println("편지좀 써요!");
	}
	public void write2() {
		System.out.println("일기좀 써요!");
	}
	public void write3() {
		System.out.println("소설을 써요?");
	}
}
//핵심 비지니스 로직과 상관없이 write 1-3 에 각각 펜을 준비해주어야 하는데 이를 횡단 관심사라 한다.(AOP)
//특징은 WritingUtil 클래스를 건들지 않고  각 메서드 이전에 펜을 준비하는 것이 가능하다.