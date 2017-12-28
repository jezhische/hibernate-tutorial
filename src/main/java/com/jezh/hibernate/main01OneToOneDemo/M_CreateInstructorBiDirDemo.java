package com.jezh.hibernate.main01OneToOneDemo;

import com.jezh.hibernate.entity.oneToOneByDirectionalDifferentProbes.InstructorBiDir;
import com.jezh.hibernate.entity.oneToOneByDirectionalDifferentProbes.InstructorDetailBiDir;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class M_CreateInstructorBiDirDemo {

    public static void main(String[] args) {

        try {
            Configuration configuration = new Configuration().configure("hibernate01.cfg.xml");
            configuration.addAnnotatedClass(InstructorBiDir.class);
            configuration.addAnnotatedClass(InstructorDetailBiDir.class);
            SessionFactory factory = configuration.buildSessionFactory();
            Session session = factory.getCurrentSession();
            System.out.println(">>Session is open: " + session.isOpen());

            InstructorDetailBiDir detail = new InstructorDetailBiDir("durgur.net", "bicycle");
            InstructorBiDir instructor = null;
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

                instructor = new InstructorBiDir("Hihuha", "Kobana", "l@dot.net",
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

                instructor = new InstructorBiDir("Honyo", "Kukana", "uyuy@dot.net");
                instructor.setInstructorDetailBiDir(session.get(InstructorDetailBiDir.class, 3));
                session.save(instructor);

                detail = new InstructorDetailBiDir("horia-moria", "postrur", instructor);
// todo: NB: new transient detail must be saved, and does not matter before setInstructorDetailBiDir(detail) or after
//                session.save(detail);
                session.get(InstructorBiDir.class, 2).setInstructorDetailBiDir(detail);
                session.save(detail);

                InstructorBiDir ibd = session.get(InstructorBiDir.class, 4);
                detail = new InstructorDetailBiDir("korlonpoma", "kopuri",
                        ibd);
                System.out.println("\n\n\n\n\n\n------------*********************" + detail + "\n\n\n\n\n\n\n");
                ibd.setInstructorDetailBiDir(detail);
                session.save(detail);
                System.out.println("\n\n\n\n\n\n------------*********************" + detail + "\n\n\n\n\n\n\n");
//                ibd.setInstructorDetailBiDir(detail);
                session.save(ibd);
                System.out.println("\n\n\n\n\n\n------------*********************"
                        + session.get(InstructorDetailBiDir.class, detail.getId()) + "\n\n\n\n\n\n\n");

                instructor = new InstructorBiDir("ooooooo", "eeeeeee", "gg@go.net");
                detail = new InstructorDetailBiDir("xxxxxxxxxx", "UUUUUUUUUUU", instructor);
                session.save(detail);
                System.out.println("\n\n\n\n\n\n------------o-o-o-o-o-o-o-o-o-o-o-o-o-o-o  "
                        + session.get(InstructorDetailBiDir.class, detail.getId())
                        + "///" + detail.getInstructorBiDir()
                        + ", " + session.get(InstructorBiDir.class, instructor.getId()) + "\n\n\n\n\n\n\n");
                session.save(instructor);
                System.out.println("\n\n\n\n\n\n------------o-o-o-o-o-o-o-o-o-o-o-o-o-o-o  "
                        + session.get(InstructorDetailBiDir.class, detail.getId())
                        + "///" + detail.getInstructorBiDir()
                        + ", " + session.get(InstructorBiDir.class, instructor.getId())
                        + "///" + instructor.getInstructorDetailBiDir() + "\n\n\n\n\n\n\n");

                System.out.println("\n\n\n--------------------------->>>>> committing...\n\n\n");
                session.getTransaction().commit();

                System.out.println("\n\n\n----------------->>Session is open: " + session.isOpen() + "\n\n\n");
            } finally {
                session.close();
                System.out.println("--------------- /*/*/*/*/* check if close() throws exception when session is closed " +
                        "already");
                session.close();
                factory.close();
            }
        } catch (HibernateException e) {
            System.out.println(">>>>>>" + e.getClass().getTypeName() + ">> cannot build or destroy factory: "
                    + e.getMessage());
        }
    }
}
