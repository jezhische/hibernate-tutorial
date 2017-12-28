package com.jezh.hibernate.main01OneToOneDemo;

import com.jezh.hibernate.entity.oneToOneByDirectionalDifferentProbes.InstructorDetailBiDir;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class L_CreateInstructorDetailBiDirDemo {

    public static void main(String[] args) {

        try {
            Configuration configuration = new Configuration().configure("hibernate01.cfg.xml");
            configuration.addAnnotatedClass(InstructorDetailBiDir.class);
            SessionFactory factory = configuration.buildSessionFactory();
            Session session = factory.getCurrentSession();
            System.out.println(">>Session is open: " + session.isOpen());

            InstructorDetailBiDir detail = new InstructorDetailBiDir("durgur.net", "bicycle");
//            InstructorBiDir instructor = null;
            try {
                session.beginTransaction();
                System.out.println(">>Session is connected: " + session.isConnected());
                System.out.println(">>Session is joined to transaction: " + session.isJoinedToTransaction());
                System.out.println(">>Session is dirty: " + session.isDirty());
                System.out.println("\n\n\n----------------------------->> Before saving in database: detail = " + detail + "\n\n\n");

                session.save(detail);

                System.out.println("\n\n\n----------------------------->> After initializing detail in instructor: " +
                        "detail = " + detail + "\n\n\n");
                session.save(detail);
                System.out.println("\n\n\n----------------------------->> After saving instructor in database: instructor = "
                        + "detail = " + detail + "\n\n\n");
                System.out.println(">>Session is dirty: " + session.isDirty());
                System.out.println("\n\n\n----------------->>Session is open: " + session.isOpen() + "\n\n\n");

                System.out.println("\n\n\n--------------------------->>>>> committing...\n\n\n");
                session.getTransaction().commit();

                System.out.println("\n\n\n----------------->>Session is open: " + session.isOpen() + "\n\n\n");
            } finally {
                factory.close();
            }
        } catch (HibernateException e) {
            System.out.println(">>>>>>" + e.getClass().getTypeName() + ">> cannot build or destroy factory: "
                    + e.getMessage());
        }
    }
}
