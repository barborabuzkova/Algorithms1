import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] s;
    private int end = 0; // number of elements
    private int size = 1; // size of array

    // construct an empty randomized queue
    public RandomizedQueue() {
        s = (Item[]) new Object[size]; // capacity???
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return end == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return end;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (end == size) {
            size = size * 2;
            Item[] temp = (Item[]) new Object[size];
            System.arraycopy(s, 0, temp, 0, end);
            s = temp;
        }
        s[end++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int location = StdRandom.uniformInt(end);
        Item value = s[location];
        s[location] = s[end - 1];
        s[end-1] = null;
        end--;
        if (end <= size/2 && end != 0) {
            Item[] temp = (Item[]) new Object[end];
            System.arraycopy(s, 0, temp, 0, end);
            s = temp;
            size = end;
        }
        return value;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return s[StdRandom.uniformInt(end)];
    }

    private class RQIterator implements Iterator<Item> {
        final int copySize;
        int copyEnd;
        Item[] copy;

        RQIterator() {
            copySize = size;
            copyEnd = end;
            copy = (Item[]) new Object[copyEnd];
            System.arraycopy(s, 0, copy, 0, copyEnd);
        }

        public boolean hasNext() {
            return 0 < copyEnd;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            int location = StdRandom.uniformInt(copyEnd);
            Item value = copy[location];
            copy[location] = copy[copyEnd - 1];
            copy[copyEnd - 1] = null;
            copyEnd--;
            return value;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

    }
    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RQIterator();
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> queue = new RandomizedQueue<>();
        assert 0 == queue.size();
        queue.enqueue(290);
        assert 290 == queue.sample();
        assert 290 == queue.sample();
        queue.enqueue(484);
        assert 2 == queue.size();
        System.out.print(queue.dequeue());
    }
}
