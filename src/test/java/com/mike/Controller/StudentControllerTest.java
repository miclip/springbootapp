package com.mike.Controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mike.Service.StudentService;
import com.mike.Entity.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@WebMvcTest(value = StudentController.class, secure = false)
public class StudentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;
    private Student mockStudent = new Student(255, "TestName", "TestCourse");
    private List<Student> mockStudents = new ArrayList<>(Arrays.asList(mockStudent, mockStudent));

    String studentJson = "{\"id\":255,\"name\":\"TestName\",\"course\":\"TestCourse\"}";

    @Test
    public void retrieveStudentById() throws Exception{
        Mockito.when(
                studentService.getStudentById(Mockito.anyInt())).thenReturn(mockStudent);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/students/255").accept(
                MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        System.out.println(result.getResponse());
        String expected = "{id:255,name:TestName,course:TestCourse}";
        JSONAssert.assertEquals(expected, result.getResponse()
                .getContentAsString(), false);
    };

    @Test
    public void retrieveAllStudents() throws Exception{
        Mockito.when(
                studentService.getAllStudents()).thenReturn(mockStudents);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/students").accept(
                MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        System.out.println(result.getResponse());
        String expected = "[{id:255,name:TestName,course:TestCourse},{id:255,name:TestName,course:TestCourse}]";
        JSONAssert.assertEquals(expected, result.getResponse()
                .getContentAsString(), false);
    };

    @Test
    public void updateStudent() throws Exception{
        Mockito.doNothing().when(studentService).updateStudent(Mockito.any(Student.class));
        mockMvc.perform(put("/students").contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(studentJson))
                .andExpect(status().isOk());
        Mockito.verify(studentService).updateStudent(Mockito.any(Student.class));
    };

    @Test
    public void addStudent() throws Exception{
        Mockito.doNothing().when(studentService).insertStudent(Mockito.any(Student.class));
        mockMvc.perform(post("/students").contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(studentJson))
                .andExpect(status().isOk());
        Mockito.verify(studentService).insertStudent(Mockito.any(Student.class));
    };

    @Test
    public void deleteStudentById() throws Exception{
        Mockito.doNothing().when(studentService).removeStudentById(Mockito.anyInt());
        mockMvc.perform(delete("/students/255").contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(studentJson))
                .andExpect(status().isOk());
        Mockito.verify(studentService).removeStudentById(Mockito.anyInt());
    };

}

