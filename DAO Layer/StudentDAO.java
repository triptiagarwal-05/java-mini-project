package com.example.dao;

import com.example.model.Student;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StudentDAO {
    private final SessionFactory sessionFactory;

    public StudentDAO(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    public void save(Student s){
        sessionFactory.getCurrentSession().save(s);
    }

    public void update(Student s){
        sessionFactory.getCurrentSession().update(s);
    }

    public void delete(Student s){
        sessionFactory.getCurrentSession().delete(s);
    }

    public Student getById(Integer id){
        return sessionFactory.getCurrentSession().get(Student.class, id);
    }

    @SuppressWarnings("unchecked")
    public List<Student> listAll(){
        return sessionFactory.getCurrentSession().createQuery("from Student").list();
    }
}
