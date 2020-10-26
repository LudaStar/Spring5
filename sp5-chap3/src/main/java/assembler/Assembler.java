package assembler;

import spring.ChangePasswordService;
import spring.MemberDao;
import spring.MemberRegisterService;

//회원 가입이나 암호 변경 기능을 제공하는 클래스의 객체를 생성하고 의존대상이 되는 객체를 주입해주는 클래스
public class Assembler {
	
	private MemberDao memberDao;
	private MemberRegisterService regSvc;
	private ChangePasswordService pwdSvc;

	//Assembler 생성자에서 객체를 생성하고 의존 주입
	public Assembler() {
		memberDao = new MemberDao();
		//MemberRegisterService 객체는 생성자를 통해 memberDao 객체를 주입 받음
		regSvc = new MemberRegisterService(memberDao);
		//ChangePasswordService 객체는 세터를 통해 주입받음
		pwdSvc = new ChangePasswordService();
		pwdSvc.setMemberDao(memberDao);
	}

	//Assembler는 자신이 생성하고 조립한 객체를 리턴하는 메서드를 제공한다.
	public MemberDao getMemberDao() {
		return memberDao;
	}

	public MemberRegisterService getMemberRegisterService() {
		return regSvc;
	}

	public ChangePasswordService getChangePasswordService() {
		return pwdSvc;
	}

}
