package com.jezh.hibernate;

import com.jezh.hibernate.entity.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.ServiceLoader;

public class TestJdbc {

    public static void main(String[] args) {

        String jdbcUrl = "jdbc:mysql://localhost:3306/hb_student_tracker?useSSL=false";
        String user = "hbstudent";
        String password = "hbstudent";

        System.out.println("Connecting to database: " + jdbcUrl);

//        try {
//            System.out.println(Class.forName("com.mysql.jdbc.Driver"));
//
//        } catch (ClassNotFoundException e) {
//            System.out.println(e.getMessage());
//        }

        try {
//            ------------------------------------------------------
//            only for testing:
            ServiceLoader<Driver> loader = ServiceLoader.load(Driver.class);
            Iterator<Driver> iterator = loader.iterator();
            iterator.forEachRemaining(System.out::println);

            System.out.println(DriverManager.getDriver("jdbc:mysql://localhost:3306/hb_student_tracker?useSSL=false"));
//            ------------------------------------------------------------------

            Connection myConn = DriverManager.getConnection(jdbcUrl, user, password);
            System.out.println(">>connection is valid: " + myConn.isValid(0));

            System.out.println("Connection successful");

            myConn.close();
            System.out.println(">>connection is closed: " + myConn.isClosed());

        } catch (SQLException e) {
            System.out.println("ou...\n" + e.getMessage());
        }

//        File hibconfig = new File("\\src\\main\\resources\\hibernate.cfg.xml");
        SessionFactory factory = new Configuration().configure("hibernate.cfg.xml") // todo: if the name of configure file is not defined,
                // hibernate will look for default name, i.e. "hibernate.cfg.xml"
                .addAnnotatedClass(Student.class)
                .buildSessionFactory();
        Session session = factory.getCurrentSession();

        Student vova = new Student("vova", "dudik", "dudik@gmail.com");
        try {
            System.out.println("factory is closed = " + factory.isClosed());

            session.beginTransaction();
            session.save(vova);
            session.getTransaction().commit();
            session = factory.getCurrentSession();
            session.beginTransaction();
            Student petya = session.get(Student.class, vova.getId());
            session.delete("vova", vova);
            session.getTransaction().commit();
            System.out.println(petya);
        } finally {
            factory.close();
            System.out.println("factory is closed = " + factory.isClosed());
        }

    }


}
