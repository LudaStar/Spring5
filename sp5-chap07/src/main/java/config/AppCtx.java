package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import aspect.ExeTimeAspect;
import chap07.Calculator;
import chap07.RecCalculator;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true) //인터페이서그 아닌 자바 클래스를 상속받아 프록시를 생성하려면 true로 설정해주어야 한다.
//@Aspect을 붙인 클래스를 공통기능으로 적용하려면 @EnableAspectJAutoProxy 애노테이션을 설정 클래스에 붙여야 함
//스프링이 @Aspect 애노테이션이 붙인 Bean객체를 찾아서 @Pointcut과 @Around 설정을 사용한다.
public class AppCtx {

	@Bean
	public ExeTimeAspect exeTimeAspect() {
		return new ExeTimeAspect();
	}

	@Bean
	public Calculator calculator() {
		return new RecCalculator();
	}

}
