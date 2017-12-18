package com.jezh.jdbc.golovach.z_examples;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static com.jezh.jdbc.golovach.config.GolovachConfig.JDBC_URL;

public class ConnectionAccess {
    private static ThreadLocal<Connection> connectionHolder = new ThreadLocal<Connection>() { // or:
//        = ThreadLocal.withInitial(() -> {
//            Connection conn = null;
//            try {
//                conn = DriverManager.getConnection(JDBC_URL);
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//            return conn;
//        });

        public Connection initialValue() {
            Connection conn = null;
            try {
                conn = DriverManager.getConnection(JDBC_URL);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return conn;
        }
    };

    // this call provides thread-safe connection:
    public static Connection getConnection() {
        return connectionHolder.get();
    }
}
