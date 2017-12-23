package com.jezh.hibernate.dao;

import com.jezh.hibernate.entity.Student;
import org.hibernate.Session;

import java.util.List;

public interface StudentDao {

    Session getSession();

    void setSession(Session session);

    void create(Student student);

//    void update(Student student);

    void delete(int id);

    Student read(int id);

    List<Student> findAll();

    List<Student> findWithDifferentQuery(String query);
}
