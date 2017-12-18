package com.jezh.jdbc.golovach.util;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;

import java.beans.PropertyVetoException;

import static com.jezh.jdbc.golovach.config.GolovachConfig.*;

public class UserDaoJdbcC3P0 {

//    DataSource - это proxy для DriverManager, factory для connection
    private final DataSource dataSource;
    {
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
            throw new RuntimeException("Exception during configuring c3p0", e); // todo: почему пробрасывание этого
            // exception приводит к тому, что final переменная dataSource засчитывается инициализированной???
        }
    }

    public DataSource getDataSource() {
        return dataSource;
    }
}
