package com.jezh.hibernate.golovach.z_examples;

import java.util.concurrent.atomic.AtomicInteger;

public class ThreadId {
    // Atomic integer containing the next thread ID to be assigned
    private static final AtomicInteger nextId = new AtomicInteger(0);

    // Thread local variable containing each thread's ID
    private static final ThreadLocal<Integer> threadId =
            new ThreadLocal<Integer>() {
                @Override protected Integer initialValue() {
                    return nextId.getAndIncrement();
                }
            };

    // Returns the current thread's unique ID, assigning it if necessary
    public static int get() {
        return threadId.get();
    }

    public static void main(String[] args) {

        System.out.println(nextId);
        for (int i = 0; i < 5; i++) {
            System.out.println(get());
        }
        System.out.println(nextId);
        System.out.println(nextId.getAndIncrement());
        System.out.println(nextId);
        System.out.println(get());
        System.out.println(nextId);
    }
}
