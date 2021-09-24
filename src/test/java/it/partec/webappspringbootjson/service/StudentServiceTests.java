package it.partec.webappspringbootjson.service;

import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.any;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.partec.webappspringbootjson.dto.Student;
import it.partec.webappspringbootjson.exception.CommonException;
import it.partec.webappspringbootjson.exception.StudentNotFoundException;
import it.partec.webappspringbootjson.service.impl.StudentServiceImpl;
import it.partec.webappspringbootjson.utils.StudentUtils;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTests {
	
	@Mock
	private ObjectMapper objectMapper;
	
	@InjectMocks
	private StudentServiceImpl studentService;
	
	@Test
	void getListStudentTest() throws Exception {
		Student student = StudentUtils.getOneStudent();
		List<Student> studentList = Arrays.asList(student);
		when(objectMapper.readValue(any(Reader.class), any(TypeReference.class)))
		.thenReturn(studentList);
		assertEquals(studentService.getListStudent(), studentList);
	}
	
	@Test
	void getListStudentExceptionTest() throws IOException {
		when(objectMapper.readValue(any(Reader.class), any(TypeReference.class)))
		.thenThrow(IOException.class);
		assertThrows(CommonException.class, () -> studentService.getListStudent());
	}
	
	@Test
	void getStudentTest() throws Exception {
		Student student = StudentUtils.getOneStudent();
		List<Student> studentList = Arrays.asList(student);
		when(objectMapper.readValue(any(Reader.class), any(TypeReference.class)))
		.thenReturn(studentList);
		assertEquals(studentService.getStudent(1), student);
	}
	
	@Test
	void getStudentNullTest() throws IOException {
		Student student = StudentUtils.getOneStudent();
		List<Student> studentList = Arrays.asList(student);
		when(objectMapper.readValue(any(Reader.class), any(TypeReference.class)))
		.thenReturn(studentList);
		assertThrows(StudentNotFoundException.class, () -> studentService.getStudent(2));
	}
	
	@Test
	void getStudentExceptionTest() throws IOException {
		when(objectMapper.readValue(any(Reader.class), any(TypeReference.class)))
		.thenThrow(IOException.class);
		assertThrows(CommonException.class, () -> studentService.getStudent(1));
	}
}
