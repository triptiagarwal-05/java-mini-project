package com.example.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="payments")
public class Payment {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer paymentId;

    @ManyToOne
    @JoinColumn(name="student_id")
    private Student student;

    private Double amount;

    @Temporal(TemporalType.TIMESTAMP)
    private Date paymentDate = new Date();

    public Payment() {}
    public Payment(Student student, Double amount) {
        this.student = student;
        this.amount = amount;
    }

    // getters/setters
    public Integer getPaymentId() { return paymentId; }
    public void setPaymentId(Integer paymentId) { this.paymentId = paymentId; }
    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }
    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }
    public Date getPaymentDate() { return paymentDate; }
    public void setPaymentDate(Date paymentDate) { this.paymentDate = paymentDate; }

    @Override
    public String toString(){
        return paymentId + " | Student: " + (student!=null?student.getName():"N/A") + " | Amount: " + amount + " | Date: " + paymentDate;
    }
}
