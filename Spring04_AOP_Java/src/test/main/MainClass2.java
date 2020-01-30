package test.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import test.mypac.Messenger;

public class MainClass2 {
	public static void main(String[] args) {
		// init.xml 문서를 로딩해서 bean 으로 만들것들은 만들어서 관리한다.
		ApplicationContext context=new ClassPathXmlApplicationContext("test/main/init.xml");	//어디에 있는 xml 문서를 해석할지.
		Messenger m=context.getBean(Messenger.class);
		m.sendGreeting("Good Morning~!");
		m.sendGreeting("Super idiot! Great Morning!");
	}
}
