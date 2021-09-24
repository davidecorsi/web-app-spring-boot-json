package it.partec.webappspringbootjson.service.impl;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

	private static final Logger logger = LogManager.getLogger(StudentServiceImpl.class);
	
	@Autowired
	private ObjectMapper objectMapper;

	public List<Student> getListStudent() throws Exception {
		logger.trace("Inizio getListStudent service");
		List<Student> studentList = null;
		logger.info("Apertura file liststudent.json");
		try(Reader file = new InputStreamReader(getClass().getClassLoader().getResourceAsStream("liststudent.json"))) {
			studentList = objectMapper.readValue(file, new TypeReference<List<Student>>(){});
			logger.debug("Fine getListStudent service");
		} catch(IOException e) {
			throw new CommonException(e);
		}
		return studentList;
	}

	public Student getStudent(long id) throws Exception {
		logger.trace(String.format("Inizio getListStudent service [ id: %d ]", id));
		List<Student> studentList = getListStudent();
		Student student = null;
		for(Student s: studentList) {
			if(id == s.getId()) {
				logger.info(String.format("Studente trovato [ id: %d ]", id));
				student = s;
				break;
			}
		}
		if(student == null) {
			throw new StudentNotFoundException("Lo studente non è stato trovato");
		}
		logger.debug(String.format("Fine getListStudent service [ id: %d ]", id));
		return student;
	}

	public void addStudent(Student student) throws Exception {
		logger.trace("Inizio addStudent service");
		List<Student> studentList = getListStudent();
		long id = 0;
		for(Student s: studentList) {
			if(s.getId() > id) {
				id = s.getId();
			}
		}
		student.setId(id + 1);
		studentList.add(student);
		logger.info("Apertura file liststudent.json");
		try(Writer file = new PrintWriter(getClass().getClassLoader().getResource("liststudent.json").getFile())) {
			file.write(objectMapper.writeValueAsString(studentList));
			logger.debug("Fine addStudent service");
		} catch(IOException e) {
			throw new CommonException(e);
		}
	}

	public void deleteStudent(long id) throws Exception {
		logger.trace(String.format("Inizio deleteStudent service [ id: %d ]", id));
		List<Student> studentList = getListStudent();
		boolean checkStudent = false;
		for(int i = 0; i < studentList.size(); i++) {
			if(studentList.get(i).getId() == id) {
				logger.info(String.format("Studente trovato [ id: %d ]", id));
				studentList.remove(i);
			}
		}
		if(!checkStudent) {
			throw new StudentNotFoundException("Lo studente non è stato trovato");
		}
		writeStudentList(studentList);
		logger.debug(String.format("Fine deleteStudent service [ id: %d ]", id));
	}
	
	private void writeStudentList(List<Student> studentList) throws IOException {
		logger.trace("Inizio writeStudentList service");
		try(Writer file = new PrintWriter(getClass().getClassLoader().getResource("liststudent.json").getFile())) {
			file.write(objectMapper.writeValueAsString(studentList));
			logger.debug("Fine writeStudentList service");
		}
	}
}
