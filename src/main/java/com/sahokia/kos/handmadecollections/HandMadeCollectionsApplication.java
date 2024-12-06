package com.sahokia.kos.handmadecollections;

public class HandMadeCollectionsApplication {
    public static void main(String[] args) {
        MyArrayList<String> myArrayList = new MyArrayList<>();
        myArrayList.put("Hello");
        myArrayList.put("World");
        myArrayList.put("Kostya");
        System.out.println(myArrayList.get(2));

        MyHashSet<String> myHashSet = new MyHashSet<>();
        myHashSet.put("banana");
        myHashSet.put("banana");
        myHashSet.put("apple");
        myHashSet.put("orange");
        myHashSet.put("peach");
        myHashSet.put("pear");
        myHashSet.put("watermelon");
        myHashSet.delete("orange");
        for (String string : myHashSet) {
            System.out.println(string);
        }
    }
}
