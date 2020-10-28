package config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import spring.MemberPrinter;
import spring.MemberSummaryPrinter;
import spring.VersionPrinter;

@Configuration 
@ComponentScan(basePackages = {"spring"}) //@Component 애노테이션을 붙인 클래스를 스캔해서 객체를 생성하고 스프링 빈으로 등록
public class AppCtx {

	@Bean
	@Qualifier("printer") // 자동 주입한 빈이 두개 이상일 때 @Qualifier 애노테이션이 자동 주입 대상 빈을 한정할 수 있다.
	//해당 빈의 한정 값으로 "printer"를 지정 
	public MemberPrinter memberPrinter1() {
		return new MemberPrinter();
	}

	@Bean
	@Qualifier("summaryPrinter")
	public MemberSummaryPrinter memberPrinter2() {
		return new MemberSummaryPrinter();
	}

	
	@Bean
	public VersionPrinter versionPrinter() {
		VersionPrinter versionPrinter = new VersionPrinter();
		versionPrinter.setMajorVersion(5);
		versionPrinter.setMinorVersion(0);
		return versionPrinter;
	} 
}
