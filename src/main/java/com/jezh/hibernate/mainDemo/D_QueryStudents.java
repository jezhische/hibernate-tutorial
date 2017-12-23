package com.jezh.hibernate.mainDemo;

import com.jezh.hibernate.dao.StudentDao;
import com.jezh.hibernate.daoImpl.StudentDaoImpl;
import com.jezh.hibernate.entity.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class D_QueryStudents {

    public static final void displayList(List list) {
        System.out.println("------------------------------------------------------");
        list.forEach(System.out::println);
    }

    public static void main(String[] args) {

        StudentDao studentDao = new StudentDaoImpl();

        SessionFactory factory = new Configuration().configure().addAnnotatedClass(Student.class)
                .buildSessionFactory();
        Session session = factory.getCurrentSession();
        studentDao.setSession(session);
        Transaction tx = session.beginTransaction();

        List<Student> boboList = studentDao.findWithDifferentQuery(
// todo: NB: use the java class name and java property name (ie class fields)
                "from Student s where s.firstName='Bobo' and s.lastName='Last'");
        List<Student> boaSelectedList = studentDao.findWithDifferentQuery(
                "from Student s where s.firstName='Boa' and id between 502 and 510");
        List<Student> differentList = studentDao.findWithDifferentQuery(
                "from Student s where s.email like 'bibi%' or s.firstName='Bobo'");
        tx.commit();
        factory.close();

        displayList(boboList);
        displayList(boaSelectedList);
        displayList(differentList);
    }
}
