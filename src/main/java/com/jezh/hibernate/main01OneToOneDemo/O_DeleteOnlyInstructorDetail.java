package com.jezh.hibernate.main01OneToOneDemo;

import com.jezh.hibernate.entity.Instructor;
import com.jezh.hibernate.entity.InstructorDetail;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;

public class O_DeleteOnlyInstructorDetail {

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
                detail = session.get(InstructorDetail.class, 4);
                if (detail != null) instructor = detail.getInstructor();
                System.out.println("\n\n\n---------------------instructor: " + instructor + ", detail: "
                        + detail + "\n\n\n");

// todo: we cannot delete detail, so far this detail is the assigned field of instructor. So we need to break this link:
// (we will get: javax.persistence.EntityNotFoundException:
// deleted object would be re-saved by cascade (remove deleted object from associations):
// [com.jezh.hibernate.entity.InstructorDetail#4])
                if (instructor != null) instructor.setInstructorDetail(null);

                if (detail != null) session.delete(detail);
                System.out.println("\n\n\n---------------------instructor: " + instructor + ", detail: "
                        + detail + "\n\n\n");

//                instructors.addAll(session.createQuery("from Instructor where instructor_detail_id=3")
//                        .getResultList());

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
        System.out.println("\n\n\n---------------------instructor: " + instructor + ", detail: "
                + detail + "\n\n\n");
        instructors.forEach(System.out::println);
    }
}
