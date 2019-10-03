package com.toberge.data.graph;

public class TopoData extends Metadata {
    private Node next;
    private boolean found = false;

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public boolean isFound() {
        return found;
    }

    public void markAsFound() {
        this.found = true;
    }

    public void setFound(boolean found) {
        this.found = found;
    }

}
