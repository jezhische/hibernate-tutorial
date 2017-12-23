package com.jezh.hibernate.daoImpl;

import com.jezh.hibernate.dao.StudentDao;
import com.jezh.hibernate.entity.Student;
import org.hibernate.Session;

import java.util.List;

public class StudentDaoImpl implements StudentDao {

    private Session session;

    public StudentDaoImpl() {
    }

    //
//    public StudentDaoImpl(Session session) {
//        this.session = session;
//    }
    @Override
    public Session getSession() {
        return session;
    }

    @Override
    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public void create(Student student) {
        if (student != null) {
            session.save(student);
        }
    }

//    @Override
//    public void update(Student student) {
//
//    }

    @Override
    public void delete(int id) {
        session.delete(session.get(Student.class, id));
    }

    @Override
    public Student read(int id) {
        return session.get(Student.class, id);
    }

    @Override
    public List<Student> findAll() {
// todo: NB: Upper case - here needs to use Java class name, not table name (class is "Student", though table is 'student')
        return session.createQuery("from Student").getResultList();
    }

    @Override
    public List<Student> findWithDifferentQuery(String query) {
        return session.createQuery(query).getResultList();
    }
}
