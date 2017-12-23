package com.jezh.hibernate.mainDemo;

import com.jezh.hibernate.dao.StudentDao;
import com.jezh.hibernate.daoImpl.StudentDaoImpl;
import com.jezh.hibernate.entity.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class C_FindAllStudents {

    public static void main(String[] args) {

        StudentDao studentDao = new StudentDaoImpl();

        SessionFactory factory = new Configuration().configure().addAnnotatedClass(Student.class)
                .buildSessionFactory();
        Session session = factory.getCurrentSession();

        studentDao.setSession(session);
        session.beginTransaction();
        List<Student> students = studentDao.findAll();
        session.getTransaction().commit();
        factory.close();

        students.forEach(System.out::println);
        System.out.println(">>>>>>>>>>>>>>> List type is " + students.getClass().getTypeName());// STOPSHIP: 21.12.2017
    }
}
