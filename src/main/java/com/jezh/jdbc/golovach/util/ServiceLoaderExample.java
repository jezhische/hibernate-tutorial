package com.jezh.jdbc.golovach.util;

import java.sql.Driver;
import java.util.Iterator;
import java.util.ServiceLoader;

public class ServiceLoaderExample {
    public static void main(String[] args) {
        ServiceLoader<Driver> loader = ServiceLoader.load(Driver.class); // Driver - это интерфейс. По этому интерфейсу
        // загрузчик сам находит те классы драйверов, которые в данный момент лежат у меня в либах, и загружает их.
        // Поскольку ServiceLoader<S> implements Iterable<S>, то можно воспаользоваться стандартным for или итератором:

//        for (Driver aLoader : loader) System.out.println(aLoader); // или:
        Iterator<Driver> iterator = loader.iterator();
//        while (iterator.hasNext())
//            System.out.println(iterator.next());
        iterator.forEachRemaining(System.out::println); // (driver -> System.out.println(driver))
    }
}
