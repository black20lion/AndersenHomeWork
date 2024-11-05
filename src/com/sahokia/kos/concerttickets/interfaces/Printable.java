package com.sahokia.kos.concerttickets.interfaces;

public interface Printable {
    default void print() {
        System.out.println("Printing default content");
    }
}
