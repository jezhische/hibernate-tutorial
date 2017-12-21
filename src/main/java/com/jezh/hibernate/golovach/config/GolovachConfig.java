package com.jezh.hibernate.golovach.config;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class GolovachConfig {
    public static final String DRIVER_CLASS_NAME;
    public static final String JDBC_URL;
    public static final String USER;
    public static final String PASSWORD;

    static
    {
        Properties props = new Properties();
        try {
            props.load(new FileReader("src\\main\\resources\\jdbcProps.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        DRIVER_CLASS_NAME = props.getProperty("driver_mysql");
        JDBC_URL = props.getProperty("url_golovach");
        USER = props.getProperty("user_login");
        PASSWORD = props.getProperty("user_password");
    }
}
