package com.sahokia.kos.handmadecollections;

import java.util.Iterator;
import java.util.LinkedList;

public class MyHashSet<T> implements Iterable<T> {
    private static final int DEFAULT_CAPACITY = 16;
    private static final float LOAD_FACTOR = 0.75f;
    private LinkedList<T>[] buckets;
    private int size;

    public MyHashSet() {
        buckets = new LinkedList[DEFAULT_CAPACITY];
        size = 0;
    }

    private int hash(T element) {
        return (element == null) ? 0 : Math.abs(element.hashCode() % buckets.length);
    }

    public boolean put(T element) {
        int index = hash(element);
        if (buckets[index] == null) {
            buckets[index] = new LinkedList<>();
        }
        if (!buckets[index].contains(element)) {
            buckets[index].add(element);
            size++;
            if (size >= LOAD_FACTOR * buckets.length) {
                resize();
            }
            return true;
        }
        return false;
    }

    public boolean contains(T element) {
        int index = hash(element);
        return buckets[index] != null && buckets[index].contains(element);
    }

    public boolean delete(T element) {
        int index = hash(element);
        if (buckets[index] != null && buckets[index].remove(element)) {
            size--;
            return true;
        }
        return false;
    }


    private void resize() {
        LinkedList<T>[] oldBuckets = buckets;
        buckets = new LinkedList[oldBuckets.length * 2];
        size = 0;

        for (LinkedList<T> bucket : oldBuckets) {
            if (bucket != null) {
                for (T value : bucket) {
                    put(value);
                }
            }
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int bucketIndex = 0;
            private Iterator<T> currentBucketIterator = null;

            @Override
            public boolean hasNext() {
                while (bucketIndex < buckets.length && (buckets[bucketIndex] == null || !buckets[bucketIndex].iterator().hasNext())) {
                    bucketIndex++;
                }
                return bucketIndex < buckets.length;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new java.util.NoSuchElementException();
                }

                if (currentBucketIterator == null || !currentBucketIterator.hasNext()) {
                    currentBucketIterator = buckets[bucketIndex].iterator();
                }

                T nextElement = currentBucketIterator.next();

                if (!currentBucketIterator.hasNext()) {
                    bucketIndex++;
                }
                return nextElement;
            }
        };
    }
}