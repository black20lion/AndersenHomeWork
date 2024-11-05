package com.sahokia.kos.concerttickets.model;

import com.sahokia.kos.concerttickets.annotations.NullableWarning;

import java.lang.reflect.Field;

public abstract class Identifiable {

    @NullableWarning
    private String id;

    protected Identifiable() {
        id = null;
        this.validateFields(this.getClass());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if (id == null) {
            throw new IllegalArgumentException("ID can not be null");
        }
        this.id = id;
    }

    private void validateFields(Class<?> clazz) {
        if (clazz.getSuperclass() != null) {
            validateFields(clazz.getSuperclass());
        }

        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(NullableWarning.class)) {
                try {
                    field.setAccessible(true);
                    Object value = field.get(this);
                    if (value == null) {
                        System.out.printf("Variable [%s] is null in [%s]!%n", field.getName(), clazz.getSimpleName());
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}