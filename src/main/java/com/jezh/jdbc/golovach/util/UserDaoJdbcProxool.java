package com.jezh.jdbc.golovach.util;

import org.logicalcobwebs.proxool.ProxoolException;
import org.logicalcobwebs.proxool.ProxoolFacade;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import static com.jezh.jdbc.golovach.daoImpl.JdbcClassicProbe.*;

public class UserDaoJdbcProxool {

    static {
//        поскольку proxool - это драйвер, регистрируем его:
        try {
            Class.forName("org.logicalcobwebs.proxool.ProxoolDriver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("There is no proxool driver in classpath", e);
        }
        Properties info = new Properties();
        info.setProperty("proxool.maximum-connection-found", "10");
        info.setProperty("proxool.house-keeping-test-sql", "select CURRENT_DATE");
        info.setProperty("USER", USER);
        info.setProperty("PASSWORD", PASSWORD);

        String alias = "production"; // можно открыть несколько пулов, используя url по алиасу: см. ниже
        // url, registerConnectionPool(url, info), getConnection()
        String url = "proxool." + alias + ":" + DRIVER_CLASS_NAME + ":" + JDBC_URL;
        try {
            ProxoolFacade.registerConnectionPool(url, info);
        } catch (ProxoolException e) {
            throw new RuntimeException("Some error configuring proxool", e);
        }
    }
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection("proxool.production");
    }
}
