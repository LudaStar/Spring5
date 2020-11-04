package controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import spring.RegisterRequest;

//커맨드 객체 검증과 에러 코드 지정
public class RegisterRequestValidator implements Validator{
	
	private static final String emailRegExp = 
			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
			"[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private Pattern pattern;

	public RegisterRequestValidator() {
		pattern = Pattern.compile(emailRegExp);
		System.out.println("RegisterRequestValidator#new(): " + this);
	}

	//supports 메서드는 파라미터로 전달받은 clazz 객체가 RegisterRequest 클래스로 타입 변환 가능한지 확인한다.
	@Override
	public boolean supports(Class<?> clazz) {
		return RegisterRequest.class.isAssignableFrom(clazz);
	}

	//validate 메서드는 두개의 파라미터를 갖는다. target 파라미터는 검사 대상 객체이고 errors 파라미터는 검사 결과 에러 코드를 설정하기 위한 객체
	//validate 메서드는 검사 대상 객체의 특정 프로퍼티나 상태가 올바른지 검사하고 올바르지 않다면 Errors의 rejectValue() 메서드를 이용해서 에러 코드 저장
	
	@Override
	public void validate(Object target, Errors errors) {
		System.out.println("RegisterRequestValidator#validate(): " + this);
		//검사 대상의 값을 구하기 위해 파라미터로 전달 받은 target을 실제 타입으로 변환한 뒤에 
		RegisterRequest regReq = (RegisterRequest) target;
		//검사한다.
		if (regReq.getEmail() == null || regReq.getEmail().trim().isEmpty()) {
			//해당 조건을 만족하면 에러코드로 "required"를 추가
			errors.rejectValue("email", "required"); //rejectValue(프로퍼티의 이름, 에러코드)
		} else {
			Matcher matcher = pattern.matcher(regReq.getEmail());
			if (!matcher.matches()) {
				errors.rejectValue("email", "bad");
			}
		}
		//위의 코드와 동일함 (null이거나 공백문자이면 required 추가)
		//errors 객체의 getFieldValue("name")메서드를 실행해서 커맨드 객체의 name 프로퍼티 값을 구함.
		//따라서 커맨드 객체를 직접 전달하지 않아도 값검증을 할 수 있음.
		
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "required");
		ValidationUtils.rejectIfEmpty(errors, "password", "required");
		ValidationUtils.rejectIfEmpty(errors, "confirmPassword", "required");
		if (!regReq.getPassword().isEmpty()) {
			if (!regReq.isPasswordEqualToConfirmPassword()) {
				errors.rejectValue("confirmPassword", "nomatch");
			}
		}
	}

}
