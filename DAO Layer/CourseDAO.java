package com.example.dao;

import com.example.model.Course;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CourseDAO {
    private final SessionFactory sessionFactory;
    public CourseDAO(SessionFactory sessionFactory){ this.sessionFactory = sessionFactory; }

    public void save(Course c){ sessionFactory.getCurrentSession().save(c); }
    public Course getById(Integer id){ return sessionFactory.getCurrentSession().get(Course.class, id); }
    @SuppressWarnings("unchecked")
    public List<Course> listAll(){ return sessionFactory.getCurrentSession().createQuery("from Course").list(); }
}
