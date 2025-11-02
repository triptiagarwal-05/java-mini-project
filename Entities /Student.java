package com.example.model;

import javax.persistence.*;

@Entity
@Table(name="students")
public class Student {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer studentId;

    @Column(nullable=false)
    private String name;

    private Double balance = 0.0;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id")
    private Course course;

    public Student() {}
    public Student(String name, Course course, Double balance) {
        this.name = name;
        this.course = course;
        this.balance = balance;
    }

    // getters/setters
    public Integer getStudentId() { return studentId; }
    public void setStudentId(Integer studentId) { this.studentId = studentId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Double getBalance() { return balance; }
    public void setBalance(Double balance) { this.balance = balance; }
    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }

    @Override
    public String toString(){
        String c = (course==null) ? "No Course" : course.getCourseName();
        return studentId + " - " + name + " | Course: " + c + " | Balance: " + balance;
    }
}
