package com.jezh.hibernate.main01OneToOneDemo;


import com.jezh.hibernate.entity.Instructor;
import com.jezh.hibernate.entity.InstructorDetail;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class K_DeleteInstructorDemo {

    public static void main(String[] args) {
        Configuration configuration = new Configuration().configure("hibernate01.cfg.xml");
        SessionFactory factory = configuration.addAnnotatedClass(Instructor.class)
                .addAnnotatedClass(InstructorDetail.class).buildSessionFactory();
        Session session = factory.getCurrentSession();
        Transaction tx = null;
        List<Instructor> instructors = null;
        int deletedInstructorId = 0;
        boolean isExistedDetail = false;
        boolean isExistedInstructor = false;
        try {
            tx = session.beginTransaction();

            instructors = session
                    .createQuery("from Instructor where instructor_detail_id=3")
                    .getResultList();
            if (!instructors.isEmpty()) {
                Instructor forDeleting = instructors.get(0);
                deletedInstructorId = forDeleting.getId();
                InstructorDetail detail = forDeleting.getInstructorDetail();
                session.delete(forDeleting);
                isExistedDetail = session.contains(detail);
                isExistedInstructor = session.contains(forDeleting);
            }


            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.out.println(e.getClass().getTypeName() + ": " + e.getMessage());
        } finally {
            factory.close();
        }

        System.out.println("\n\n\n----------------------->>>>>>>>>>>>>>");
        System.out.println("***********************>>>>>>>>> deletedInstructorId = " + deletedInstructorId
                + "\n***************************>>>>>>>> is the deletedInstructor detail is existed: " + isExistedDetail
                + "\n***************************>>>>>>>> is the deletedInstructor is existed: " + isExistedInstructor);
        if (instructors != null) instructors.forEach(System.out::println);
        else System.out.println("------------------ fail: instructors == null");
        System.out.println("----------------------->>>>>>>>>>>>>>\n\n\n");
    }
}
