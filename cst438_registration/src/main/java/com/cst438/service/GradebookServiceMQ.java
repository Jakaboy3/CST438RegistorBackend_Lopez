package com.cst438.service;


import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cst438.domain.FinalGradeDTO;
import com.cst438.domain.Enrollment;
import com.cst438.domain.EnrollmentDTO;
import com.cst438.domain.EnrollmentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@ConditionalOnProperty(prefix = "gradebook", name = "service", havingValue = "mq")
public class GradebookServiceMQ implements GradebookService {
	
	@Autowired
	RabbitTemplate rabbitTemplate;
	
	@Autowired
	EnrollmentRepository enrollmentRepository;
	
	Queue gradebookQueue = new Queue("gradebook-queue", true);

	// send message to grade book service about new student enrollment in course
	@Override
	public void enrollStudent(String student_email, String student_name, int course_id) {
		System.out.println("Start Message "+ student_email +" " + course_id); 
		// create EnrollmentDTO, convert to JSON string and send to gradebookQueue
		// TODO
		EnrollmentDTO s = new EnrollmentDTO(0,student_email, student_name, course_id);
		String x = asJsonString(s);
		rabbitTemplate.convertAndSend(gradebookQueue.getName(), x);
	}
	
	@RabbitListener(queues = "registration-queue")
	@Transactional
	public void receive(String message) {
		System.out.println("Receive grades :" + message);
		/*
		 * for each student grade in courseDTOG,  find the student enrollment 
		 * entity and update the grade.
		 */
		//url=http://localhost:8081/enrollment
		// deserialize the string message to FinalGradeDTO[] 
		//FinalGradeDTO[] 
		
				
		// TODO
		FinalGradeDTO[] s = fromJsonString(message, FinalGradeDTO[].class);
		for(FinalGradeDTO gradeDTO : s) {
			String student_email = gradeDTO.studentEmail();
			int course_id = gradeDTO.courseId();
			
			String grade = gradeDTO.grade();
			Enrollment enroll = enrollmentRepository.findByEmailAndCourseId(student_email,course_id);
			if(enroll == null) {
				System.out.println("Nothing found");
			}else {
				enroll.setCourseGrade(grade);
				enrollmentRepository.save(enroll);
			}
		}

	}
	
	private static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static <T> T  fromJsonString(String str, Class<T> valueType ) {
		try {
			return new ObjectMapper().readValue(str, valueType);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
