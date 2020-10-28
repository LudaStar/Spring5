package spring;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component //속성값을 부여하지 않았으므로 Bean의 이름이 memberRegisterService로 바뀜
public class MemberRegisterService {
	
	@Autowired
	private MemberDao memberDao;
	
	//기본 생성자 추가 -> AppCtx 클래스에서 기본 생성자를 이용해서 객체를 생성하기 위함
	public MemberRegisterService() {
		
	}

	//생성자를 통해서 의존 객체를 전달 받음
	//private MemberDao memberDao = new MemberDao(); -> 의존 객체를 직접 생성
	public MemberRegisterService(MemberDao memberDao) {
		this.memberDao = memberDao;
	}
	
	public Long regist(RegisterRequest req) {
		Member member = memberDao.selectByEmail(req.getEmail());
		
		if (member != null) {
			throw new DuplicateMemberException("dup email " + req.getEmail());
		}
		
		Member newMember = new Member(req.getEmail(), req.getPassword(), req.getName(), LocalDateTime.now());
		memberDao.insert(newMember);
		return newMember.getId();
	}
	
}
