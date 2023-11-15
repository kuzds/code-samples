package ru.kuzds.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class RedisApplication implements CommandLineRunner {

	private final StudentRepository studentRepository;

	public static void main(String[] args) {
		SpringApplication.run(RedisApplication.class, args);
	}

	@Override
	public void run(String... args) {
		Student student = new Student("Eng2015001", "John Doe", Student.Gender.MALE, 1);
		studentRepository.save(student);

		Student retrievedStudent = studentRepository.findById("Eng2015001").get();

		retrievedStudent.setName("Richard Watson");
		studentRepository.save(student);

		studentRepository.deleteById(student.getId());

		Student engStudent = new Student(
				"Eng2015001", "John Doe", Student.Gender.MALE, 1);
		Student medStudent = new Student(
				"Med2015001", "Gareth Houston", Student.Gender.MALE, 2);
		studentRepository.save(engStudent);
		studentRepository.save(medStudent);

		List<Student> students = new ArrayList<>();
		studentRepository.findAll().forEach(students::add);

		log.info(students.toString());
	}
}
