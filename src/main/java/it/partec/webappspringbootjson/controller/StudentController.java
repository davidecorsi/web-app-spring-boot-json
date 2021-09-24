package it.partec.webappspringbootjson.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.partec.webappspringbootjson.dto.Student;
import it.partec.webappspringbootjson.service.StudentService;

@RestController
@RequestMapping("/student")
public class StudentController {
	
	private static final Logger logger = LogManager.getLogger(StudentController.class);
	
	@Autowired
	private StudentService studentService;
	
	@GetMapping
	public ResponseEntity<List<Student>> getListStudent() throws Exception {
		logger.trace("Inizio getListStudent controller");
		List<Student> studentList = null;
		studentList = studentService.getListStudent();
		logger.debug("Fine getListStudent controller");
		return new ResponseEntity<List<Student>>(studentList, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Student> getStudent(@PathVariable("id") long id) throws Exception {
		logger.trace(String.format("Inizio getStudent controller [ id: %d ]", id));
		Student student = null;
		student = studentService.getStudent(id);
		logger.debug(String.format("Fine getStudent controller [ id: %d ]", id));
		if(student != null) {
			return new ResponseEntity<Student>(student, HttpStatus.OK);
		}
		logger.info(String.format("Studente non trovato [ id: %d ]", id));
		return new ResponseEntity<Student>(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping
	public ResponseEntity<Object> addStudent(@RequestBody Student student) throws Exception {
		logger.trace("Inizio addStudent controller");
		studentService.addStudent(student);
		logger.debug("Fine addStudent controller");
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteStudent(@PathVariable("id") long id) throws Exception {
		logger.trace(String.format("Inizio deleteStudent controller [ id: %d ]", id));
		studentService.deleteStudent(id);
		logger.debug(String.format("Fine deleteStudent controller [ id: %d ]", id));
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
