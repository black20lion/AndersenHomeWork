package com.sahokia.kos.concerttickets.model;

import java.util.UUID;

public abstract class User extends Identifiable {
    User() {
        super.setId(UUID.randomUUID().toString());
    }

    public void printRole() {
        System.out.println(getClass().getSimpleName());
    }

}
