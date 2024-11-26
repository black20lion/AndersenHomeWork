package com.sahokia.kos.jdbchomework.model;

import java.time.LocalDate;

public class User {
    private int id;
    private String name;
    private LocalDate creationDate;

    // Конструкторы, геттеры и сеттеры
    public User(int id, String name, LocalDate creationDate) {
        this.id = id;
        this.name = name;
        this.creationDate = creationDate;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", creationDate=" + creationDate +
                '}';
    }
}
