package com.jezh.jdbc.golovach.jdbc.impl;

import com.jezh.jdbc.golovach.jdbc.ConnectionFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static com.jezh.jdbc.golovach.daoImpl.JdbcClassicProbe.JDBC_URL;
import static com.jezh.jdbc.golovach.daoImpl.JdbcClassicProbe.PASSWORD;
import static com.jezh.jdbc.golovach.daoImpl.JdbcClassicProbe.USER;

public class ConnectionFactoryJdbc implements ConnectionFactory {
    @Override
    public Connection newConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
    }

    @Override
    public void close() throws SQLException {
//      nothing to close - поскольку объект класса ConnectionFactoryJdbc не имеет состояния, ничего не хранит. Он просто
// обращается к DriverManager и берет у него Connection. Поэтому закрывать этот объект не нужно.
    }
}
