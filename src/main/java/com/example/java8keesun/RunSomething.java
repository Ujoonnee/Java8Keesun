package com.example.java8keesun;

@FunctionalInterface
public interface RunSomething {

    void doIt(String a);

    static void print() {
        System.out.println("print");
    }

    default void printNum() {
        System.out.println("print 1");
    }
}
