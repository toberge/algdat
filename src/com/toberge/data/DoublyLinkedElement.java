package com.toberge.data;

public class DoublyLinkedElement<T> {
    private T value;
    private DoublyLinkedElement<T> next = null;
    private DoublyLinkedElement<T> previous = null;

    public DoublyLinkedElement(T value) {
        this.value = value;
    }

    public DoublyLinkedElement(T value, DoublyLinkedElement<T> next) {
        this.value = value;
        this.next = next;
    }

    public T getValue() {
        return this.value;
    }

    public DoublyLinkedElement<T> getNext() {
        return next;
    }

    public void setNext(DoublyLinkedElement<T> next) {
        this.next = next;
    }

    public DoublyLinkedElement<T> getPrevious() {
        return previous;
    }

    public void setPrevious(DoublyLinkedElement<T> previous) {
        this.previous = previous;
    }
}
