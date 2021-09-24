package it.partec.webappspringbootjson.service;

import java.util.List;

import it.partec.webappspringbootjson.dto.Student;

public interface StudentService {
	
	public List<Student> getListStudent() throws Exception;
	public void addStudent(Student student) throws Exception;
	public void deleteStudent(long id) throws Exception;
	public Student getStudent(long id) throws Exception;
}