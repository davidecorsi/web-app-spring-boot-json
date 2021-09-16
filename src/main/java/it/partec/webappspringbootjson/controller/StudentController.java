package it.partec.webappspringbootjson.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.partec.webappspringbootjson.dto.Student;
import it.partec.webappspringbootjson.service.StudentService;

@RestController
@RequestMapping("/student")
public class StudentController {
	
	@Autowired
	private StudentService studentService;
	
	@GetMapping
	public ResponseEntity<List<Student>> getListStudent() {
		List<Student> studentList = null;
		try {
			studentList = studentService.getListStudent();
		} catch(Exception e) {
			return new ResponseEntity<List<Student>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<List<Student>>(studentList, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Object> addStudent(@RequestBody Student student) {
		try {
			studentService.addStudent(student);
		} catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
}
