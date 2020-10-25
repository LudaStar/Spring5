package chap02;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppContext{
	
	//스프링은 객체를 생성하고 초기화하는 기능을 제공하는데, 이 부분이 그러한 설정 담고 있음. 스프링이 생성하는 객체를 Bean객체라고 부름
	@Bean //Bean애노테이션을 메서드에 붙이면 해당 메서드가 생성한 객체를 스프링이 관리하는 Bean객체로 등록
	public Greeter greeter() { //Bean객체에 대한 정보를 담고 있는 것이 greeter메서드. 메서드의 이름은 Bean객체를 구분할 때 사용
		
		Greeter g = new Greeter();
		g.setFormat("%s, 안녕하세요!"); //Greeter 객체 초기화
		return g;
	}
}
