package spring;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public class MemberDao {
	
	private JdbcTemplate jdbcTemplate;
	
	
	//JdbcTemplate 객체 생성하려면 DataSource를 생성자에 전달
	public MemberDao(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	
	public Member selectByEmail(String email) {
		
		//query 메서드는 sql 파라미터로 전달받은 쿼리를 실행하고, RowMapper를 이용해서 ResultSet의 결과를 자바 객체로 변환한다.
		//sql 파라미터가 인덱스 기반 파라미터를 가진 쿼리 이므로 List<T> query(String sql, RowMapper<T> rowMapper, object args) 사용
		//인덱스가 두 개 이상이면 콤마로 구분
		List<Member> results = jdbcTemplate.query("SELECT * FROM MEMBER WHERE EMAIL=?", 
				new RowMapper<Member>() {
			
			@Override
			//RowMapper의 mapRow 메서드는 SQL 실행결과로 구한 ResultSet에서 한 행의 데이터를 읽어와 자바 객체로 변환하는 매퍼 기능을 구현. Member객체 생성해서 리턴
			public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				Member member = new Member(
						rs.getString("EMAIL"),
						rs.getString("PASSWORD"),
						rs.getString("NAME"),
						rs.getTimestamp("REGDATE").toLocalDateTime());
				
				member.setId(rs.getLong("ID"));
				return member;
			}
		},
				email);
		//query 메서드는 쿼리를 실행한 결과가 존재하지 않으면 길이가 0인 List를 리턴하므로 List가 비어있는지 여부로 결과가 존재하지 않는지 확인할 수 있다.
		return results.isEmpty() ? null : results.get(0);
	}
	
		
		
		//실행결과가 1개이며 queryForObject()로 읽어오고 싶을 때
		//리턴 타입이 List가 아니라 RowMapper로 변환해주는 타입(Member)
		public Member selectByID(String email) {
			Member member = jdbcTemplate.queryForObject("SELECT * FROM MEMBER WHERE ID=?", 
				new RowMapper<Member>() {
			
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
			}, 100); 
			return member;
	}
	
	
	// 람다 사용하여 간단하게 표현
	public List<Member> selectAll() {
		List<Member> results = jdbcTemplate.query("select * from MEMBER",
				(ResultSet rs, int rowNum) -> {
					Member member = new Member(
							rs.getString("EMAIL"),
							rs.getString("PASSWORD"),
							rs.getString("NAME"),
							rs.getTimestamp("REGDATE").toLocalDateTime());
					
					member.setId(rs.getLong("ID"));
					return member;
				});
		return results;
	} 
	
	public int count() {
		//결과가 1행인 경우에 사용할 수 있는 queryForObject메서드. 두 번째 파라미터는 칼럼을 읽어올 때 사용할 타입
		Integer count = jdbcTemplate.queryForObject(
				"select count(*) from MEMBER", Integer.class);
		return count;
		
		/*queryForObect메서드도 인덱스 파라미터(물음표)사용이 가능하다
		double avg = queryForObect(
				"select avg(height) from FURNITURE where TYPE=? and STATUS=?", Double.class, 100, "S");
		*/
	}
	
	
	public void update(Member member) {
		jdbcTemplate.update(
				"update MEMBER set NAME = ?, PASSWORD = ? where EMAIL = ?",
				member.getName(), member.getPassword(), member.getEmail());
	}
	
	public void insert(Member member) {
		//KeyHolder를 사용하여 자동으로 생성된 키값을 알 수 있다. 테이블에 ID는 auto_increment로 지정했으므로 이 값을 알고 싶음
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		//update메서드는 PreparedStatementCreator 객체와 KeyHolder 객체를 파라미터로 갖는다
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				// 파라미터로 전달받은 Connection을 이용해서 PreparedStatement 생성
				//PreparedStatement객체의 두 번째 파라미터는 String 배열인{"ID"}이다 이 두 번째 파라미터는 자동 생성되는 키 칼럼 목록을 지정할 때 사용
				PreparedStatement pstmt = con.prepareStatement(
						"insert into MEMBER (EMAIL, PASSWORD, NAME, REGDATE) " +
						"values (?, ?, ?, ?)",
						new String[] { "ID" });
				//PreparedStatement의 set메서드를 사용해서 직접 인덱스 파라미터의 값을 설정
				pstmt.setString(1, member.getEmail());
				pstmt.setString(2, member.getPassword());
				pstmt.setString(3, member.getName());
				pstmt.setTimestamp(4,Timestamp.valueOf(member.getRegisterDateTime()));
				// 생성한 PreparedStatement 객체 리턴
				return pstmt;
			}
		}, keyHolder); //update메서드는 PreparedStatement를 실행한 후 자동 생성된 키 값을 KeyHolder에 보관한다
		Number keyValue = keyHolder.getKey(); //KeyHolder에 보관된 키값을 getKey()메서드로 구한다. 이 메서드는 java.lang.Number를 리턴
		member.setId(keyValue.longValue());//longValue()를 이용해서 키 값을 Long으로 바꾸었다
		

	}
	//insert메서드 람다식 사용
	public void insert2(Member member) {
		//KeyHolder를 사용하여 자동으로 생성된 키값을 알 수 있다. 테이블에 ID는 auto_increment로 지정했으므로 이 값을 알고 싶음
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		jdbcTemplate.update((Connection con) -> {
			PreparedStatement pstmt = con.prepareStatement(
					"insert into MEMBER (EMAIL, PASSWORD, NAME, REGDATE) " +"values (?, ?, ?, ?)", new String[] { "ID" });
			
			pstmt.setString(1, member.getEmail());
			pstmt.setString(2, member.getPassword());
			pstmt.setString(3, member.getName());
			pstmt.setTimestamp(4,Timestamp.valueOf(member.getRegisterDateTime()));
			// 생성한 PreparedStatement 객체 리턴
			return pstmt;
			
	}, keyHolder);
		
	}

}
