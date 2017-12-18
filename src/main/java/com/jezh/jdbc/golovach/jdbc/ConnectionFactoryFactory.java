package com.jezh.jdbc.golovach.jdbc;

import com.jezh.jdbc.golovach.jdbc.ConnectionFactory;
import com.jezh.jdbc.golovach.jdbc.impl.ConnectionFactoryC3P0;
import com.jezh.jdbc.golovach.jdbc.impl.ConnectionFactoryDbcp;
import com.jezh.jdbc.golovach.jdbc.impl.ConnectionFactoryJdbc;
import com.jezh.jdbc.golovach.jdbc.impl.ConnectionFactoryProxool;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ConnectionFactoryFactory {
    public static enum FactoryType {RAW, C3P0, DBCP, PROXOOL}

    private static FactoryType currentType = FactoryType.RAW;
    private static List<ConnectionFactory> allFactories = new LinkedList<>();

    public static synchronized void setType(FactoryType type) {
        currentType = type;
    }

    public static synchronized ConnectionFactory newConnectionFactory() {
        ConnectionFactory result;
        try {
            switch (currentType) {
                case RAW:
                    result = new ConnectionFactoryJdbc();
                    break;
                case C3P0:
                    result = new ConnectionFactoryC3P0();
                    break;
                case DBCP:
                    result = new ConnectionFactoryDbcp();
                    break;
                case PROXOOL:
                    result = new ConnectionFactoryProxool();
                    break;
                default:
                    throw new RuntimeException("no proper factory type - no connection here!"); // если свитч не нашел
// нужного ключевого слова, но и исключение, соответственно, не выброшено, то return неинициированный result - это
// будет ошибка компиляции. Поэтому выкидываем здесь исключение, которое не даст дойти до return
            }
        } catch (SQLException e) {
            throw new RuntimeException("cannot find appropriate type", e);
        }
        return result;
    }
}
