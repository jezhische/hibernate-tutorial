package com.jezh.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestJdbc {

    public static void main(String[] args) {

        String jdbcUrl = "jdbc:mysql://localhost:3060/hb_student_tracker?useSSL=false";
        String user = "hbstudent";
        String password = "hbstudent";

        System.out.println("Connecting to database: " + jdbcUrl);

        try {
            Connection myConn = DriverManager.getConnection(jdbcUrl, user, password);

            System.out.println("Connection successful");

        } catch (SQLException e) {
            System.out.println("ou...\n" + e.getMessage());
        }
    }
}
