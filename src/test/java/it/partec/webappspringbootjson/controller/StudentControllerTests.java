package it.partec.webappspringbootjson.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import it.partec.webappspringbootjson.dto.Student;
import it.partec.webappspringbootjson.service.StudentService;
import it.partec.webappspringbootjson.utils.StudentUtils;

@WebMvcTest(StudentController.class)
public class StudentControllerTests {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private StudentService studentService;

	@Test
	void getListStudentTest() throws Exception {
		Student student = StudentUtils.getOneStudent();
		List<Student> studentList = Arrays.asList(student);
		when(studentService.getListStudent()).thenReturn(studentList);
		mvc.perform(get("/student")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(1)))
		.andExpect(jsonPath("$[0].id", is((int) student.getId())))
		.andExpect(jsonPath("$[0].name", is(student.getName())))
		.andExpect(jsonPath("$[0].surname", is(student.getSurname())))
		.andExpect(jsonPath("$[0].age", is((int) student.getAge())));
	}

	@Test
	void getListStudentExceptionTest() throws Exception {
		when(studentService.getListStudent()).thenThrow(IOException.class);
		mvc.perform(get("/student")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isInternalServerError())
		.andExpect(jsonPath("$.status", is("503")))
		.andExpect(jsonPath("$.error", is("ERRORE INTERNO")));
	}

	@Test
	void getStudentTest() throws Exception {
		Student student = StudentUtils.getOneStudent();
		when(studentService.getStudent(1)).thenReturn(student);
		mvc.perform(get("/student/1")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id", is((int) student.getId())))
		.andExpect(jsonPath("$.name", is(student.getName())))
		.andExpect(jsonPath("$.surname", is(student.getSurname())))
		.andExpect(jsonPath("$.age", is((int) student.getAge())));	
	}

	@Test
	void getStudentNotFoundTest() throws Exception {
		when(studentService.getListStudent()).thenReturn(null);
		mvc.perform(get("/student/1")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound());
	}

	@Test
	void getStudentExceptionTest() throws Exception {
		when(studentService.getStudent(1)).thenThrow(IOException.class);
		mvc.perform(get("/student/1")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isInternalServerError())
		.andExpect(jsonPath("$.status", is("503")))
		.andExpect(jsonPath("$.error", is("ERRORE INTERNO")));
	}
}
