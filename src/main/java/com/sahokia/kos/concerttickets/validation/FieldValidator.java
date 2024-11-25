package com.sahokia.kos.concerttickets.validation;

import com.sahokia.kos.concerttickets.annotations.NullableWarning;

import java.lang.reflect.Field;

public class FieldValidator {
    public static void validateFieldsForNullable(Object object) {
        Class<?> clazz = object.getClass();
        while (clazz != null) {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(NullableWarning.class)) {
                    try {
                        field.setAccessible(true);
                        Object value = field.get(object);
                        NullableWarning annotation = field.getAnnotation(NullableWarning.class);

                        if (value == null) {
                            System.out.printf(annotation.message(), field.getName(), clazz.getSimpleName());
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
            clazz = clazz.getSuperclass();
        }
    }
}
