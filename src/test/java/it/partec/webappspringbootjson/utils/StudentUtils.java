package it.partec.webappspringbootjson.utils;

import it.partec.webappspringbootjson.dto.Student;

public class StudentUtils {
	
	public static Student getOneStudent() {
		Student student = new Student();
		student.setId(1);
		student.setName("Antonio");
		student.setSurname("Frattasi");
		student.setAge(22);
		return student;
	}
}
