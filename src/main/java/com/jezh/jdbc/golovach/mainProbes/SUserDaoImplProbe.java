package com.jezh.jdbc.golovach.mainProbes;

import com.jezh.jdbc.golovach.entity.SUser;
import com.jezh.jdbc.golovach.exceptions.DBSystemException;
import com.jezh.jdbc.golovach.exceptions.NotUniqueEmailException;
import com.jezh.jdbc.golovach.exceptions.NotUniqueLoginException;
import com.jezh.jdbc.golovach.jdbc.ConnectionFactoryFactory;
import com.jezh.jdbc.golovach.dao.SUserDao;
import com.jezh.jdbc.golovach.jdbc.ConnectionFactory;
import com.jezh.jdbc.golovach.util.JdbcUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.jezh.jdbc.golovach.config.GolovachConfig.DRIVER_CLASS_NAME;

public class SUserDaoImplProbe implements SUserDao {

    public static final String SELECT_ALL_SQL = "select * from s_user";
    public static final String DELETE_BY_ID_SQL = "delete from s_user where id=?";
    public static final String INSERT_SQL = "insert into s_user(login, s_name, email) values(?, ?, ?)";
    public static final String SELECT_USER_BY_LOGIN = "select * from s_user where login=?";
    public static final String SELECT_USER_ID_BY_LOGIN = "select id from s_user where login=?";
    public static final String SELECT_USER_ID_BY_EMAIL = "select id from s_user where email=?";

    private ConnectionFactory connectionFactory;

//    //    DataSource - это proxy для DriverManager, factory для connection
//    private DataSource dataSource = new UserDaoJdbcC3P0().getDataSource(); // один из вариантов ConnectionPool
////    private UserDaoJdbcProxool udjProxool = new UserDaoJdbcProxool(); // еще вариант ConnectionPool

    static {
        JdbcUtils.initDriver(DRIVER_CLASS_NAME);
    }

//    empty constructor just in case
    public SUserDaoImplProbe() {
    }
// working constructor
    public SUserDaoImplProbe(ConnectionFactoryFactory.FactoryType factoryType) {
        ConnectionFactoryFactory.setType(factoryType); // нужно установить тип фабрики до создания экземпляра
        // newConnectionFactory(), поскольку setType() назначает статическое поле currentType, и по дефолту
        // currentType = FactoryType.RAW, так что если вначале создать экземпляр, то и получим new ConnectionFactoryJdbc()
        connectionFactory = ConnectionFactoryFactory.newConnectionFactory();
    }



    @Override
    public List<SUser> getSUsers() throws DBSystemException {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        List<SUser> list = new ArrayList<>();
        try {
            connection = connectionFactory.newConnection();
//            connection = dataSource.getConnection();
//            connection = udjProxool.getConnection();
//            connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            connection.setAutoCommit(false);

            statement = connection.createStatement();
            rs = statement.executeQuery(SELECT_ALL_SQL);
            while(rs.next()) { // тем самым перемещаем курсор на первую строчку таблицы, иначе получим
                // "Before start of result set" exception
                list.add(new SUser(rs.getInt("id"), rs.getString("login"),
                        rs.getString("s_name"), rs.getString("email")));
            }
//            System.out.println(JdbcUtils.existWithLogin(connection, "jezh"));
            connection.commit();
        } catch (SQLException e) {
            JdbcUtils.rollbackQuietly(connection);
            throw new DBSystemException("Can't execute SQL = '" + SELECT_ALL_SQL + "' ", e);
        } finally {
            JdbcUtils.closeQuietly(rs);
            JdbcUtils.closeQuietly(statement);
            JdbcUtils.closeQuietly(connection);
        }
        return list;
    }

    @Override
    public void insert(SUser user) throws NotUniqueLoginException, NotUniqueEmailException, DBSystemException {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = connectionFactory.newConnection();
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            conn.setAutoCommit(false);

            if (JdbcUtils.existWithLogin(conn, user.getLogin())) {
                throw new NotUniqueLoginException(String.format(">>Login '%s' already exists", user.getLogin()));
            }
            if (JdbcUtils.existWithEmail(conn, user.getEmail())) {
                throw new NotUniqueEmailException(String.format(">>Email '%s' already exists", user.getEmail()));
            }

            ps = conn.prepareStatement(INSERT_SQL);
            ps.setString(1, user.getLogin());
            ps.setString(2, user.getName());
            ps.setString(3, user.getEmail());

            ps.executeUpdate();
            conn.commit();

        } catch (SQLException e) {
            JdbcUtils.rollbackQuietly(conn);
            throw new DBSystemException(String.format("Can't execute SQL '%s'", INSERT_SQL), e);
        } finally {
            JdbcUtils.closeQuietly(ps);
            JdbcUtils.closeQuietly(conn);
        }

    }

    @Override
    public void deleteById(int id) throws DBSystemException {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = connectionFactory.newConnection();
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            conn.setAutoCommit(false);

            ps = conn.prepareStatement(DELETE_BY_ID_SQL);
            ps.setInt(1, id);
            ps.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            JdbcUtils.rollbackQuietly(conn);
            throw new DBSystemException(String.format("Can't delete s_user with id %d: %s", id, e.getCause()), e);
        } finally {
            JdbcUtils.closeQuietly(ps);
            JdbcUtils.closeQuietly(conn);
        }
    }

    @Override
    public SUser selectUserByLogin(String login) throws DBSystemException {
        Connection conn = null;
        PreparedStatement ps;
        ResultSet rs = null;
        SUser user = null;
        try {
            conn = connectionFactory.newConnection();
            ps = conn.prepareStatement(SELECT_USER_BY_LOGIN);
            ps.setString(1, login);
            rs = ps.executeQuery();
            if (rs.next())
                user = new SUser(rs.getInt(1), rs.getString(2),
                        rs.getString(3), rs.getString(4));
        } catch (SQLException e) {
            JdbcUtils.rollbackQuietly(conn);
            throw new DBSystemException(String.format("Can't select s_user with login '%s': %s", login, e.getCause()), e);
        }
        return user;
    }

    public static void main(String[] args) {

        SUserDaoImplProbe test = new SUserDaoImplProbe(ConnectionFactoryFactory.FactoryType.C3P0);
        try {
            test.insert(new SUser("kivi", "wooroo", "pluh@gorka.com"));
            System.out.println("insertion user 'kivi' in the s_user table is succesfull");
            test.insert(new SUser("jezh", "ivan", "jezhische@gmail.com"));
            System.out.println("insertion user 'kivi' in the s_user table is succesfull");
        } catch (NotUniqueLoginException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getSQLState());
        } catch (NotUniqueEmailException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getSQLState());
        } catch (DBSystemException e) {
            System.out.println(e.getMessage() + ": " + e.getCause());
            System.out.println(e.getSQLState());
        }

        SUser kiviUser = null;
        try {
            kiviUser = test.selectUserByLogin("kivi");
            System.out.println("first selection is succefull");
        } catch (DBSystemException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getSQLState());
        }
        System.out.println("kiviUser = " + kiviUser);
        if (kiviUser != null) {
            try {
                test.deleteById(kiviUser.getId());
                System.out.println("first deletion is succefull");
            } catch (DBSystemException e) {
                System.out.println(e.getMessage());
                System.out.println(e.getSQLState());
            }
        }
        try {
            kiviUser = test.selectUserByLogin("kivi");
            System.out.println("second deletion is succefull");
        } catch (DBSystemException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getSQLState());
        }

        JdbcUtils.avgTime(test,100);
    }
}
