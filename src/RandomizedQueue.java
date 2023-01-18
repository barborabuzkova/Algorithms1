import edu.princeton.cs.algs4.StdRandom;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] s;
    private int end = 0;
    private int size = 0;

    // construct an empty randomized queue
    public RandomizedQueue() {
        s = (Item[]) new Object[1]; // capacity???
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return end == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (end + 1 == size) {
            s = (Item[]) new Object[size * 2];
        }
        s[end++] = item;
        size++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int location = StdRandom.uniformInt(0, size - 1);
        Item value = s[location];
        s[location] = s[end];
        end--;
        size--;
        return value;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return s[StdRandom.uniformInt(0, size - 1)];
    }

    private class RQIterator implements Iterator<Item> {
        int i = 0;
        Item[] copy;

        RQIterator() {
            copy = (Item[]) new Object[size];
            for (int j = 0; j < size; j++) {
                copy[j] = s[j];
            }
        }

        public boolean hasNext() {
            return i < end;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            int location = StdRandom.uniformInt(0, size - 1);
            Item value = copy[location];
            copy[location] = copy[end--];
            i++;
            size--;
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

    }

}
