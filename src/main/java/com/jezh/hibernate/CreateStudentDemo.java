package com.jezh.hibernate;

import com.jezh.hibernate.entity.Student;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class CreateStudentDemo {

    private static final Log log = LogFactory.getLog(CreateStudentDemo.class);

    public static void main(String[] args) {

        Logger logger = LogManager.getLogger(); // to initialize logger - в общем, не требуется

        try {
            SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Student.class)
                    .buildSessionFactory();
            Session session = factory.getCurrentSession();
            System.out.println(">>Session is open: " + session.isOpen());
            try {
                System.out.println(">>Session is connected: " + session.isConnected());
            } catch (HibernateException e) {
                System.out.println(">>" + e.getClass().getTypeName() + ">> Session transaction isn't began: " + e.getMessage());
            }
            try {
                System.out.println(">>Session is joined to transaction: " + session.isJoinedToTransaction());
            } catch (HibernateException e) {
                System.out.println(">>" + e.getClass().getTypeName() + ">> Session transaction isn't began: " + e.getMessage());
            }

            try {
                System.out.println("**************** Creating new Student object...");
                Student bobo = new Student("Bobo", "Last", "bobolast@gmail.com");
                System.out.println("**************** Session begins transaction...");
                session.beginTransaction();
                System.out.println(">>Session is connected: " + session.isConnected());
                System.out.println(">>Session is joined to transaction: " + session.isJoinedToTransaction());
                System.out.println(">>Session is dirty: " + session.isDirty());
                System.out.println("**************** Saving Student object...");
                session.save(bobo);
                System.out.println(">>Session is dirty: " + session.isDirty());
                System.out.println("**************** Session flushing...");
                try {
                    session.flush();
                } catch (IllegalStateException e) {
                    System.out.println(">>" + e.getClass().getTypeName() + ">> Session.flush(): " + e.getMessage());
                }
                System.out.println("**************** Session commiting...");
                session.getTransaction().commit();

// https://stackoverflow.com/questions/4040761/control-the-hibernate-sessionwhen-to-close-it-manually

//the most common pattern in a multi-user client/server application is session-per-request.
//todo: if you use sessionFactory.getCurrentSession(), you'll obtain a "current session" which is bound to the lifecycle
// of the transaction and will be automatically flushed and closed when the transaction ends (commit or rollback).
//if you decide to use sessionFactory.openSession(), you'll have to manage the session yourself
// and to flush and close it "manually".
// To implement a session-per-request pattern, prefer the first approach (much easier and less verbose).
// Use the second approach to implement long conversations.

                System.out.println(">>Session is open: " + session.isOpen());
                System.out.println(">>Session is connected: " + session.isConnected());
                try {
                    System.out.println(">>Session is joined to transaction: " + session.isJoinedToTransaction());
                } catch (IllegalStateException expected) {
                    log.debug("session.isJoinedToTransaction", expected);
                    System.out.println(">>" + expected.getClass().getTypeName() + ">> Session transaction is done: "
                            + expected.getMessage());
                }
            } finally {
                System.out.println("**************** Session flushing...");
                try {
                    session.flush();
                } catch (IllegalStateException e) {
                    System.out.println(">>" + e.getClass().getTypeName() + ">> Session.flush(): " + e.getMessage());
                }
                System.out.println("**************** Closing the session...");
                session.close();
                System.out.println(">> Session is closed: " + !session.isOpen());
                System.out.println("**************** Closing the SessionFactory...");
                factory.close();
                System.out.println(">> Session is closed: " + !session.isOpen());
            }
        } catch (HibernateException e) {
            System.out.println(">>>>>>" + e.getClass().getTypeName() + ">> cannot build or destroy factory: " + e.getMessage());
        }
    }
}
