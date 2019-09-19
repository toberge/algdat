package com.toberge.data;

public class DirectedNode<T> {
    private T value;
    private DirectedNode<T> next = null;

    public DirectedNode(T value) {
        this.value = value;
    }

    public DirectedNode(T value, DirectedNode<T> next) {
        this.value = value;
        this.next = next;
    }

    public T getValue() {
        return this.value;
    }

    public DirectedNode<T> getNext() {
        return next;
    }

    public void setNext(DirectedNode<T> next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return value + (next == null ? "" : " -> " + next);
    }
}