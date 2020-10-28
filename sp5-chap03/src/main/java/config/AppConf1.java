package config;

import org.springframework.context.annotation.Bean;

import spring.MemberDao;
import spring.MemberPrinter;

public class AppConf1 {
	
	@Bean
	public MemberDao memberDao() {
		return new MemberDao();
	}
	
	@Bean
	public MemberPrinter memberPrinter() {
		return new MemberPrinter();
	}

}
