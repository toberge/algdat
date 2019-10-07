package com.toberge.data.graph;

public class ParentData extends Metadata{

    private Node parent;

    public boolean hasParent() {
        return parent != null;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }
}
