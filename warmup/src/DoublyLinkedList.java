/**
 * Simple bidirectonal LinkedList.
 * 
 * Without knowing if it is a common practice or not,
 * I decided to have two stored entry points that
 * - both are null if the list is empty
 * - both point to the same item if only one is stored
 * - diverge when size > 1
 *
 * @param <T> type of value in list (yay for generic types!)
 */
public class DoublyLinkedList<T> {

    private Element first, last;

    public DoublyLinkedList() {

    }

    public void append(T data) {

        if (first == last) {
            if (first == null) {
                first = new Element(data); // only special treatment! yay for optimization! (and then I bolloxed it up too much... -_-)
                last = first;
            } else {
                last = new Element(data, null, first);
                first.setNext(last);
            }
            // now it's basically a reordered version of what it was before... with one less check (last == null if first == null should always be the case)
        } else {
           // normal situation
           Element element = new Element(data, null, last);
           last.setNext(element);
           last = element; // then replace last
        }

        /*if (first == null && last == null) {
          first = new Element(data);
          last = first;
        } else if (last == first) {
           last = new Element(data, null, first);
           first.setNext(last);
        } else {
           // normal situation
           Element element = new Element(data, null, last);
           last.setNext(element);
           last = element; // then replace last
        }*/

    }

    public void prepend(T data) { // same schtick

        if (first == last) {
            if (last == null) {
                last = new Element(data);
                first = last;
            } else {
                first = new Element(data);
                last.setPrevious(first);
            }
        } else {
            Element element = new Element(data, first, null);
            first.setPrevious(element);
            first = element;
        }

    }

    Element getFirst() {
        return first;
    }

    Element getLast() {
        return last;
    }

    public void removeFromStart(T data) { // rather ugly, I know, all because of me not used to this + list being doubly linked and this is my first attempt at linked list

        if (first == null) return;

        Element behind = null;
        Element current = first;

        while (current != null) {
            if (current.getData().equals(data)) {

                if (behind == null) {
                    first = first.getNext();
                    if (first != null) first.setPrevious(null);
                    else if (last != null) last = null; // set last to null if shit
                } else if (current.getNext() == null) {
                    behind.setNext(null);
                    if (current.equals(last)) {
                        last = first; // yey
                    }
                } else {
                    behind.setNext(current.getNext());
                    current.getNext().setPrevious(behind); // and done?
                }
            }
            behind = current;
            current = current.getNext();
        }

    }

    @Override
    public String toString() {

        if (first == null) return "<no content in this " + getClass().getName() + ">";

        StringBuilder bldr = new StringBuilder(first.getData().toString());

        Element element = first.getNext();

        while (element != null) {
            bldr.append(System.lineSeparator());
            bldr.append(element.getData().toString());
            element = element.getNext();
        }

        return bldr.toString();

    }

    protected class Element { // no need for a <T> here, T is defined in outer class

        private T data;
        private Element next, previous;

        public Element(T data, Element next, Element previous) {
            this.data = data;
            this.next = next;
                this.previous = previous;
        }

        public Element(T data) {
            this.data = data;
            }

        public T getData() {
            return data;
        }

        public Element getNext() {
            return next;
        }

            public void setNext(Element next) {
            this.next = next;
        }

        public Element getPrevious() {
            return previous;
        }

        public void setPrevious(Element previous) {
            this.previous = previous;
        }
    }
}



class LLTester {
    public static void main(String[] args) {
        DoublyLinkedList<String> list = new DoublyLinkedList<>();
        list.append("Roger");
        list.append("Åse");
        list.append("Grete");
        System.out.println(list);
        System.out.println("--------> removing Åse, appending Ola");
        list.removeFromStart("Åse");
        list.append("Ola");
        System.out.println(list);
        System.out.println("--------> removing Roger (first)");
        list.removeFromStart("Roger");
        System.out.println(list);
        System.out.println("First: " + list.getFirst().getData());
        System.out.println("Last: " + list.getLast().getData());
        System.out.println("--------> removing Ola (last)");
        list.removeFromStart("Ola");
        System.out.println(list);
        System.out.println("First: " + list.getFirst().getData());
        System.out.println("Last: " + list.getLast().getData());
        System.out.println("--------> removing Grete (only one left)");
        list.removeFromStart("Grete");
        System.out.println(list);
        System.out.println("Last: " + (list.getLast() == null? "is null" : list.getLast().getData())); // last MUST BE NULL too...
    }
}