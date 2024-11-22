package com.sahokia.kos.handmadecollections;

public class MyArrayList<T> {
    private Object[] array;
    private int size;
    private static final int DEFAULT_CAPACITY = 10;

    public MyArrayList() {
        array = new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    public void put(T element) {
        if (size == array.length) {
            resize(array.length * 2);
        }
        array[size] = element;
        size++;
    }

    public T get(int index) {
        if (index >= 0 && index < size) {
            return (T) array[index];
        } else {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
    }

    public void delete(int index) {
        if (index >= 0 && index < size) {
            for (int i = index; i < size - 1; i++) {
                array[i] = array[i + 1];
            }
            array[--size] = null;
            if (array.length / 2 > size && array.length / 2 >= DEFAULT_CAPACITY) {
                resize(array.length / 2);
            }
        } else {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
    }

    private void resize(int newCapacity) {
        Object[] newArray = new Object[newCapacity];
        if (size >= 0) {
            System.arraycopy(array, 0, newArray, 0, size);
        }
        array = newArray;
    }
}
