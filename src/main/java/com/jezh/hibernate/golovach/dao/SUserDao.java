package com.jezh.hibernate.golovach.dao;

import com.jezh.hibernate.golovach.entity.SUser;
import com.jezh.hibernate.golovach.exceptions.DBSystemException;
import com.jezh.hibernate.golovach.exceptions.NotUniqueEmailException;
import com.jezh.hibernate.golovach.exceptions.NotUniqueLoginException;

import java.util.List;

public interface SUserDao {
    List<SUser> getSUsers() throws DBSystemException;

    void insert(SUser user) throws NotUniqueLoginException, NotUniqueEmailException, DBSystemException;

    void deleteById(int id) throws DBSystemException;

    SUser selectUserByLogin(String login) throws DBSystemException;
}
