package net.songecom.tempwork.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import net.songecom.tempwork.model.StudentDto;

@Repository //bean 주입
public class StudentDaoImpl implements StudentDao {
	
	//jdbc 템플릿 가져오기 (변수 지정)
	private JdbcTemplate template;  //(dataSource)넣어 데이터 초기화한다
	
	//생성자 만들기
	public StudentDaoImpl(DataSource dataSource) {
		this.template = new JdbcTemplate(dataSource); //커넥터 이용해 접속정보 이용했는데, jdbcTemplate에 넣어서 이용 (위에다 선언하고 필드 초기화 방법으로 이용)
	}
	
	//sql insert
	@Override
	public int create(StudentDto dto) {
		
		int rs = 0; //리턴값 
		String sql = "insert into student(stu_name, stu_email, stu_course) values (?,?,?)"; //sql 그동안 쓴 구문과 같고 id는 자동생성이라 뺌
		
		try {
			rs = template.update(sql, 
					new Object[] {  //배열로 리턴값 
						dto.getStu_name(),
						dto.getStu_email(),
						dto.getStu_course()
			});  //리턴값 변경구문
		}catch(Exception e) {
			e.printStackTrace();  //에러처리만 하면 됨 - prepare..이거가 jdbc가 대행해줌
		}
		
		return rs;  //리턴값 
	}
	
	//업데이트 쿼리문
	@Override
	public int update(StudentDto dto) {
		
		String sql = "update student set stu_name=?, stu_email=?, stu_course=? where stu_id = ?";  //insert 쿼리랑 똑같
		try {
			int rs = template.update(sql, new Object[] {
				dto.getStu_name(),
				dto.getStu_email(),
				dto.getStu_course(),
				dto.getStu_id()
			});
			return rs;			
		}catch(Exception e) {
			e.printStackTrace();
			return 0; //쿼리문리턴
		}
		
	}
	
	//삭제
	@Override
	public int delete(int studentId) {
		String sql = "delete from student where stu_id=?";
		try {
			//update insert delete = update, Object타입으로 배열로받기위해[]
			int rs = template.update(sql, new Object[] {studentId} );
			return rs;
			
		}catch(Exception e) {
			return 0;
		}
		
	}

	@Override
	public List<StudentDto> read() {
		String sql = "select * from student order by stu_id desc";
		List<StudentDto> stList = template.query(sql, new RowMapper<StudentDto>() {  //Rowmapper는 array리스트로 되어있는것 루프돌려 출력하는것 

			@Override
			public StudentDto mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				//dto에 한줄이 담겨서 리스트에 담기고~ -> List<>타입으로 배열로 한줄씩 모두 담김
				StudentDto dto = new StudentDto(); 
				dto.setStu_id(rs.getInt("stu_id"));
				dto.setStu_name(rs.getString("stu_name"));
				dto.setStu_email(rs.getString("stu_email"));
				dto.setStu_course(rs.getString("stu_course"));
				return dto;
			}
			
		});
		return stList;  //배열로 한줄씩 받은값 변수 위에 stList로 지정되어 리턴
	}
	
	//세부정보 보기 (하나씩보기 = dto에 담아 빼기)
	@Override
	public StudentDto findStudentById(int studentId) {
		
		String sql = "select * from student where stu_id = ?";
		try {
		StudentDto stView = template.queryForObject(
				sql,
				new Object[] {studentId} , 
				new RowMapper<StudentDto>() {  //query말고 queryForObject로 써도 무관 (에러뜸)

			@Override
			public StudentDto mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				StudentDto dto = new StudentDto(); 
				dto.setStu_id(rs.getInt("stu_id"));
				dto.setStu_name(rs.getString("stu_name"));
				dto.setStu_email(rs.getString("stu_email"));
				dto.setStu_course(rs.getString("stu_course"));
				
				return dto;
			}
		});
			return stView;
		}catch(Exception e) {
			return null;
		}
	}
	/*
	//세부정보 보기 (배열의 배열 = Arraylist)
		@Override
		public List<StudentDto> findStudentById(int studentId) {
			
			String sql = "select * from student where stu_id = ?";
			
			List<StudentDto> stView = template.query(sql, new RowMapper<StudentDto>() {  //query말고 queryForObject로 써도 무관 (에러뜸)

				@Override
				public StudentDto mapRow(ResultSet rs, int rowNum) throws SQLException {
					
					StudentDto dto = new StudentDto(); 
					dto.setStu_id(rs.getInt("stu_id"));
					dto.setStu_name(rs.getString("stu_name"));
					dto.setStu_email(rs.getString("stu_email"));
					dto.setStu_course(rs.getString("stu_course"));
					
					return dto;
					
				}
			});
			
			return stView;
		}
		*/

}
