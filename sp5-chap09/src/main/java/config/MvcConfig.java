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
		//DefaultServletHandlerConfigurer의 enable()메서드는 두 개의 빈 객체를 추가한다
		// 1. DefaultServletHttpRequestHandler는 클라이언트의 모든 요청을 WAS가 제공하는 디폴트 서블릿에 전달한다.
		// 2. SimpleUrlHandlerMapping을 이용해서 모든 경로를 DefaultServletHttpRequestHandler를 이용해서 처리하게 한다.
		configurer.enable();
	}
	
	/*
	 DispatcherServlet의 요청 처리 방식
	 1. RequestMappingHandlerMapping을 사용해서 요청을 처리할 핸들러를 검색한다. 존재하면 해당 컨트롤러를 이용해서 요청을 처리한다.
	 2. 존재하지 않으면 SimpleUrlHandlerMapping을 사용해서 요청을 처리할 핸들러를 검색한다.
	 DefaultServletHandlerConfigurer#enable() 메서드가 등록한 SimpleUrlHandlerMapping은 모든 경로에 대해 DefaultServletHttpRequestHandler를 리턴
	 DispatcherServlet은 DefaultServletHttpRequestHandler에 처리를 요청한다.
	 DefaultServletHttpRequestHandler는 디폴트 서블릿에 처리를 위임한다.
	 */
	
	
	/*
	jsp를 이용해서 컨트롤러의 실행 결과를 보여주기  위한 설정을 추가한다.
	@EnableWebMvc 애노테이션을 사용하여 WebMvcConfigurer타입인 빈 객체의 메서드를 호출해서 MVC 설정을 추가
	예를 들어 ViewResolver설정을 추가하기 위해  WebMvcConfigurer 타입인 빈 객체의 configrueViewResolvers() 메서드를 호출 */
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		//registry.jsp()코드는 JSP를 뷰 구현으로 사용할 수 있도록 해주는 설정
		//첫 번째 인자는 JSP파일 경로를 사용할 접두어이며 두 번째 인자는 접미사이다.
		registry.jsp("/WEB-INF/view/", ".jsp");
	}

}