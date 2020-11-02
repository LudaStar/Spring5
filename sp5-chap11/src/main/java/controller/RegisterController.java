package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import spring.DuplicateMemberException;
import spring.MemberRegisterService;
import spring.RegisterRequest;


@Controller
public class RegisterController {


	private MemberRegisterService memberRegisterService;

	public void setMemberRegisterService(MemberRegisterService memberRegisterService) {
		this.memberRegisterService = memberRegisterService;
	} 

	@RequestMapping("/register/step1")
	public String handleStep1() {
		return "register/step1";
	}
	
	/*
	 요청 파라미터에 접근하는 방법1 -> 컨트롤러 처리 메서드의 파라미터를 HttpServletRequest 타입을 사용하고 getParameter() 메서드를 이용해서 파라미터의 값을 구하면 된다
	@PostMapping("/register/step2")
	public String handleStep2(HttpServletRequest request) {
		String agreeParam = request.getParameter("agree");
		if(agreeParam == null || !agreeParam.equals("true")) {
			return "register/step1";
		}
		return "register/step2";
	}
	*/
	

	// 요청 파라미터에 접근하는 방법2 -> @RequestParam애노테이션 : 요청 파라미터 갯수가 적을 때 간단하게 사용 가능 
	@PostMapping("/register/step2")
	public String handleStep2(
			//"agree"요청 파라미터의 값을 읽어와 agreeVal 파라미터에 할당한다. 요청 파라미터의 값이 없으면 "false" 문자열을 반환
			@RequestParam(value = "agree", defaultValue = "false") Boolean agree,
			//jsp 파일에서 form:form 태그를 사용하려면 커맨드 객체가 존재해야 하고 최초에 폼을 보여주는 요청에 대해 form:form 태그를 사용하려면 폼 표시 요청이 왔을 때에도
			//커맨드 객체를 생성해서 모델에 저장해야 한다. 
			Model model) {
		if (!agree) {
			return "register/step1";
		}
		//step.2jsp 에서 <form:form>태그를 사용하기 때문에 step1에서 step2로 넘어오는 단계에서 이름이 registerRequest인 객체를 모델에 넣어야 제대로 작동한다.
		//이렇게 model에 안 넣고 handleStep2 메서드 파라미터 Model model 대신 바로 RegisterRequest registerRequest를 추가해주어도 된다.
		model.addAttribute("registerRequest", new RegisterRequest());
		return "register/step2";
	}
	
	//컨트롤러에서 특정페이지로 리다이렉트시키는 방법 -> /register/step2 경로를 GET 방식으로 접근할 때 /register/step1 경로로 리다이렉트
	@GetMapping("/register/step2")
	public String handleStep2Get() {
		return "redirect:/register/step1";
	}
	
	@PostMapping("/register/step3")
	//RegisterRequest는 커맨드 객체로서 세터 메서드를 포함하는 객체이다.
	//스프링은 커맨드 객체의 첫 글자를 소문자로 바꾼 클래스 이름과 동일한 속성 이름을 사용해서 커맨드 객체를 뷰에 전달한다. -> step3.jsp를 보면 registerRequest.name을 볼 수 있을 것이다.
	public String handleStep3(RegisterRequest regReq) {
		try {
			memberRegisterService.regist(regReq);
			return "register/step3";
		} catch (DuplicateMemberException ex) {
			return "register/step2";
		}
	} 
	/*
	 step2.jsp에서 받은 정보를 서버에 전송하는데 폼 전송 요청을 처리하는 컨트롤러 코드는 각 파라미터의 값을 구하기 위해 
	 public String handleStep3(HttpServletRequest request) { 
	 	 String email = request.getParameter("email");
	 }
	 이런 식으로 구해야 한다. 하지만 코드의 길이가 너무 길어지므로 스프링은 요청 파라미터의 값을 커맨드 객체에 담아준다.
	 */
}
