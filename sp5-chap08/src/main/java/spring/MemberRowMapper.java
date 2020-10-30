package spring;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

//동일한 RowMapper 구현을 여러 곳에서 사용한다면 아래 코드처럼 RowMapper 인터페이스를 구현한 클래스를 만들어 중복을 막을 수 있다.
public class MemberRowMapper implements RowMapper<Member> {
	
	private JdbcTemplate jdbcTemplate;

	@Override
	public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
		Member member = new Member(
				rs.getString("EMAIL"),
				rs.getString("PASSWORD"),
				rs.getString("NAME"),
				rs.getTimestamp("REGDATE").toLocalDateTime());
		member.setId(rs.getLong("ID"));
		return member;
	}
	
	public Member selectByEmail(String email) {
		List<Member> results = jdbcTemplate.query("select * from MEMBER where EMAIL=?",
				new MemberRowMapper(), email);
		
		return results.isEmpty()? null : results.get(0);
	}
	
	public Collection<Member> selectAll() {
		List<Member> results = jdbcTemplate.query("select*from MEMBER", new MemberRowMapper());
		return results;
	}

}
