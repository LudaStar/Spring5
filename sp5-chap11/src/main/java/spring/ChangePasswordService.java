package spring;

import org.springframework.transaction.annotation.Transactional;

public class ChangePasswordService {
	
	private MemberDao memberDao;

	//@Transactional 애노테이션이 붙은 메서드를 통일한 트랜잭션 범위에서 실행
	//--> memberDao.selectByEmail()에서 실행하는 쿼리와 member.changePassword()에서 실핸하는 쿼리는 한 트랜잭션에 묶인다.
	@Transactional
	public void changePassword(String email, String oldPwd, String newPwd) {
		Member member = memberDao.selectByEmail(email);
		if (member == null)
			throw new MemberNotFoundException();

		member.changePassword(oldPwd, newPwd);

		memberDao.update(member);
	}

	public void setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
	}

}
