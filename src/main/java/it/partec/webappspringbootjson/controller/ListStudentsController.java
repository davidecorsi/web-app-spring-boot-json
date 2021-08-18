package it.partec.webappspringbootjson.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import it.partec.webappspringbootjson.model.Student;
import it.partec.webappspringbootjson.service.StudentService;

@RestController
public class ListStudentsController {
	
	@Autowired
	private StudentService studentService;
	
	@GetMapping("/liststudent")
	public ResponseEntity<List<Student>> getListStudent() {
		List<Student> studentList = null;
		try {
			studentList = studentService.getListStudent();
		} catch(Exception e) {
			return new ResponseEntity<List<Student>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<List<Student>>(studentList, HttpStatus.OK);
	}
}
