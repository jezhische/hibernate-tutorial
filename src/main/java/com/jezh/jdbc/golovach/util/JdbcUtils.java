package com.jezh.jdbc.golovach.util;

import com.jezh.jdbc.golovach.mainProbes.SUserDaoImplProbe;
import com.jezh.jdbc.golovach.exceptions.DBSystemException;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;

import static com.jezh.jdbc.golovach.mainProbes.SUserDaoImplProbe.SELECT_USER_ID_BY_EMAIL;
import static com.jezh.jdbc.golovach.mainProbes.SUserDaoImplProbe.SELECT_USER_ID_BY_LOGIN;

public class JdbcUtils {

    private static boolean initialized;

    public static synchronized void initDriver(String driverName) {
        if (!initialized) {
            try {
                Class.forName(driverName);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                throw new RuntimeException(">>>Can't initialize driver '" + driverName + "' " + e);
            }
        } initialized = true;
    }


    public static void rollbackQuietly(Connection connection) {
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                // NOP
            }
        }
    }

    public static void closeQuietly(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                // NOP
            }
        }
    }

    public static void closeQuietly(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                // NOP
            }
        }
    }

    public static void closeQuietly(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                // NOP
            }
        }
    }

    public static void avgTime(SUserDaoImplProbe test, int count) {
        ArrayList<Long> times = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            long start = System.nanoTime();
            try {
                System.out.println(test.getSUsers());
            } catch (DBSystemException e) {
                System.out.println(e.getMessage() + ": " + e.getCause());
            }
            long passed = (System.nanoTime() - start) / 1000;
            times.add(passed);
            System.out.println(passed);
        }
        long rawAvg = times.stream().mapToLong(l -> l).sum() / (times.size() * 1000); // хотя вроде
        // нужно l -> l.longValue()
        System.out.println(">> rawAvg = " + rawAvg + "ms");
        Collections.sort(times);
        int calculationStart = (int) Math.floor(count * 0.2);
        int calculationFinish = (int) Math.floor(count * 0.8);
        long avg = times.subList(calculationStart, calculationFinish).stream().mapToLong(l -> l).sum()
                / ((calculationFinish - calculationStart) * 1000);
        System.out.println(">> avg = " + avg + "ms");

    }

    public static boolean existWithLogin(Connection conn, String login) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(SELECT_USER_ID_BY_LOGIN);
        ps.setString(1, login);
        ResultSet rs = ps.executeQuery();
//        System.out.println(rs.getString("first_name"));
        return rs.next();
    }

    public static boolean existWithEmail(Connection conn, String email) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(SELECT_USER_ID_BY_EMAIL);
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }
}
