package config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import spring.ChangePasswordService;
import spring.MemberDao;
import spring.MemberInfoPrinter;
import spring.MemberListPrinter;
import spring.MemberPrinter;
import spring.MemberRegisterService;
import spring.VersionPrinter;

public class AppConf2 {
	
	@Autowired //스프링의 자동 주입 기능을 위한 것. 스프링 설정 클래스의 필드에 이 애노테이션을 붙이면 해당 타입의 Bean을 memberDao 필드에 해당
	//AppConf1 클래스에서 MemberDao타입의 Bean을 설정했으므로 그 Bean이 할당된다.
	//다른 설정 파일에 정의한 빈을 필드에 할당했다면 설정 메서드에서 이 필드를 사용해서 필요한 빈을 주입하면 된다.
	//스프링 빈에 의존하는 다른 빈을 자동으로 주입하고 싶을 때 사용
	//설정 클래스에서 Bean 메서드에서 의존주입을 위한 코드를 작성할 필요가 없다
	private MemberDao memberDao;
	@Autowired
	private MemberPrinter memberPrinter;
	
	@Bean
	public MemberRegisterService memberRegSvc() {
		return new MemberRegisterService(memberDao);
	}
	
	@Bean
	public ChangePasswordService changePwdSvc() {
		ChangePasswordService pwdSvc = new ChangePasswordService();
		pwdSvc.setMemberDao(memberDao);
		return pwdSvc;
	}
	
	@Bean
	public MemberListPrinter listPrinter() {
		//필드로 주입받은 Bean 객체를 생성자를 이용해서 주입
		return new MemberListPrinter(memberDao, memberPrinter);
	}
	
	@Bean
	public MemberInfoPrinter infoPrinter() {
		MemberInfoPrinter infoPrinter = new MemberInfoPrinter();
		infoPrinter.setMemberDao(memberDao);
		infoPrinter.setPrinter(memberPrinter);
		return infoPrinter;
	}
	
	@Bean
	public VersionPrinter versionPrinter() {
		VersionPrinter versionPrinter = new VersionPrinter();
		versionPrinter.setMajorVersion(5);
		versionPrinter.setMinorVersion(0);
		return versionPrinter;
	}

}
