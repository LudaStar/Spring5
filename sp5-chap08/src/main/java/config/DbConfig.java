package config;


import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DbConfig {
	
	@Bean(destroyMethod = "close") //커넥션 풀에 보관된 Connection을 닫는다
	public DataSource dataSource() {
		DataSource ds = new DataSource();
		ds.setDriverClassName("com.mysql.cj.jdbc.mysql");
		ds.setUrl("jdbc:mysql://localhost/spring5fs?serverTimezone=Asia/Seoul&useSSL=false&characterEncoding=utf8");
		ds.setUsername("spring5");
		ds.setPassword("spring5");
		
		//Tomcat JDBC 모듈의 org.apache.tomcat.jdbc.pool.DataSource 클래스는 커넥션 풀 기능을 제공하는 DataSource 구현 클래스이다.
		//DataSource 클래스는 커넥션을 몇 개 만들 지 지정할 수 있는 메서드를 제공한다.
		ds.setInitialSize(2);  //커넥션 풀을 초기화할 때 생성할 초기 커넥션 개수를 지정한다. 기본값은 10이다.
		ds.setMaxActive(10); //커넥션 풀에거 가져올 수 있는 최대 커넥션 개수를 지정한다. 기본값은 100이다.
		
		//커넥션 풀의 커넥션이 유효한지 주기적으로 검사
		ds.setTestWhileIdle(true); //유휴 커넥션 검사
		ds.setMinEvictableIdleTimeMillis(60000 * 3); // 최소 유휴 시간 3분
		ds.setTimeBetweenEvictionRunsMillis(10 * 1000); //10초 주기
		return ds;
	}

}
