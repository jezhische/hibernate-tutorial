package com.jezh.hibernate.mainDemo;

import com.jezh.hibernate.entity.InstructorDetail;
import com.jezh.hibernate.entity.Student;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class I_CreateInstructorDetailDemo {

    public static void main(String[] args) {

        try {
            SessionFactory factory = new Configuration().configure("hibernate01.cfg.xml").addAnnotatedClass(InstructorDetail.class)
                    .buildSessionFactory();
            Session session = factory.getCurrentSession();
            System.out.println(">>Session is open: " + session.isOpen());

            InstructorDetail detail = new InstructorDetail("hovo.net", "kuku");
            try {
                session.beginTransaction();
                System.out.println(">>Session is connected: " + session.isConnected());
                System.out.println(">>Session is joined to transaction: " + session.isJoinedToTransaction());
                System.out.println(">>Session is dirty: " + session.isDirty());
                System.out.println("\n\n\n----------------------------->> Before saving in database: " + detail + "\n\n\n");
                session.save(detail);
                System.out.println("\n\n\n----------------------------->> After saving in database: " + detail + "\n\n\n");
                System.out.println(">>Session is dirty: " + session.isDirty());
                System.out.println("\n\n\n----------------->>Session is open: " + session.isOpen() + "\n\n\n");
                System.out.println("\n\n\n--------------------------->>>>> committing...\n\n\n");
                session.getTransaction().commit();
                System.out.println("\n\n\n----------------->>Session is open: " + session.isOpen() + "\n\n\n");
            } finally {
                factory.close();
            }
        } catch (HibernateException e) {
            System.out.println(">>>>>>" + e.getClass().getTypeName() + ">> cannot build or destroy factory: " + e.getMessage());
        }
    }
}
