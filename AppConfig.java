package com.example.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.*;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.example.dao.*;
import com.example.service.*;
import com.example.model.Course;
import com.example.model.Student;
import com.example.model.Payment;

@Configuration
@ComponentScan(basePackages = "com.example")
@EnableTransactionManagement
public class AppConfig {

    // Use this block to switch between MySQL and H2 by commenting/uncommenting
    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource ds = new DriverManagerDataSource();
        // MySQL
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        ds.setUrl("jdbc:mysql://localhost:3306/studentdb?useSSL=false&serverTimezone=UTC");
        ds.setUsername("root");
        ds.setPassword("your_password_here");
        // For H2, you could set:
        // ds.setDriverClassName("org.h2.Driver");
        // ds.setUrl("jdbc:h2:mem:studentdb;DB_CLOSE_DELAY=-1");
        // ds.setUsername("sa"); ds.setPassword("");
        return ds;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory(DataSource dataSource){
        LocalSessionFactoryBean sf = new LocalSessionFactoryBean();
        sf.setDataSource(dataSource);
        sf.setAnnotatedClasses(Student.class, Course.class, Payment.class);
        Properties hprops = new Properties();
        hprops.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
        hprops.put("hibernate.show_sql", "true");
        hprops.put("hibernate.hbm2ddl.auto", "update"); // change for production
        hprops.put("hibernate.format_sql", "true");
        sf.setHibernateProperties(hprops);
        return sf;
    }

    @Bean
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory){
        HibernateTransactionManager tm = new HibernateTransactionManager();
        tm.setSessionFactory(sessionFactory);
        return tm;
    }

    // DAOs and Services are detected by @ComponentScan or @Repository/@Service annotations.
    // If you prefer explicit @Bean declarations, you may add them here.
}
