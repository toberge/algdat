package com.toberge.data;

public class Queue<T> {
    private T[] array;
    private int start = 0, end = 0, count = 0;

    public Queue(int size) {
        if (size < 1) throw new IllegalArgumentException("Size must be > 0");
        array = (T[]) new Object[size];
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public boolean isFull() {
        return count == array.length;
    }

    public void put(T element) {
        if (isFull()) return;
        array[end] = element;
        end = (end+1) % array.length;
        count++;
    }

    public T getNext() {
        if (!isEmpty()) {
            T element = array[start];
            start = (start+1) % array.length;
            count--;
            return element;
        } else {
            return null;
        }
    }

    public T checkNext() {
        if (!isEmpty()) {
            return array[start];
        } else {
            return null;
        }
    }
}
