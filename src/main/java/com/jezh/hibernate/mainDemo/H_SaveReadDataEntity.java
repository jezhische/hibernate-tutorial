package com.jezh.hibernate.mainDemo;

import com.jezh.hibernate.entity.DataEntity;
import com.jezh.hibernate.exceptions.FactoryNotClosedException;
import com.jezh.hibernate.util.DataUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.Date;

public class H_SaveReadDataEntity {
    public static Configuration configuration = new Configuration().configure();
    public static SessionFactory factory;

    static {
        configuration.addAnnotatedClass(DataEntity.class);
        factory = configuration.buildSessionFactory();
    }

    public static String saveAndCheck(DataEntity dataEntity) throws FactoryNotClosedException {
        String result = null;
//        SessionFactory factory = configuration.buildSessionFactory();
        Session session = factory.getCurrentSession();
        Transaction tx = session.beginTransaction();
        try {
            session.save(dataEntity);
            if (session.get(DataEntity.class, dataEntity.getId()) != null)
                result = "\n\n\n---------------------------->>>>>>>>>>>> DataEntity instance saved successfully\n\n\n";
            else result = "\n\n\n---------------------------->>>>>>>>>>>> cannot save DataEntity instance\n\n\n";
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            System.out.println("\n\n\n----------------------->>>>>>>>>>>>>> Exception: "
                    + e.getClass().getTypeName()
                    + ": "
                    + e.getMessage()
                    + "\n\n\n");
            factory.close();
//            throw new FactoryNotClosedException("\n\n\n--------------------->>>>>>>>>>The factory isn't closed\n\n\n");
        } finally {
//            factory.close();
            session.close();
        }
        return result;
    }

    public static void printDataEntity(DataEntity dataEntity) throws FactoryNotClosedException {
        SessionFactory factory = configuration.buildSessionFactory();
        Session session = factory.getCurrentSession();
        Transaction tx = session.beginTransaction();
        try {
            System.out.println("\n\n\n------------------------------>>>>>>>>>>>>> current DataEntity instance: "
                    + session.get(DataEntity.class, dataEntity.getId()) + "\n\n\n");
        } catch (Exception e) {
            tx.rollback();
            System.out.println("\n\n\n----------------------->>>>>>>>>>>>>> Exception: "
                    + e.getClass().getTypeName()
                    + ": "
                    + e.getMessage()
                    + "\n\n\n");
            factory.close();
        } finally {
//            factory.close();
            session.close();
//            throw new FactoryNotClosedException("\n\n\n--------------------->>>>>>>>>>The factory isn't closed\n\n\n");
        }
    }

    public static void clouseTheFactory() {

        if (factory.isOpen())
            factory.close();
    }

    public static void main(String[] args) {
        DataEntity dataEntity = new DataEntity("first", "12/05/1975");
        DataEntity dataEntity2 = new DataEntity("first", "12/05/1975");
        DataEntity dataEntity3 = new DataEntity("first", "12/05/1975");
        try {
            System.out.println(saveAndCheck(dataEntity));
            System.out.println(saveAndCheck(dataEntity2));
            System.out.println(saveAndCheck(dataEntity3));
            printDataEntity(dataEntity);
            printDataEntity(dataEntity2);
            printDataEntity(dataEntity3);
        } catch (FactoryNotClosedException e) {
            System.out.println("\n\n\n--------------------->>>>>>>>>>The factory isn't closed\n\n\n");
        }
        finally {
            clouseTheFactory();
            System.exit(1);
        }
    }
}
