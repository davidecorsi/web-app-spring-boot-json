package it.partec.webappspringbootjson.service.impl;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.ImmutableTag;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.Timer.Sample;
import it.partec.webappspringbootjson.dto.Student;
import it.partec.webappspringbootjson.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService {
	
	private Counter counterGetList;
	private AtomicInteger gaugeGetList;
	private Timer timerGetList;
	
	public StudentServiceImpl(MeterRegistry meterRegistry) {
		this.counterGetList = meterRegistry.counter("counter_get_list", "type", "service");
		this.gaugeGetList = meterRegistry.gauge("gauge_get_list", 
				Arrays.asList(new ImmutableTag("type", "service")), new AtomicInteger());
		this.timerGetList = meterRegistry.timer("timer_get_list", "type", "service");
	}
	
	@Autowired
	private ObjectMapper objectMapper;

	public List<Student> getListStudent() throws IOException {
		Sample sample = Timer.start();
		counterGetList.increment();
		List<Student> studentList = null;
		try(Reader file = new InputStreamReader(getClass().getClassLoader().getResourceAsStream("liststudent.json"))) {
			studentList = objectMapper.readValue(file, new TypeReference<List<Student>>(){});
			gaugeGetList.set(studentList.size());
		} catch(IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			sample.stop(timerGetList);
		}
		return studentList;
	}

	public Student getStudent(long id) throws IOException {
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

	public void addStudent(Student student) throws IOException {
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
			e.printStackTrace();
			throw e;
		}
	}

	public void deleteStudent(long id) throws IOException {
		List<Student> studentList = getListStudent();
		for(int i = 0; i < studentList.size(); i++) {
			if(studentList.get(i).getId() == id) {
				studentList.remove(i);
			}
		}
		writeStudentList(studentList);
	}
	
	private void writeStudentList(List<Student> studentList) throws IOException {
		try(Writer file = new PrintWriter(getClass().getClassLoader().getResource("liststudent.json").getFile())) {
			file.write(objectMapper.writeValueAsString(studentList));
		} catch(IOException e) {
			e.printStackTrace();
			throw e;
		}
	}
}
