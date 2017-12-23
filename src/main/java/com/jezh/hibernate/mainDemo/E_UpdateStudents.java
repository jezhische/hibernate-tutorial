package com.jezh.hibernate.mainDemo;

import com.jezh.hibernate.dao.StudentDao;
import com.jezh.hibernate.daoImpl.StudentDaoImpl;
import com.jezh.hibernate.entity.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class E_UpdateStudents {

    public static void main(String[] args) {

//        StudentDao studentDao = new StudentDaoImpl();
        Student student;

        SessionFactory factory = new Configuration().configure().addAnnotatedClass(Student.class)
                .buildSessionFactory();
        Session session = factory.getCurrentSession();
        try {
            session.beginTransaction();
            student = session.get(Student.class, 4);
            if (student != null) {
                student.setFirstName("Hororo");
            }
        } catch (Exception e) {
            session.getTransaction().rollback();
            System.out.println(e.getMessage());
        } finally {
            session.getTransaction().commit();// todo: to update we only need to commit transaction
            session = factory.getCurrentSession();
            try {
                session.beginTransaction();
                System.out.println("-----------------------------------------------\n" +
                        ">>>>>>>>>>>>>>>>>>>>>>>>>" + session.get(Student.class, 4) +
                        "\n-------------------------------------------------------");
            } catch (Exception e) {
                session.getTransaction().rollback();
                System.out.println(e.getMessage());
            } finally {
                session.getTransaction().commit();
                factory.close();
            }
        }


    }
}
