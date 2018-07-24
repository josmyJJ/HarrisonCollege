package com.example.service;

import com.example.model.Course;
import com.example.model.Major;
import com.example.model.Student;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService implements IStudentService {
    @Override
    public List<Student> getAllStudents() {

        Student student = new Student();
        student.setName("Josmey");
        student.setNumber(17895);
        student.setYearOfEntry("2017");
        student.setId(1);

        Student student2 = new Student();
        student2.setName("Hirani");
        student2.setNumber(44895);
        student2.setYearOfEntry("2016");
        student2.setId(2);

        Student student3 = new Student();
        student3.setName("David");
        student3.setNumber(54895);
        student3.setYearOfEntry("2012");
        student3.setId(3);

        List<Student> students = new ArrayList<Student>();
        students.add(student);
        students.add(student2);
        students.add(student3);

        return  students;

    }

    @Override
    public List<Class> GetAllClassesOfStudentByID(int studentId) {
        return null;
    }

    @Override
    public List<Class> GetAllSchedules(int studentId) {
        return null;
    }

    @Override
    public List<Course> GetAllCoursesByDepartmentID(int departmentId) {
        return null;
    }

    @Override
    public List<Class> GetClassesOfUserDepartmentById(int studentId, int department) {
        return null;
    }

    @Override
    public List<Major> GetAllMajorsByDepartmentId(int department) {
        return null;
    }
}
