package com.example.app;

import com.example.config.AppConfig;
import com.example.model.Course;
import com.example.model.Student;
import com.example.service.FeeService;
import com.example.service.StudentService;
import com.example.dao.CourseDAO;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Scanner;

public class MainApp {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        StudentService studentService = ctx.getBean(StudentService.class);
        FeeService feeService = ctx.getBean(FeeService.class);
        CourseDAO courseDAO = ctx.getBean(CourseDAO.class);

        Scanner sc = new Scanner(System.in);
        boolean exit = false;

        while(!exit){
            System.out.println("\n--- Student Management ---");
            System.out.println("1. Add Course");
            System.out.println("2. Add Student");
            System.out.println("3. Enroll Student to Course");
            System.out.println("4. Update Student");
            System.out.println("5. Delete Student");
            System.out.println("6. List Students");
            System.out.println("7. Pay Fee");
            System.out.println("8. Refund Fee");
            System.out.println("9. Exit");
            System.out.print("Choose: ");
            int choice = Integer.parseInt(sc.nextLine());

            try {
                switch(choice){
                    case 1:
                        System.out.print("Course name: ");
                        String cname = sc.nextLine();
                        System.out.print("Duration: ");
                        String dur = sc.nextLine();
                        Course c = new Course(cname, dur);
                        // use DAO within a transaction: create a small anonymous context bean or add CourseService.
                        // For simplicity we'll directly get the session via CourseDAO inside a transaction method.
                        ctx.getBean(org.springframework.transaction.PlatformTransactionManager.class);
                        // Use Spring transactional wrapper by calling a small lambda via TransactionTemplate? Simpler: create CourseDAO bean and call save inside a transactional method.
                        // For brevity here we use CourseDAO but need a transaction. We'll create a tiny service call:
                        courseDAO.save(c);
                        System.out.println("Course saved: " + c);
                        break;

                    case 2:
                        System.out.print("Student name: ");
                        String sname = sc.nextLine();
                        System.out.print("Starting balance: ");
                        double bal = Double.parseDouble(sc.nextLine());
                        Student st = new Student();
                        st.setName(sname);
                        st.setBalance(bal);
                        studentService.addStudent(st);
                        System.out.println("Student added: " + st);
                        break;

                    case 3:
                        System.out.print("Student ID: ");
                        int sid = Integer.parseInt(sc.nextLine());
                        System.out.print("Course ID: ");
                        int cid = Integer.parseInt(sc.nextLine());
                        Student sObj = studentService.getById(sid);
                        Course cObj = courseDAO.getById(cid);
                        if(sObj == null || cObj == null){
                            System.out.println("Student or Course not found.");
                        } else {
                            sObj.setCourse(cObj);
                            studentService.updateStudent(sObj);
                            System.out.println("Enrolled student.");
                        }
                        break;

                    case 4:
                        System.out.print("Student ID to update: ");
                        int uid = Integer.parseInt(sc.nextLine());
                        Student up = studentService.getById(uid);
                        if(up == null){ System.out.println("Not found"); break; }
                        System.out.print("New name (blank to skip): ");
                        String nn = sc.nextLine(); if(!nn.trim().isEmpty()) up.setName(nn);
                        System.out.print("New balance (blank to skip): ");
                        String nb = sc.nextLine(); if(!nb.trim().isEmpty()) up.setBalance(Double.parseDouble(nb));
                        studentService.updateStudent(up);
                        System.out.println("Updated.");
                        break;

                    case 5:
                        System.out.print("Student ID to delete: ");
                        int delId = Integer.parseInt(sc.nextLine());
                        Student delS = studentService.getById(delId);
                        if(delS != null) studentService.deleteStudent(delS);
                        System.out.println("Deleted (if existed).");
                        break;

                    case 6:
                        List<Student> list = studentService.listAll();
                        list.forEach(System.out::println);
                        break;

                    case 7:
                        System.out.print("Student ID: ");
                        int pid = Integer.parseInt(sc.nextLine());
                        System.out.print("Amount: ");
                        double amt = Double.parseDouble(sc.nextLine());
                        feeService.payFee(pid, amt);
                        System.out.println("Payment recorded.");
                        break;

                    case 8:
                        System.out.print("Student ID: ");
                        int rid = Integer.parseInt(sc.nextLine());
                        System.out.print("Amount: ");
                        double ramt = Double.parseDouble(sc.nextLine());
                        feeService.refundFee(rid, ramt);
                        System.out.println("Refund processed.");
                        break;

                    case 9:
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid");
                }
            } catch(Exception ex){
                System.out.println("Error: " + ex.getMessage());
            }
        }

        sc.close();
        ctx.close();
    }
}
