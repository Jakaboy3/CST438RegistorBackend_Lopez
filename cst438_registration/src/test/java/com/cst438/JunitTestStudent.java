package com.cst438;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.cst438.domain.StudentDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class JunitTestStudent{

    @Autowired
    private MockMvc mvc;

    /*
     * Test retrieval of all students.
     */
    @Test
    public void getAllStudents() throws Exception {
        MockHttpServletResponse response;

        response = mvc.perform(
                MockMvcRequestBuilders
                .get("/student")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify that return status = OK (value 200) 
        assertEquals(200, response.getStatus());

        StudentDTO[] students = fromJsonString(response.getContentAsString(), StudentDTO[].class);
        assertNotEquals(0, students.length);
    }

    /*
     * Test retrieval of a specific student.
     */
    @Test
    public void getStudentById() throws Exception {
        MockHttpServletResponse response;

        int testStudentId = 1;  // Adjust this as necessary based on your test data

        response = mvc.perform(
                MockMvcRequestBuilders
                .get("/student/" + testStudentId)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify that return status = OK (value 200) 
        assertEquals(200, response.getStatus());

        StudentDTO student = fromJsonString(response.getContentAsString(), StudentDTO.class);
        assertEquals(testStudentId, student.student_id());
    }

    // Add more tests as necessary for other CRUD operations...

    private static <T> T fromJsonString(String str, Class<T> valueType) {
        try {
            return new ObjectMapper().readValue(str, valueType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    @Test
    public void deleteStudent() throws Exception{
    	MockHttpServletResponse response;
    	
    	response = mvc.perform(
                MockMvcRequestBuilders
    			.get("MockMvcRequestBuilders")
    			.accept(MediaType.APPLICATION_JSON))
    			.andReturn().getResponse();
    	StudentDTO[] dto_list = fromJsonString(response.getContentAsString(), StudentDTO[].class);
    	boolean found = false;
    	for(StudentDTO dto : dto_list) {
    		if(dto.student_id()==1) found=true;
    	}
    	assertTrue(found);
    	
    	response = mvc.perform(
    			MockMvcRequestBuilders
    			.delete("/student/1"))
    			.andReturn().getResponse();
    	
    	dto_list = fromJsonString(response.getContentAsString(), StudentDTO[].class);
    	found = false;
    	for(StudentDTO dto : dto_list) {
    		if(dto.student_id()==1) found=true;
    	}
    	assertFalse(found);
    }
	private static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

    
    
    
    

}
