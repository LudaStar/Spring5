package config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc //스프링 MVC 설정을 활성화, 내부적으로 다양한 빈 설정을 추가해준다.
public class MvcConfig implements WebMvcConfigurer { //WebMvcConfigurer인터페이스는 스프링 MVC의 개별 설정으러 조정할 때 사용한다.

	//DispatcherServlet의 매핑경로를 /로 주었을 때, jsp/html/css등을 올바르게 처리하기 위한 설정을 추가한다.
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
	
	//jsp를 이용해서 컨트롤러의 실행 결과를 보여주기  위한 설정을 추가한다.
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		//registry.jsp()코드는 JSP를 뷰 구현으로 사용할 수 있도록 해주는 설정
		//첫 번째 인자는 JSP파일 경로를 사용할 접두어이며 두 번째 인자는 접미사이다.
		registry.jsp("/WEB-INF/view/", ".jsp");
	}

}