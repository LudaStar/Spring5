package chap09;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

//웹 요청을처리하고 결과를 뷰에 전달하는 빈 객체. @GetMapping애노테이션이나 @PostMapping과 같은 애노테이션으로 처리할 경로를 지정해주어야 한다.
@Controller
public class HelloController {

	//메서드가 처리할 요청 경로를 지정한다.
	@GetMapping("/hello") // "/hello"경로로 들어온 요청을 hello 메서드를 이용해서 처리
	
	public String hello(Model model, //model 파라미터는 컨트롤러의 처리 결과를 뷰에 전달할 때 사용한다.
			
			//@RequestParam 애노테이션은 HTTP 요청 파라미터의 값을 메서드의 파라미터로 전달할 때 사용된다.
			@RequestParam(value = "name", required = false) String name) {  //name요청 파라미터값을 name 파라미터에 전달한다.
		
		//greeting이라는 모델 속성에 값을 설정. 두번째 파라미터는 값. JSP코드는 이 속성을 이용해서 값을 출력
		model.addAttribute("greeting", "안녕하세요, " + name);
		
		//컨트롤러의 처리 결과를 보여줄 뷰 이름이 'hello' 뷰 구현을 찾아주는 것은 ViewResolver가 처리
		return "hello";
	}
	/*
	@GetMapping 애노테이션 값은 서블릿 경로를 기준으로 하는데 톰캣의 경우 webapps\sp5-chap09폴더는 웹브라우저에서 http://host/sp5-chap09경로에 해당
	따라서 우리가 지정한 경로는 http://host/sp5-chap09/hello 인 것
	
	
	@RequestParam 애노테이션의 value속성은 HTTP요청 파라미터의 이름이고, required속성은 필수 여부


	Model 객체의 addAttribute() 메서드는 뷰에 전달할 데이터를 지정하기 위해 사용된다.
	첫 번째 파라미터는 데이터를 식별하는데 사용되는 속성 이름, 두 번째 파라미터는 속성 이름에 해당하는 값이다. 뷰 코드는 이 속성 이름을 사용해서 컨트롤러가 전달한 데이터에 접근한다.
	
	
	<핸들러가 만들어서 전달하는 스프링에서 받을 수 있는 객체>
	1. HttpSession
	request 객체에서 얻을 수 있지만 세션 객체만 필요한 경우라면 파라미터로 바로 받을 수 있습니다. 
 

	2. Locale
	java.util.Locale 클래스의 객체입니다. 
	지리적, 국가적, 문화적 지역을 다루는 클래스로 지역적 분류에 따라 여러 가지 처리를 자동으로 해줍니다. 
	대표적으로 시간 표현 등이 있습니다.

 
	3. Model
	스프링에서 제공해주는 데이터 공유 객체입니다. 
	스프링 이전에 사용하던 방식대로 request, session 등의 객체를 사용해도 되지만 Model 객체를 더 많이 사용하는 것 같습니다. 
	사용법은 거의 비슷하고, request와 같이 하나의 요청 처리에 국한된 생명주기를 가집니다. 
	
	
	*/
}
