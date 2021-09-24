package it.partec.webappspringbootjson.service;

import java.io.IOException;
import java.util.List;

import it.partec.webappspringbootjson.dto.Student;

public interface StudentService {
	
	public List<Student> getListStudent() throws IOException;
	public void addStudent(Student student) throws IOException;
	public void deleteStudent(long id) throws Exception;
	public Student getStudent(long id) throws Exception;
}