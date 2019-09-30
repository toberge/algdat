package com.toberge.data;

public class LinkedElement<T> {
    private T value;
    private LinkedElement<T> next = null;

    public LinkedElement(T value) {
        this.value = value;
    }

    public LinkedElement(T value, LinkedElement<T> next) {
        this.value = value;
        this.next = next;
    }

    public T getValue() {
        return this.value;
    }

    public LinkedElement<T> getNext() {
        return next;
    }

    public void setNext(LinkedElement<T> next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return value + (next == null ? "" : " -> " + next);
    }
}