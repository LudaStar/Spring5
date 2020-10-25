package chap02;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main2 {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppContext.class);
		
		Greeter g1 = ctx.getBean("greeter", Greeter.class);
		Greeter g2 = ctx.getBean("greeter", Greeter.class);
		//g1과 g2는 같은 객체임
		//별도의 설정을 하지 않으면 스프링은 한 개의 Bean객체만을 생성. 이 때 빈 객체는 '싱글톤 범위를 갖는다'고 표현
		//싱글톤은 한 개의 Bean애노테이션에 대해 한 개의 빈 객체를 생성
		System.out.println("(g1 == g2) = " + (g1 == g2));
		ctx.close();

	}

}
