package com.example.service;

import com.example.dao.StudentDAO;
import com.example.model.Student;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentService {
    private final StudentDAO studentDAO;
    public StudentService(StudentDAO studentDAO){ this.studentDAO = studentDAO; }

    @Transactional(readOnly = true)
    public Student getById(Integer id){ return studentDAO.getById(id); }

    @Transactional(readOnly = true)
    public List<Student> listAll(){ return studentDAO.listAll(); }

    @Transactional
    public void addStudent(Student s){ studentDAO.save(s); }

    @Transactional
    public void updateStudent(Student s){ studentDAO.update(s); }

    @Transactional
    public void deleteStudent(Student s){ studentDAO.delete(s); }
}
