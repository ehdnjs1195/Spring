package test.example3;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import test.mypac.Car;
import test.mypac.Weapon;

public class MainClass {
	public static void main(String[] args) {
		// test.example3 페이키지에 있는 init.xml 문서를 로딩해서 해당 문서에 명시된 bean 설정에 맞게 객체를 생성하고 
		// 생성된 객체는 spring bean container 에서 관리하기.
		ApplicationContext context=new ClassPathXmlApplicationContext("test/example3/init.xml");
		
		// useWeapon() 메소드를 호출하려면?
		// spring bean container 에서 관리되는 객체중에 Weapon type 의 참조값 가져오기(bean의 타입으로 가져오기. 이름X)
		Weapon w1=context.getBean(Weapon.class);	//클래스 타입을 전달하는 방법: 클래스명 혹은 인터페이스명  .class	=> Weapon.class 을 통해 Weapon type을 가져온다. 
		useWeapon(w1);
		
		// useCar() 메소드를 호출하려면?
		Car c1=context.getBean(Car.class);
		useCar(c1);
	}
	
	public static void useWeapon(Weapon w) {
		w.attack();
	}
	public static void useCar(Car c) {
		c.drive();
	}
}
