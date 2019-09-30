package com.toberge.data;

public class LinkedList<T> {

    private LinkedElement<T> head = null;
    private int size = 0;

    public LinkedList(T head) {
        this.head = new LinkedElement<>(head);
        size = 1;
    }

    public LinkedList() {}

    public LinkedElement<T> getHead() {
        return head;
    }

    public int size() {
        return size;
    }

    /**
     * Inserting at head
     * @param element
     */
    public void insert(T element) {
        LinkedElement<T> newHead = new LinkedElement<>(element);
        newHead.setNext(head);
        head = newHead;
        size++;
    }

    public boolean contains(T element) {
        LinkedElement<T> current = head;
        while (current != null) { // fixed bug
            if (current.getValue().equals(element)) {
                return true;
            }
            current = current.getNext();
        }
        return false;
    }

    public void remove(T element) {
        LinkedElement<T> previous = null;
        LinkedElement<T> current = head;
        while (current != null) {
            if (current.getValue().equals(element)) {
                if (previous == null) {
                    head = head.getNext(); // current is head
                } else {
                    previous.setNext(current.getNext()); // done
                }
                size--;
                return;
            }
            previous = current;
            current = current.getNext();
        }
    }

    @Override
    public String toString() {
        return head.toString();
    }
}
