package com.jezh.hibernate.mainDemo;

import com.jezh.hibernate.entity.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class G_DeleteStudent {

    public static void main(String[] args) {
        Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
        configuration.addAnnotatedClass(Student.class);
        SessionFactory factory = configuration.buildSessionFactory();
        Session session = factory.getCurrentSession();

        session.beginTransaction();

        // todo: to learn: load() instead of get() can return proxy, if the object isn't exist (to avoid
        // or to prevent NullPointerException)
        try {
            System.out.println("-----------------------------------------------\n" +
                    ">>>>>>>>>>>>>>>>>>>>>>>>>" + session.load(Student.class, 501) +
                    "\n-------------------------------------------------------");
            try {
                session.delete(session.get(Student.class, 501));
            } catch (Exception e) {
                System.out.println("----------------------------------------->>>>>>>>>>>>>>>>>>>>>>>>>>>> Exception: "
                        + e.getClass().getTypeName() + ": " + e.getMessage());
            }
            try {
                session.delete(session.get(Student.class, 501));
            } catch (Exception e) {
                System.out.println("----------------------------------------->>>>>>>>>>>>>>>>>>>>>>>>>>>> Exception: "
                        + e.getClass().getTypeName() + ": " + e.getMessage());
            }
            System.out.println("-----------------------------------------------\n" +
                    ">>>>>>>>>>>>>>>>>>>>>>>>>00000" + session.get(Student.class, 501) +
                    "\n-------------------------------------------------------");

            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Exception: " + e.getClass().getTypeName()
                    + ": " + e.getMessage());
        } finally {
            factory.close();
        }
    }
}
