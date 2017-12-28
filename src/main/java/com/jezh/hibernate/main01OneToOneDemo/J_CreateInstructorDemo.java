package com.jezh.hibernate.main01OneToOneDemo;

import com.jezh.hibernate.entity.Instructor;
import com.jezh.hibernate.entity.InstructorDetail;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class J_CreateInstructorDemo {

    public static void main(String[] args) {

        try {
            Configuration configuration = new Configuration().configure("hibernate01.cfg.xml");
            configuration.addAnnotatedClass(Instructor.class);
            configuration.addAnnotatedClass(InstructorDetail.class);
            SessionFactory factory = configuration.buildSessionFactory();
            Session session = factory.getCurrentSession();
            System.out.println(">>Session is open: " + session.isOpen());

            InstructorDetail detail = new InstructorDetail("durgur.net", "bicycle");
            Instructor instructor = null;
            try {
                session.beginTransaction();
                System.out.println(">>Session is connected: " + session.isConnected());
                System.out.println(">>Session is joined to transaction: " + session.isJoinedToTransaction());
                System.out.println(">>Session is dirty: " + session.isDirty());
                System.out.println("\n\n\n----------------------------->> Before saving in database: instructor = "
                        + instructor + ", detail = " + detail + "\n\n\n");

// todo: NB: только при CascadeType.ALL у Instructor не нужно заранее сохранять instructorDetail в бд, во всех остальных
// случаях получаем TransientObjectException: object references an unsaved transient instance - save the transient
// instance beforeQuery flushing
                session.save(detail);

                instructor = new Instructor("Hihuha", "Kobana", "l@dot.net",
                        /*session.get(InstructorDetail.class, detail.getId())*/    detail);
//                System.out.println("\n\n\n----------------------------->> After saving detail in database: instructor = "
//                       + instructor + ", detail = " + detail + "\n\n\n");
                System.out.println("\n\n\n----------------------------->> After initializing detail in instructor: " +
                        "instructor = " + instructor + ", detail = " + detail + "\n\n\n");
                session.save(instructor);
                System.out.println("\n\n\n----------------------------->> After saving instructor in database: instructor = "
                        + instructor + ", detail = " + detail + "\n\n\n");
                System.out.println(">>Session is dirty: " + session.isDirty());
                System.out.println("\n\n\n----------------->>Session is open: " + session.isOpen() + "\n\n\n");

// а вот здесь связываем этого же инструктора и новосозданного инструктора с одной и той же уже созданной ранее записью
// instructor_detail. В таблице instructor появляются две новых записи: ОБНОВЛЯЕТСЯ деталь в только что созданном
// инструкторе, и создается еще один инструктор. Оба инструктора имеют одинаковую деталь, но самой детали это пофиг,
// она об этом даже не догадывается, у нее нет ссылок на инструктора:

//                instructor.setInstructorDetailBiDir(session.get(InstructorDetail.class, 3));
//                session.save(instructor); // раскоментить эти строчки для того, чтобы добиться описанного выше
// эффекта!
                instructor = new Instructor("Honyo", "Kukana", "uyuy@dot.net");
                instructor.setInstructorDetail(session.get(InstructorDetail.class, 3));
                session.save(instructor);

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
