package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
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

	public void setMemberRegisterService(
			MemberRegisterService memberRegisterService) {
		this.memberRegisterService = memberRegisterService;
	}

	@RequestMapping("/register/step1")
	public String handleStep1() {
		return "register/step1";
	}

	@PostMapping("/register/step2")
	public String handleStep2(
			@RequestParam(value = "agree", defaultValue = "false") Boolean agree,
			Model model) {
		if (!agree) {
			return "register/step1";
		}
		model.addAttribute("registerRequest", new RegisterRequest());
		return "register/step2";
	}

	@GetMapping("/register/step2")
	public String handleStep2Get() {
		return "redirect:/register/step1";
	}

	//RegisterRequestValidator.java에서  ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "required"); 이 코드에서
	//검사 대상 객체은 target을 파라미터로 전달하지 않았는데 target객체의 "name"프로퍼티의 값을 검사할까?
	//이렇게 요청 매핑 애노테이션 적용 메서드의 커맨드 객체 파라미터 뒤에 errors 타입 파라미터가 위치하면 스프링으 handler3()메서드를 호출할 때 
	//커맨드 객체와 연결된 Errors 객체를 생성해서 파라미터로 전달한다. 이 errors 객체는 커맨드 객체의 특정 프로퍼티 값을 구할 수 있는 getFieldValue()메서드를 제공
	@PostMapping("/register/step3")
	public String handleStep3(RegisterRequest regReq, Errors errors) {
		new RegisterRequestValidator().validate(regReq, errors);
		//hasErrors 메서드는  rejectValue 메서드가 한번이라도 불리면 true 리턴
		if (errors.hasErrors())
			return "register/step2";

		try {
			memberRegisterService.regist(regReq);
			return "register/step3";
		} /*catch(WrongIdPasswordException ex) {   
				errors.reject("notMatchingIdPassword"); //커맨드 객체의 특정 프로퍼티가 아닌 커맨드 객체 자체가 잘못될 수도 있다. 이럴 땐 reject메소드 사용
				return "login/loginForm;                //예를 들어 아이디와 비밀번호를 잘못 입력한 경우 메시지를 보여줘야 하므로 프로퍼티에 에러를 추가하기보다는
			}											//커맨드 객체 자체에 에러를 추가해야 한다.
		
		*/

		catch (DuplicateMemberException ex) {
			errors.rejectValue("email", "duplicate");
			return "register/step2";
		}
	}

}
