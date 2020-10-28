package spring;

public class MemberSummaryPrinter extends MemberPrinter{
	
	@Override
	public void print(Member member) {
		System.out.printf("회원정보 : 이메일  = %s, dlfma = %s \n", member.getEmail(), member.getName());
	}

}
