package it.partec.webappspringbootjson.service.impl;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.partec.webappspringbootjson.dto.Student;
import it.partec.webappspringbootjson.exception.CommonException;
import it.partec.webappspringbootjson.exception.StudentNotFoundException;
import it.partec.webappspringbootjson.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	private ObjectMapper objectMapper;

	public List<Student> getListStudent() throws Exception {
		List<Student> studentList = null;
		try(Reader file = new InputStreamReader(getClass().getClassLoader().getResourceAsStream("liststudent.json"))) {
			studentList = objectMapper.readValue(file, new TypeReference<List<Student>>(){});
		} catch(IOException e) {
			throw new CommonException(e);
		}
		return studentList;
	}

	public Student getStudent(long id) throws Exception {
		List<Student> studentList = getListStudent();
		Student student = null;
		for(Student s: studentList) {
			if(id == s.getId()) {
				student = s;
				break;
			}
		}
		if(student == null) {
			throw new StudentNotFoundException("Lo studente non è stato trovato");
		}
		return student;
	}

	public void addStudent(Student student) throws Exception {
		List<Student> studentList = getListStudent();
		long id = 0;
		for(Student s: studentList) {
			if(s.getId() > id) {
				id = s.getId();
			}
		}
		student.setId(id + 1);
		studentList.add(student);
		try(Writer file = new PrintWriter(getClass().getClassLoader().getResource("liststudent.json").getFile())) {
			file.write(objectMapper.writeValueAsString(studentList));
		} catch(IOException e) {
			throw new CommonException(e);
		}
	}

	public void deleteStudent(long id) throws Exception {
		List<Student> studentList = getListStudent();
		boolean checkStudent = false;
		for(int i = 0; i < studentList.size(); i++) {
			if(studentList.get(i).getId() == id) {
				studentList.remove(i);
				checkStudent = true;
			}
		}
		if(!checkStudent) {
			throw new StudentNotFoundException("Lo studente non è stato trovato");
		}
		writeStudentList(studentList);
	}
	
	private void writeStudentList(List<Student> studentList) throws Exception {
		try(Writer file = new PrintWriter(getClass().getClassLoader().getResource("liststudent.json").getFile())) {
			file.write(objectMapper.writeValueAsString(studentList));
		} catch(IOException e) {
			throw new CommonException(e);
		}
	}
}