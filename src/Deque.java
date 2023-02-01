import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private int size = 0;
    private Node first, last;

    private class Node {
        Item item;
        Node next;
        Node previous;
    }

    // construct an empty deque
    public Deque() {

    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        first.previous = null;
        size++;
        if (size == 1) {
            last = first;
        } else {
            oldFirst.previous = first;
        }
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        last.previous = oldLast;
        size++;
        if (size == 1) {
            first = last;
        } else {
            oldLast.next = last;
        }
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item item = first.item;
        first = first.next;
        size--;
        if (size == 0) {
            last = null;
        } else {
            first.previous = null;
        }
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item item = last.item;
        last = last.previous;
//        Node item = first;
//        Node previous = null;
//        while (item.next != null) {
//            previous = item;
//            item = item.next;
//        }
//        Item result = item.item;
//        last = previous;
//        if (last != null) {
//            last.next = null;
//        }
        size--;
        if (size == 0) {
            first = null;
        } else {
            last.next = null;
        }
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new Iterator<Item>() {
            Node next = Deque.this.first;

            public boolean hasNext() {
                return !(next == null);
            }

            public Item next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                Item value = next.item;
                next = next.next;
                return value;
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    // unit testing (required)
    public static void main(String[] args) {

    }

}
