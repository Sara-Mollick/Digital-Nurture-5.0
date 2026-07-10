package com.cognizant.springlearn;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Date;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class SpringLearnApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void contextLoads() {
    }

    @Test
    void testGetCountriesUnauthorized() throws Exception {
        // Without authentication, accessing secured resource should return 401 Unauthorized
        mockMvc.perform(get("/countries"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testAuthenticateSuccess() throws Exception {
        // Authenticating with correct Basic Credentials should succeed
        mockMvc.perform(get("/authenticate")
                .header("Authorization", "Basic dXNlcjpwd2Q=")) // user:pwd
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateEmployeeNotFound() throws Exception {
        // Mocking an exceptional scenario where employee is not found (404)
        // Creating an employee with ID 999 (which does not exist)
        Department dept = new Department();
        dept.setId(1);
        dept.setName("IT");

        Skill skill = new Skill();
        skill.setId(1);
        skill.setName("Java");

        Employee nonExistentEmployee = new Employee();
        nonExistentEmployee.setId(999);
        nonExistentEmployee.setName("NonExistent");
        nonExistentEmployee.setSalary(50000.0);
        nonExistentEmployee.setPermanent(true);
        nonExistentEmployee.setDateOfBirth(new Date());
        nonExistentEmployee.setDepartment(dept);
        nonExistentEmployee.setSkills(Collections.singletonList(skill));

        // The request needs to bypass security or be authenticated. Since we configured basic auth, 
        // we can authenticate it using Basic Auth headers.
        mockMvc.perform(put("/employees")
                .header("Authorization", "Basic dXNlcjpwd2Q=") // user:pwd
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nonExistentEmployee)))
                .andExpect(status().isNotFound());
    }
}
