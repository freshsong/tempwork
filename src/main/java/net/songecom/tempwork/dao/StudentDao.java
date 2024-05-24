package net.songecom.tempwork.dao;

import java.util.List;

import net.songecom.tempwork.model.StudentDto;

public interface StudentDao {
	
	int create(StudentDto dto); //Dto타입 객체를 create insert해서 씀
	
	int update(StudentDto dto); //Dto타입 객체를 delete해서 씀
	
	int delete(int studentId); //id번호만 받음 됨
	
	List<StudentDto> read();  //제네릭으로 전체목록 받기 (페이징도 이걸로씀)
	
	
	StudentDto findStudentById(int studentId); //arraylist -> object로 바꾸면서 타입변경 
	//public List<StudentDto> findStudentById(int studentId); //하나로 받는거 : 세부목록보기 
	
	
}
