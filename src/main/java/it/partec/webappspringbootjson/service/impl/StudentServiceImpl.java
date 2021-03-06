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
}
