package com.example.service;

import com.example.dao.PaymentDAO;
import com.example.dao.StudentDAO;
import com.example.model.Payment;
import com.example.model.Student;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FeeService {
    private final StudentDAO studentDAO;
    private final PaymentDAO paymentDAO;

    public FeeService(StudentDAO studentDAO, PaymentDAO paymentDAO){
        this.studentDAO = studentDAO;
        this.paymentDAO = paymentDAO;
    }

    /**
     * Make a payment -- deduct from student's balance and record a payment row.
     * The whole method is transactional and will roll back on runtime exceptions.
     */
    @Transactional
    public void payFee(Integer studentId, Double amount){
        if(amount == null || amount <= 0) throw new IllegalArgumentException("Amount must be positive.");

        Student s = studentDAO.getById(studentId);
        if(s == null) throw new IllegalArgumentException("Student not found: " + studentId);

        Double newBalance = s.getBalance() - amount;
        // Example business rule: balance can go negative? We'll allow but warn. Alternatively block it.
        s.setBalance(newBalance);
        studentDAO.update(s);

        Payment p = new Payment(s, amount);
        paymentDAO.save(p);

        // If anything throws after this, Spring will rollback both update and save
    }

    @Transactional
    public void refundFee(Integer studentId, Double amount){
        if(amount == null || amount <= 0) throw new IllegalArgumentException("Amount must be positive.");

        Student s = studentDAO.getById(studentId);
        if(s == null) throw new IllegalArgumentException("Student not found: " + studentId);

        s.setBalance(s.getBalance() + amount);
        studentDAO.update(s);

        // Optionally record negative payment or a separate refund table; here we'll record as negative Payment
        Payment p = new Payment(s, -amount);
        paymentDAO.save(p);
    }
}
