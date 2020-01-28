package test.example1;

import test.mypac.Weapon;
import test.mypac.WeaponImpl;

public class MainClass {
	public static void main(String[] args) {
		// useWeapon() 메소드를 호출하는게 목적이라면?
		
		// 필요한 type 객체의 직접 생성해서
		WeaponImpl w1=new WeaponImpl();
		// 메소드를 호출함으로써 목적을 달성한다.
		useWeapon(w1);
	}
	
	// Weapon(인터페이스) type 을 전달해야 호출할 수 있는 메소드
	public static void useWeapon(Weapon w) {
		w.attack();
	}
}

//스프링을 사용하는 이유 : 의존관계를 느슨하게 만들어서 유지,보수 작업을 편하게 하기 위함