package com.jezh.jdbc.golovach.tx;

import com.jezh.jdbc.golovach.jdbc.ConnectionFactory;
import com.jezh.jdbc.golovach.jdbc.ConnectionFactoryFactory;
import com.jezh.jdbc.golovach.util.JdbcUtils;

import java.sql.Connection;
import java.util.concurrent.Callable;

public class TransactionManagerImpl implements TransactionManager {

    private static ThreadLocal<Connection> connectionHolder = new ThreadLocal<>();

    static {ConnectionFactoryFactory.setType(ConnectionFactoryFactory.FactoryType.C3P0);} // or need constructor

    private static final ConnectionFactory factory = ConnectionFactoryFactory.newConnectionFactory();

    @Override
    public <T> T doInTransaction(Callable<T> unitOfWork) throws Exception { // many calls of database in one transaction
        Connection conn = factory.newConnection();
        // to set thread-safe connection:
        connectionHolder.set(conn); // from this point this connection is bound to the current thread
        try {
            T result = unitOfWork.call(); // here we call the call() method, that define in unitOfWork object; and,
            // for example, in the class SimpleQuizExample we create new anonymous Callable and define call() there.
            // We can have MANY methods inside of one method call(), so call() is COMMAND template, and it is
            // GATEWAY here: many calls of database in one transaction (in one call())
            conn.commit();
            return result;
        } catch (Exception e) {
            JdbcUtils.rollbackQuietly(conn);
            throw e;
        } finally {
            JdbcUtils.closeQuietly(conn);
            connectionHolder.remove();
        }
//        return null; // unreachable statement 'cause there is "throw e" in catch block
    }
    public static Connection getConnection() {
        return connectionHolder.get();
    }
}
