package com.jezh.hibernate.main03OneToManyDemo;

import com.jezh.hibernate.entity.Course;
import com.jezh.hibernate.entity.Instructor;
import com.jezh.hibernate.entity.InstructorDetail;
import com.jezh.hibernate.entity.Student;
import com.jezh.hibernate.entity.oneToOneByDirectionalDifferentProbes.Review;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class R_DeleteCourse {
    public static void main(String[] args) {
        Course course = null;
        Review review = null;
        Configuration configuration = new Configuration().configure("hibernate03.cfg.xml");
        configuration.addAnnotatedClass(Instructor.class);
        configuration.addAnnotatedClass(InstructorDetail.class);
        configuration.addAnnotatedClass(Course.class);
        configuration.addAnnotatedClass(Review.class);
        configuration.addAnnotatedClass(Student.class);
        SessionFactory factory = configuration.buildSessionFactory();
        Session session = null;
        Transaction tx = null;

        try {
            session = factory.getCurrentSession();
            tx = session.beginTransaction();

////            create a course
//            course = new Course("zig-zag-stripping");
////            adding some reviews
//            course.addReview(new Review("Ho-ho!"));
//            course.addReview(new Review("huraru!"));
//            course.addReview(new Review("copito!"));
////            save the course, and with "cascade all" leverage we must to save also reviews
//            session.save(course);

            course = session.get(Course.class, 19);
            System.out.println("\n\n\n---------------------before deleting course with id = 19 : course: "
                    + course + "\n\n\n");
            session.delete(course);
            System.out.println("\n\n\n---------------------after deleting course with id = 19 : course: "
                    + course + "\n\n\n");

            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.out.println(">>>>>>" + e.getClass().getTypeName() + ">> cannot build or destroy factory: "
                    + e.getMessage());
        } finally {
            if (session != null) session.close();
            factory.close();
        }
    }
}
