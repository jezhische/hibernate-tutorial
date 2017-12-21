package com.jezh.hibernate.golovach.jdbc.impl;

import com.jezh.hibernate.golovach.jdbc.ConnectionFactory;
import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

import static com.jezh.hibernate.golovach.config.GolovachConfig.*;

public class ConnectionFactoryC3P0 implements ConnectionFactory {

    private DataSource dataSource;

    public ConnectionFactoryC3P0() throws SQLException {
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        try {
            cpds.setDriverClass(DRIVER_CLASS_NAME);
            cpds.setJdbcUrl(JDBC_URL); // database name
            cpds.setUser(USER); // table name
            cpds.setPassword(PASSWORD);

            cpds.setMaxStatements(180);
            cpds.setMaxStatementsPerConnection(10);
            cpds.setMinPoolSize(1);
            cpds.setAcquireIncrement(1);
            cpds.setMaxPoolSize(20);

            dataSource = cpds;
        } catch (PropertyVetoException e) {
            throw new RuntimeException("Exception during configuring c3p0", e);
        }
    }

    @Override
    public Connection newConnection() throws SQLException {
        return dataSource.getConnection();
    }

    @Override
    public void close() throws SQLException {
        ((ComboPooledDataSource)dataSource).close();
    }
}
