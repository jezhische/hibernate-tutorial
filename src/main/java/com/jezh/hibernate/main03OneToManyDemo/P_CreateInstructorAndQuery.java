package com.jezh.hibernate.main03OneToManyDemo;

import com.jezh.hibernate.entity.Course;
import com.jezh.hibernate.entity.Instructor;
import com.jezh.hibernate.entity.InstructorDetail;
import com.jezh.hibernate.entity.Student;
import com.jezh.hibernate.entity.oneToOneByDirectionalDifferentProbes.Review;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;

import java.util.List;

public class P_CreateInstructorAndQuery {

    public static void main(String[] args) {
//        ServiceRegistry srvc = null;
        Instructor instructor = null;
        InstructorDetail detail = null;
        Course course = null;
        Review review = null;
        Configuration configuration = new Configuration().configure("hibernate03.cfg.xml");
        configuration.addAnnotatedClass(Instructor.class);
        configuration.addAnnotatedClass(InstructorDetail.class);
        configuration.addAnnotatedClass(Course.class);
        configuration.addAnnotatedClass(Review.class);
        configuration.addAnnotatedClass(Student.class);

//        srvc = new StandardServiceRegistryBuilder()
//                .applySettings(configuration.getProperties()).build();

        SessionFactory factory = configuration.buildSessionFactory();
        Session session = null;
        Transaction tx = null;

        try {
            session = factory.getCurrentSession();
            tx = session.beginTransaction();
            instructor = new Instructor("Curio", "Dimbolio", "net@stack.overflow");
            detail = new InstructorDetail("http://huhu.youtube.com", "breeding");
            course = new Course("Animal breeding");
            review = new Review("some comment");
            review.setCourse(course);
            instructor.setInstructorDetail(detail);
            instructor.addCourse(course);
            course.addReview(review);
            session.save(review);
            System.out.println("\n\n\n---------------------transient instructor: " + instructor + ", \ndetail: "
                    + detail + ", course: " + course + "\n----------------------review = " + review + "\n\n\n");
            session.save(instructor);
            System.out.println("\n\n\n---------------------persistent instructor: " + instructor + ", \ndetail: "
                    + detail + ", course: " + course + "\n----------------------review = " + review + "\n\n\n");
            session.save(course);
            System.out.println("\n\n\n---------------------after saving course: instructor: " + instructor + ", \ndetail: "
                    + detail + ", course: " + course + "\n----------------------review = " + review + "\n\n\n");
            course = new Course("Huhruvania");
            session.save(course);
            instructor.addCourse(course);
            session.save(instructor);
            System.out.println("\n\n\n---------------------after saving with primarily saved course: instructor: "
                    + instructor + ", \ndetail: " + detail + ", course: " + course +
                    "\n----------------------review = " + review + "\n\n\n");

            course = new Course("GOROATIA");
            course.setInstructor(instructor);
            System.out.println("\n\n\n---------------------before saving course with primarily set instructor : instructor: "
                    + instructor + ", \ndetail: " + detail + ", course: " + course +
                    "\n----------------------review = " + review + "\n\n\n");
            session.save(course);
            System.out.println("\n\n\n---------------------after saving course with primarily set instructor : instructor: "
                    + instructor + ", \ndetail: " + detail + ", course: " + course +
                    "\n----------------------review = " + review + "\n\n\n");

            session.save(instructor);
            System.out.println("\n\n\n---------------------after saving instructor: instructor: "
                    + instructor + ", \ndetail: " + detail + ", course: " + course +
                    "\n----------------------review = " + review + "\n\n\n");
            instructor.addCourse(course);
            session.save(course);
            System.out.println("\n\n\n---------------------after saving course when it was added to instructor : " +
                    "instructor: " + instructor + ", \ndetail: " + detail + ", course: " + course +
                    "\n----------------------review = " + review + "\n\n\n");
            System.out.println("\n\n\n---------------------after saving instructor with added course : instructor: "
                    + instructor + ", \ndetail: " + detail + ", course: " + course +
                    "\n----------------------review = " + review + "\n\n\n");

//            System.out.println("\n\n\n---------------------after saving course when it was added to instructor : " +
//                    "instructor: " + instructor + ", \ndetail: " + detail + ", course: " + course + "\n\n\n");

// if I add next line, I'll get LazyInitializationException in the line 95 System.out.println(instructor.getCourses()):
// "failed to lazily initialize a collection of role: com.jezh.hibernate.entity.Instructor.courses, could not initialize
// proxy - no Session", because of @OneToMany(fetch = FetchType.LAZY...) private List<Course> courses; in Instructor.
//            instructor = session.get(Instructor.class, 28);

// So I have to handle it: either CALL GETTER instructor.getCourses() while the session is still open,
// todo: or I must to do HQL JOIN FETCH to cope LazyInitializationException
            Query<Instructor> query = session.createQuery(
                    "select i from Instructor i JOIN FETCH i.courses where i.id=:theInstructorId",
                    Instructor.class);
            query.setParameter("theInstructorId", 28);
            instructor = query.getSingleResult();

// And a version of query to get courses for a given instructor
            Query<Course> queryC = session.createQuery(
                    "select c from Course c where c.instructor.id=:theInstructorId",
                    Course.class);
            queryC.setParameter("theInstructorId", 25);
            List<Course> tempCourses = queryC.getResultList();
            System.out.println("\n\n\n------------->>>>>>>>>>>>>>>>>>>>>>>>> tempCourses = " +
                    "\n===================== " + tempCourses + "\n\n\n--------------------------------");



            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.out.println(">>>>>>" + e.getClass().getTypeName() + ">> cannot build or destroy factory: "
                    + e.getMessage());
        } finally {
            if (session != null) session.close();
            factory.close();
        }
        System.out.println(instructor.getInstructorDetail());
        System.out.println(instructor.getCourses());
        System.out.println(detail.getInstructor());
        System.out.println(review);
        System.out.println(instructor.getCourses().get(0).getReviews());
    }
}
