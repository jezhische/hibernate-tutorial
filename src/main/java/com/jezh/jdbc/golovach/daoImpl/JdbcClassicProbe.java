package com.jezh.jdbc.golovach.daoImpl;

import com.jezh.jdbc.golovach.dao.ConnectionFactoryFactory;
import com.jezh.jdbc.golovach.dao.StudentDao;
import com.jezh.jdbc.golovach.jdbc.ConnectionFactory;
import com.jezh.jdbc.golovach.util.JdbcUtils;
import com.jezh.jdbc.golovach.util.UserDaoJdbcC3P0;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.jezh.jdbc.golovach.config.GolovachConfig.DRIVER_CLASS_NAME;

public class JdbcClassicProbe implements StudentDao {

    private static final String SELECT_ALL_SQL = "select * from student";

    private ConnectionFactory connectionFactory;

//    //    DataSource - это proxy для DriverManager, factory для connection
//    private DataSource dataSource = new UserDaoJdbcC3P0().getDataSource(); // один из вариантов ConnectionPool
////    private UserDaoJdbcProxool udjProxool = new UserDaoJdbcProxool(); // еще вариант ConnectionPool

    static {
        JdbcUtils.initDriver(DRIVER_CLASS_NAME);
    }

    public JdbcClassicProbe() {
    }

    public JdbcClassicProbe(ConnectionFactoryFactory.FactoryType factoryType) {
        ConnectionFactoryFactory.setType(factoryType);
        connectionFactory = ConnectionFactoryFactory.newConnectionFactory();
    }



    @Override
    public List<String> getTableFields() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<String> list = new ArrayList<>();
        try {
            connection = connectionFactory.newConnection();
//            connection = dataSource.getConnection();
//            connection = udjProxool.getConnection();
//            connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            connection.setAutoCommit(false);

            statement = connection.createStatement();
            resultSet = statement.executeQuery(SELECT_ALL_SQL);
            if (resultSet != null) {
                list.add(resultSet.getString("first_name"));
                list.add(resultSet.getString("last_name"));
                list.add(resultSet.getString("email_name"));
            }
            connection.commit();
        } catch (SQLException e) {
            JdbcUtils.rollbackQuietly(connection);
//            connection.rollback();
//            throw new DBSystemException("Can't execute SQL = '" + SELECT_ALL_SQL + "' ", e);
            System.out.println(e.getMessage());
        } finally {
            try {
                if (connection != null)
                    connection.rollback();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            JdbcUtils.closeQuietly(resultSet);
            JdbcUtils.closeQuietly(statement);
            JdbcUtils.closeQuietly(connection);
        }
        return list;
    }

    public void avgTime(int count) {
        ArrayList<Long> times = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            long start = System.nanoTime();
            getTableFields();
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

    public static void main(String[] args) {

        JdbcClassicProbe test = new JdbcClassicProbe(ConnectionFactoryFactory.FactoryType.DBCP);
        test.avgTime(100);
    }
}
