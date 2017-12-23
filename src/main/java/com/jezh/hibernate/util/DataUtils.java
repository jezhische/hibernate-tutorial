package com.jezh.hibernate.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataUtils {
    public static SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
    public static SimpleDateFormat timeFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    public static Date parseDateFromString(String dateString, SimpleDateFormat formatter) throws ParseException {
        return formatter.parse(dateString);
    }

    public static String formatDateToString(Date date, SimpleDateFormat formatter) {
        return (date == null? null : formatter.format(date));
    }
}
