package com.jezh.hibernate.mainDemo;

import com.jezh.hibernate.entity.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;

import static com.jezh.hibernate.mainDemo.D_QueryStudents.displayList;

public class F_UpdateFieldsOfStudents {

    public static void main(String[] args) {

//        StudentDao studentDao = new StudentDaoImpl();
        Student student;
        List<Student> students = new ArrayList<>();

        SessionFactory factory = new Configuration().configure().addAnnotatedClass(Student.class)
                .buildSessionFactory();
        Session session = factory.getCurrentSession();
        try {
            session.beginTransaction();

            // Updating some field in all the Students entry:
            session.createQuery("update Student set lastName='Next'").executeUpdate();

            students = session.createQuery("from Student").getResultList();
        } catch (Exception e) {
            session.getTransaction().rollback();
            System.out.println(e.getMessage());
        } finally {
                session.getTransaction().commit();
                factory.close();
            }
        if (students != null)
            displayList(students);
        else System.out.println(">>>>>>>>>>>>>>>>> failed");
    }
}
