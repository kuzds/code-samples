package ru.kuzds.redis;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class RedisApplicationTests {

	private final static String STUDENT_ID = "Eng2015001";

	@Autowired
	StudentRepository studentRepository;
	@Test
	void contextLoads() {

		Student student = new Student(STUDENT_ID, "John Doe", Student.Gender.MALE, 1);
		studentRepository.save(student);

		Optional<Student> optionalStudent = studentRepository.findById(STUDENT_ID);

		Assertions.assertTrue(optionalStudent.isPresent());

		optionalStudent.get().setName("Richard Watson");
		studentRepository.save(student);

		studentRepository.deleteById(student.getId());

		optionalStudent = studentRepository.findById(STUDENT_ID);
		Assertions.assertFalse(optionalStudent.isPresent());

		Student engStudent = new Student(
				STUDENT_ID, "John Doe", Student.Gender.MALE, 1);
		Student medStudent = new Student(
				"Med2015001", "Gareth Houston", Student.Gender.MALE, 2);
		studentRepository.save(engStudent);
		studentRepository.save(medStudent);

		List<Student> students = new ArrayList<>();
		studentRepository.findAll().forEach(students::add);
		Assertions.assertEquals(2, students.size());
	}
}
