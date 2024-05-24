package net.songecom.tempwork.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import net.songecom.tempwork.model.StudentDto;

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

	@Override
	public int update(StudentDto dto) {
		return 0;
	}

	@Override
	public int delte(int studentId) {
		return 0;
	}

	@Override
	public List<StudentDto> read() {
		return null;
	}

	@Override
	public List<StudentDto> findStudentById(int studentId) {
		return null;
	}

}
