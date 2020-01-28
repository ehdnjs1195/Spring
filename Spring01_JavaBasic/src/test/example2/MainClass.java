package test.example2;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import test.mypac.Weapon;

public class MainClass {
	public static void main(String[] args) {
		// init.xml 문서를 해석해서 bean 을 생성한다. (WeaponImpl 객체를 생성하겠다)
		ApplicationContext context=new ClassPathXmlApplicationContext("test/example2/init.xml");	//로딩해야할 xml의 위치, 부모타입으로 받기
		
		// 스프링이 관리하고 있는 객체중에서 "myWeapon" 이라는 이름의 객체의
		// 참조값을 가지고 와서 Weapon type 으로 casting 해서 변수에 담는다.
		Weapon w1=(Weapon)context.getBean("myWeapon");	//bean을 생성하면서 bean의 이름을 적는다.  WeaponImpl이나 TestWeapon 타입으로 받게되면  import해야 하므로 의존관계를 느슨하게 하는 의미가 없어진다. => 각 클래스가 구현하고 있는 인터페이스 타입을 적극 활용한다.
		// Weapon type 객체를 이용해서 원하는 동작을 한다.
		useWeapon(w1);
	}
	public static void useWeapon(Weapon w) {
		w.attack();
	}
}

// 스프링을 사용하면 WeaponImpl 을 import하지 않고도 Weapon 인터페이스 타입을 통해서 사용할 수 있다. => WeaponImpl을 직접 new하지 않았다. => 의존하지 않았다.
// 만약 이 시점에 WeaponImpl 클래스를 수정한다면 현재 MainClass에 영향을 미칠까? 아니다 why? import해서 사용하고 있는것이 아니기 때문에. 반면 example1 에서는 영향이 생길 수 밖에 없다.
// 만약 WeaponImpl 클래스의 이름을 바꾸거나 삭제하면 example1에서는 MainClass에 오류 표시가 뜨지만 example2에서는 영향이 없다.
// 그래서 의존관계가 있는(import해서 사용하고 있는) 클래스는 뭔가 수정사항이 생기면 그 클래스 자체를 수정해야 하는 일이 생기지만, (유지&보수 관점에서 클래스 수정은 커다란 일이다.)
// spring을 이용해서 인터페이스 타입을 받아 사용하는 클래스는 xml파일만 수정해주면 된다. 


// [의존관계를 느슨하게 하는 방법]    
// 핵심 의존 객체를 직접 new 하지 않고(import하지 않고) 스프링 프레임 워크에 맞기기
// 인터페이스 타입을 적극 활용하기