package com.jezh.hibernate.golovach.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionFactory {

    Connection newConnection() throws SQLException;
    void close() throws SQLException;
}
