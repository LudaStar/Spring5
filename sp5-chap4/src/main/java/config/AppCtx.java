package config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import spring.ChangePasswordService;
import spring.MemberDao;
import spring.MemberInfoPrinter;
import spring.MemberListPrinter;
import spring.MemberPrinter;
import spring.MemberRegisterService;
import spring.MemberSummaryPrinter;
import spring.VersionPrinter;

@Configuration 
public class AppCtx {

	@Bean
	public MemberDao memberDao() {
		return new MemberDao();
	}
	
	@Bean
	public MemberRegisterService memberRegSvc() {
		//return new MemberRegisterService(memberDao());
		return new MemberRegisterService();
	}
	
	@Bean
	public ChangePasswordService changePwdSvc() {
		//ChangePasswordService pwdSvc = new ChangePasswordService();
		//pwdSvc.setMemberDao(memberDao());
		
		//의존을 주입하지 않아도 스프링이 @Autowired가 붙인 필드에 해당 타입의 빈 객체를 찾아서 주입한다.
		//ChangePasswordService.java 파일에서 Memberdao에 @Autowired붙임

		//return pwdSvc;		
		return new ChangePasswordService();
	}
	
	@Bean
	@Qualifier("printer") // 자동 주입한 빈이 두개 이상일 때 @Qualifier 애노테이션이 자동 주입 대상 빈을 한정할 수 있다.
	//해당 빈의 한정 값으로 "printer"를 지정 
	public MemberPrinter memberPrinter1() {
		return new MemberPrinter();
	}
	
	//@Bean
	//public MemberPrinter memberPrinter2() {
	//	return new MemberPrinter();
	//}
	
	@Bean
	@Qualifier("summaryPrinter")
	public MemberSummaryPrinter memberPrinter2() {
		return new MemberSummaryPrinter();
	}
	//memberPrint2 빈의 타입을 변경했음에도 에러가 나는 이유는(Qualifier 애노테이션 없앴을 때)
	//MemberSummaryPrinter가 MemberPrinter 클래스를 상속했기 때문이다.
	
	@Bean
	public MemberListPrinter listPrinter() {
		//return new MemberListPrinter(memberDao(), memberPrinter());
		return new MemberListPrinter();
	}
	/*
	@Bean
	public MemberInfoPrinter infoPrinter() {
		MemberInfoPrinter infoPrinter = new MemberInfoPrinter();
		//infoPrinter.setMemberDao(memberDao());
		//infoPrinter.setPrinter(memberPrinter());
		
		//MemberInfoPrinter.java에서 setMemberDao 메서드에 @Autowired를 붙였기 때문에 생략 가능한 것
		
		//return infoPrinter;
		return new MemberInfoPrinter();
	} */
	
	@Bean
	public MemberInfoPrinter infoPrinter() {
		MemberInfoPrinter infoPrinter = new MemberInfoPrinter();
		infoPrinter.setPrinter(memberPrinter2());
		//memberPrinter2를 주입했지만 결과는 memberPrint1이 주입 되었다. 
		//이는  MemberInfoPrinter class에서 @Qualifier("printer")를 설정했기 때문에 같은 한정자를 가진 memberPrinter1이 주입된 것이다.
		
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
