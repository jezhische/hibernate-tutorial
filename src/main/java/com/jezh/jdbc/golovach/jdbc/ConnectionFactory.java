package com.jezh.jdbc.golovach.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionFactory {

    Connection newConnection() throws SQLException;
    void close() throws SQLException;
}
