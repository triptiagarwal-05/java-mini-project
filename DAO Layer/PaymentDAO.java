package com.example.dao;

import com.example.model.Payment;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PaymentDAO {
    private final SessionFactory sessionFactory;
    public PaymentDAO(SessionFactory sessionFactory){ this.sessionFactory = sessionFactory; }

    public void save(Payment p){ sessionFactory.getCurrentSession().save(p); }
    @SuppressWarnings("unchecked")
    public List<Payment> listByStudentId(Integer studentId){
        return sessionFactory.getCurrentSession()
                .createQuery("from Payment p where p.student.studentId = :sid")
                .setParameter("sid", studentId).list();
    }
}
