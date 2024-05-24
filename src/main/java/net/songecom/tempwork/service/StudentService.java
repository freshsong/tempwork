package net.songecom.tempwork.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.songecom.tempwork.dao.StudentDao;
import net.songecom.tempwork.model.StudentDto;

@Service 
public class StudentService {
	
	@Autowired //자동객체생성되게
	private StudentDao studentDao; 
	
	//메소드 구현시켜두면 mapper에서 실행됨 (간단해짐)
	//create (StudentDao에서 가져오기)
	@Transactional //동시작업시 동시에 이뤄지지 않고 정상적으로 하나씩 차례차례 구현됨
	public int addStudent(StudentDto dto) {
		return studentDao.create(dto);
	}
	
	//update
	@Transactional //동시작업시 정상적으로 하나씩 차례차례 구현됨
	public int updateStudent(StudentDto dto) {
		return studentDao.update(dto);
	}
		
	//delete (StudentDto dto 아닌 studentId로 와야, Dao에서 지정한값)
	@Transactional //동시작업시 정상적으로 하나씩 차례차례 구현됨
	public int deleteStudent(int studentId) {
		return studentDao.delete(studentId);
	}
		
	//read
	public List<StudentDto> getAllStudents(){
		return studentDao.read();
	}
		
	//findeById
	public StudentDto getStudentById(int studentId) {
		return studentDao.findStudentById(studentId);
	}
	
}
