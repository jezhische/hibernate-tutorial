package com.jezh.hibernate.main01OneToOneDemo;

import com.jezh.hibernate.entity.Instructor;
import com.jezh.hibernate.entity.InstructorDetail;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;

public class N_GetInstructorFromInstructorDetail {
    public static void main(String[] args) {

        InstructorDetail detail = null;
        Instructor instructor = null;
        List<Instructor> instructors = new ArrayList<>();
        try {
            Configuration configuration = new Configuration().configure("hibernate01.cfg.xml");
            configuration.addAnnotatedClass(Instructor.class);
            configuration.addAnnotatedClass(InstructorDetail.class);
            SessionFactory factory = configuration.buildSessionFactory();
            Session session = factory.getCurrentSession();
            System.out.println(">>Session is open: " + session.isOpen());

            try {
                session.beginTransaction();

//                instructor = session.load(Instructor.class, 1);
//                detail = instructor.getInstructorDetail();
                detail = session.get(InstructorDetail.class, 3);
                instructor = detail.getInstructor();
                instructors.addAll(session.createQuery("from Instructor where instructor_detail_id=3")
                        .getResultList());

                session.getTransaction().commit();
            } catch (Exception e) {
                session.getTransaction().rollback();
                e.printStackTrace();
            } finally {
                factory.close();
            }
        } catch (HibernateException e) {
            System.out.println(">>>>>>" + e.getClass().getTypeName() + ">> cannot build or destroy factory: "
                    + e.getMessage());
        }
        System.out.println("\n\n\n---------------------" + instructor + " "
                + detail + "\n\n\n");
        instructors.forEach(System.out::println);
    }
}
