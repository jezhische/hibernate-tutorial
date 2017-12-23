package com.jezh.hibernate.mainDemo;

import com.jezh.hibernate.dao.StudentDao;
import com.jezh.hibernate.daoImpl.StudentDaoImpl;
import com.jezh.hibernate.entity.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class B_ReadStudentDemo {

    public static void main(String[] args) {
        SessionFactory factory = new Configuration().configure().addAnnotatedClass(Student.class)
                .addAnnotatedClass(Student.class).buildSessionFactory(); // я так понимаю, может быть добавлено
        // любое количество annotated classes(this method makes: "Read metadata from the annotations associated
        // with this class")
        Session session = factory.getCurrentSession();
        StudentDao dao = new StudentDaoImpl();
        dao.setSession(session);
        Student boa = new Student("Boa", "Last", "boa@gmail.com");
        String beforeSaving = boa.toString();
        Transaction trans = session.beginTransaction();
        dao.create(boa);
        dao.create(boa); // первый раз происходит ACTION_MERGE, а второй раз - ACTION_REFRESH
        String afterSaving = boa.toString();
        System.out.printf("\t>>>>>> beforeSaving = %s, \n\t\tafterSaving = %s, \n\t\tretrieving instance = %s\n",
                beforeSaving, afterSaving, dao.read(boa.getId()));
        System.out.println("----------------------------->>>>Equals into this session before committing: " + boa.equals(
                session.get(Student.class, boa.getId())));
        System.out.println("--------------------->>>>Equals of new object into this session before committing: "
                + new Student("Boa", "Last", "boa@gmail.com").equals(session.get(
                Student.class, boa.getId())));

//        session.getTransaction().commit();
        trans.commit();

        session = factory.getCurrentSession();
        dao.setSession(session);
        trans = session.beginTransaction();
        System.out.println("\t>>>>>>>>>>" + dao.read(boa.getId()));
// todo: NB: if equals() is not overrode into Entity class, hibernate gives equality inside of open session, but after
// it closed, hibernate hasn't way to determine this. The main problem is duplicating of objects in databes table.
// So we need to override equals() and hashCode().
         System.out.println("--------------------->>>>Equals into another session after first committing: " + boa.equals(
                session.get(Student.class, boa.getId())));
        System.out.println("--------------------->>>>Equals of new object into another session after first committing: "
                + new Student("Boa", "Last", "boa@gmail.com").equals(session.get(
                        Student.class, boa.getId())));
        trans.commit();


        factory.close();
    }
}
