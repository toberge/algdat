package com.toberge.data;

import java.util.ArrayList;

public class Stack<T> {

    private ArrayList<T> elements;
    private int amount = 0;

    public Stack() {
        elements = new ArrayList<>();
    }

    public boolean isEmpty() {
        return elements.size() == 0;
    }

    /*public boolean isEmtpy() {
        return amount == 0;
    }*/

    /*public boolean isFull() {
        return amount == elements.length;
    }*/

    public void push(T obj) {
        //elements[amount - 1] = obj;
        elements.add(obj);
        amount++;
    }

    public T pop() {
        // return (T)elements[--amount];
        if (!isEmpty()) {
            return elements.remove(--amount);
        } else {
            return null;
        }
    }

}
