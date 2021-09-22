package it.partec.webappspringbootjson.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.MeterRegistry;
import it.partec.webappspringbootjson.dto.Student;
import it.partec.webappspringbootjson.service.StudentService;

@RestController
@RequestMapping("/student")
public class StudentController {
	
	@Autowired
	private StudentService studentService;
	
	private DistributionSummary distributionSummaryGetList;

	public StudentController(MeterRegistry meterRegistry) {
		this.distributionSummaryGetList = meterRegistry.summary("distribution_get_list");
	}
	
	@GetMapping
	@Timed("get_list_student_controller")
	public ResponseEntity<List<Student>> getListStudent() throws IOException {
		List<Student> studentList = null;
		studentList = studentService.getListStudent();
		return new ResponseEntity<List<Student>>(studentList, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Student> getStudent(@PathVariable("id") long id) throws Exception {
		Student student = null;
		student = studentService.getStudent(id);
		if(student != null) {
			return new ResponseEntity<Student>(student, HttpStatus.OK);
		}
		return new ResponseEntity<Student>(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping
	public ResponseEntity<Object> addStudent(@RequestBody Student student, HttpServletRequest req) throws IOException {
		distributionSummaryGetList.record(req.getContentLength());
		studentService.addStudent(student);	
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteStudent(@PathVariable("id") long id) throws Exception {
		studentService.deleteStudent(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
