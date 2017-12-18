package com.jezh.jdbc.golovach.dao;

import com.jezh.jdbc.golovach.entity.SUser;
import com.jezh.jdbc.golovach.exceptions.DBSystemException;
import com.jezh.jdbc.golovach.exceptions.NotUniqueEmailException;
import com.jezh.jdbc.golovach.exceptions.NotUniqueLoginException;

import java.util.List;

public interface SUserDao {
    List<SUser> getSUsers() throws DBSystemException;

    void insert(SUser user) throws NotUniqueLoginException, NotUniqueEmailException, DBSystemException;

    void deleteById(int id) throws DBSystemException;

    SUser selectUserByLogin(String login) throws DBSystemException;
}
