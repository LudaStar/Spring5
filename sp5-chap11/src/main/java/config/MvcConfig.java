package config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer{


	@Override
	public void configureDefaultServletHandling(
			DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		registry.jsp("/WEB-INF/view/", ".jsp");
	}

	
	//step3.jsp에서의 링크가 첫 화면으로 이동하는데 이 첫 화면은 단순이 환영 문구와 회원 가입으로 이동할 수 있는 링크만 제공한다고 할 때, 이를 위한 컨트롤러 클래스는 뷰 이름만 리턴하도록 구현하는데
	//단순 연결만을 위한 컨트롤러 클래스를 만드는 것은 성가시므로 WebMvcConfigurer인터페이스의 addViewControllers메서드를 사용해서 요청경로랑 뷰 이름을 연결할 수 있다.
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/main").setViewName("main");
	}
	
}
