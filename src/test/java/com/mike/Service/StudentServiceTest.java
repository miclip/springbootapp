package com.mike.Service;

import com.mike.Dao.FakeStudentDaoImpl;
import com.mike.Dao.StudentDao;
import com.mike.Entity.Student;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@RunWith(SpringRunner.class)
@ContextConfiguration
public class StudentServiceTest {

    @Configuration
    static class StudentServiceTestContextConfiguration {
        @Bean
        public StudentService studentService() {
            return new StudentService();
        }
        @Bean
        public StudentDao studentDao() {
            return Mockito.mock(StudentDao.class);
        }
    }

    private Student mockStudent = new Student(255, "TestName", "TestCourse");
    private List<Student> mockStudents = new ArrayList<>(Arrays.asList(mockStudent, mockStudent));

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentDao studentDao;

    @Test
    public void retrieveAllStudents(){
        Mockito.when(studentDao.getAllStudents()).thenReturn(mockStudents);
        Collection<Student> students = studentService.getAllStudents();
        Assert.assertEquals(students.size(), 2);
    }

    @Test
    public void retrieveStudentById(){
        Mockito.when(studentDao.getStudentById(Mockito.anyInt())).thenReturn(mockStudent);
        Student student = studentService.getStudentById(1);
        Assert.assertEquals(student.getName(), mockStudent.getName());
        Assert.assertEquals(student.getCourse(), mockStudent.getCourse());
    }

    @Test
    public void updateStudent(){
        Mockito.doNothing().when(studentDao).updateStudent(Mockito.any(Student.class));
        studentService.updateStudent(mockStudent);
        Mockito.verify(studentDao).updateStudent(Mockito.any(Student.class));
    }

    @Test
    public void addStudent(){
        Mockito.doNothing().when(studentDao).insertStudent(Mockito.any(Student.class));
        studentService.insertStudent(mockStudent);
        Mockito.verify(studentDao).insertStudent(Mockito.any(Student.class));
    }

    @Test
    public void removeStudentById(){
        Mockito.doNothing().when(studentDao).removeStudentById(Mockito.anyInt());
        studentService.removeStudentById(2);
        Mockito.verify(studentDao).removeStudentById(Mockito.anyInt());
    }

}
