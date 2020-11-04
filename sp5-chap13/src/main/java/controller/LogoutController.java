package controller;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;

public class LogoutController {
	
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		//HttpSession 제거
		session.invalidate();
		return "redirect:/main";
	}
}
