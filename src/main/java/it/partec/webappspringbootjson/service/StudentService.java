package it.partec.webappspringbootjson.service;

import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.partec.webappspringbootjson.model.Student;

@Service
public class StudentService {

	@Autowired
	private ObjectMapper objectMapper;

	public List<Student> getListStudent() throws Exception {
		List<Student> studentList = null;
		try(Reader file = new InputStreamReader(getClass().getClassLoader().getResourceAsStream("liststudent.json"))) {
			studentList = objectMapper.readValue(file, new TypeReference<List<Student>>(){});
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
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
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public void deleteStudent(long id) throws Exception{
	}
}
