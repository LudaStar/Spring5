package spring;

import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;

public class MemberPrinter {
	
	private DateTimeFormatter dateTimeFormatter;
	
	//기본 생성자에서 dateTimeFormmater 필드의 값을 초기화
	//DateTimeFormmater 타입의 빈이 존재하지 않아도 오류가 뜨지 않음 -> setDateFormatter 메소드에 return = false를 설정해두었기 때문
	public MemberPrinter() {
		dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
	}
	
	public void print(Member member) {

		if(dateTimeFormatter == null) {
			System.out.printf(
					"회원 정보: 아이디=%d, 이메일=%s, 이름=%s, 등록일=%tF\n", 
					member.getId(), member.getEmail(),
					member.getName(), member.getRegisterDateTime());
		}
		else {
			System.out.printf("회원 정보: 아이디=%d, 이메일=%s, 이름=%s, 등록일=%s\n",
					member.getId(), member.getEmail(),
					member.getName(), dateTimeFormatter.format(member.getRegisterDateTime()));
		}
	}	
	
	//자동 주입할 빈이 없을 때의 방법 1.
	//DateTimeFormatter 타입의 빈이 존재하지 않으면 익셉션이 발생하지 않고 setDateFormatter() 메서드를 실행하지 않는다.
	
	@Autowired(required = false) //매칭되는 빈이 없어도 인셉션이 발생하지 않으며 자동 주입을 수행하지 않는다.
	//빈이 없어도 null을 전달하지 않음
	public void setDateFormatter(DateTimeFormatter dateTimeFormatter) {
		this.dateTimeFormatter = dateTimeFormatter;
	} 
	
	/*
	//방법 2.
	@Autowired
	public void setDateFormatter(Optional<DateTimeFormatter> formatterOpt) {
		if(formatterOpt.isPresent()) {
			this.dateTimeFormatter = formatterOpt.get;
		}
		else {
			this.dateTimeFormatter = null;
		}
	} */
	
	/*
	//방법 3. @Nullable 애노테이션 사용 - 자동 주입할 빈이 존재하지 않으면 인자로 null을 전달
	//-> 방법1과 다른 점은 자동 주입할 빈이 존재하지 않아도 메서드가 호출된다는 점이다.
	@Autowired

	public void setDateFormatter(@Nullable DateTimeFormatter dateTimeFormatter) {
		this.dateTimeFormatter = dateTimeFormatter;
	}
	*/
	
}
