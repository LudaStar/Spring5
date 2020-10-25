package chap02;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
//AnnotationConfigApplicationContext클래스는 자바 클래스에서 정보를 읽어와 객체 생성과 초기화를 수행
public class Main {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppContext.class); //Bean객체 생성
		//객체를 생성할 때 AppContext 클래스를 생성자 파라미터로 전달. AppContext에 정의한 @Bean 설정 정보를 읽어와 Greeter 객체를 생성하고 초기화
		Greeter g = ctx.getBean("greeter", Greeter.class); //Bean객체 제공
		//GetBean메서드는 Bean객체를 검색할 때 사용. 첫 파라미터는 Bean객체의 이름, 두번째 파라미터는 검색할 Bean객체의 타입
		String msg = g.greet("스프링");
		System.out.println(msg);
		ctx.close();
	}
}
