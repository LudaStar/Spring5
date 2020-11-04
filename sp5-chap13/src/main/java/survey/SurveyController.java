package survey;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/survey") //@RequestMapping에만 경로를 지정했기 때문에
public class SurveyController {
	
	//GET방식의 /survey 요청이 들어오면 form()메서드가 처리하고
	@GetMapping
	public String form(Model model) {
		List<Question> questions = createQuestions();
		model.addAttribute("questions", questions);
		return "survey/surveyForm";
	}
	
	/*
	 //Model을 이용해서 뷰에 전달할 데이터 설정 + 결과를 보여줄 뷰 이름을 리턴  이 두가지를 처리할 수 있는 ModelAndView
	  	@GetMapping
		public ModelAndView form(Model model) {
			ModelAndView mav = new ModelAndView();
			mav.addObject("questions", questions);
			mav.setViewName("sruvey/serveyForm");
			return mav;
	}	  
	 */
	
	//SurveyController가 Question 객체 목록을 생성해서 뷰에 전달하도록 구현 (실제론 DB에서 정보를 읽어오겠지만 이 예제는 직접 생성)
	private List<Question> createQuestions() {
		Question q1 = new Question("당신의 역할은 무엇입니까?",
				Arrays.asList("서버", "프론트", "풀스택"));
		Question q2 = new Question("많이 사용하는 개발도구는 무엇입니까?",
				Arrays.asList("이클립스", "인텔리J", "서브라임"));
		Question q3 = new Question("하고 싶은 말을 적어주세요.");
		return Arrays.asList(q1, q2, q3);
	}
	
	//POST방식의 /survey 요청이 들어오면 submit()메서드가 처리한다.
	@PostMapping
	public String submit(@ModelAttribute("ansData") AnsweredData data) {
		return "survey/submitted";
	}
}
