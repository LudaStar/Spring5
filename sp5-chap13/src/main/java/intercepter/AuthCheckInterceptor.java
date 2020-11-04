package intercepter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

public class AuthCheckInterceptor implements HandlerInterceptor{

	@Override
	//preHandle()메서드는 HttpSession에 "authInfo"속성이 존재하면 true를 리턴한다
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response,Object handler) throws Exception {
		
		HttpSession session = request.getSession(false);
		if (session != null) {
			Object authInfo = session.getAttribute("authInfo");
			if (authInfo != null) {
				return true;
			}
		}
		//로그인 상태가 아니므로 리다이렉트
		response.sendRedirect(request.getContextPath() + "/login"); //request.getContextpath()메서드는 현재 컨텍스트 경로를 리턴
		return false;
	}
}
