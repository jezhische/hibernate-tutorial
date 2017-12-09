package com.jezh.jdbc.golovach.jdbc.impl;

import com.jezh.jdbc.golovach.jdbc.ConnectionFactory;
import org.apache.commons.dbcp.BasicDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static com.jezh.jdbc.golovach.config.GolovachConfig.*;

public class ConnectionFactoryJdbc implements ConnectionFactory {

    @Override
    public Connection newConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, USER, PASSWORD); // драйвер mysql уже зарегистрирован автоматически,
        // регистрации не нужно, у нас уже новое jdbc, в котором  в драйвер менеджере есть статический блок с
        // методом loadInitialDrivers();
        // , в котором есть строчка ServiceLoader<Driver> loadedDrivers = ServiceLoader.load(Driver.class);
    }

    @Override
    public void close() throws SQLException {
//      nothing to close - поскольку объект класса ConnectionFactoryJdbc не имеет состояния, ничего не хранит. Он просто
// обращается к DriverManager и берет у него Connection. Поэтому закрывать этот объект не нужно.
    }
}
