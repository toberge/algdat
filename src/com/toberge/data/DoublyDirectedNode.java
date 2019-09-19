package com.toberge.data;

public class DoublyDirectedNode<T> {
    private T value;
    private DoublyDirectedNode<T> next = null;
    private DoublyDirectedNode<T> previous = null;

    public DoublyDirectedNode(T value) {
        this.value = value;
    }

    public DoublyDirectedNode(T value, DoublyDirectedNode<T> next) {
        this.value = value;
        this.next = next;
    }

    public T getValue() {
        return this.value;
    }

    public DoublyDirectedNode<T> getNext() {
        return next;
    }

    public void setNext(DoublyDirectedNode<T> next) {
        this.next = next;
    }

    public DoublyDirectedNode<T> getPrevious() {
        return previous;
    }

    public void setPrevious(DoublyDirectedNode<T> previous) {
        this.previous = previous;
    }
}
