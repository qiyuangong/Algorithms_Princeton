import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int n;
    private Item[] s;
    private int head;
    private int tail;
    private int capacity;

    public RandomizedQueue() {
        // construct an empty randomized queue
        n = 0;
        capacity = 10;
        head = 0;
        tail = 0;
        s = (Item[]) new Object[capacity];
    }
    private void resize(int newCapacity) {
        Item[] copy = (Item[]) new Object[newCapacity];
        for (int i = head; i < tail; i++) {
            copy[i - head] = s[i];
            s[i] = null;
        }
        s = copy;
        tail = tail - head;
        head = 0;
        capacity = newCapacity;
    }
    private void move() {
        // when tail reach end, move elements to head
        for (int i = head; i < tail; i++) {
            s[i - head] = s[i];
            s[i] = null;
        }
        tail = tail - head;
        head = 0;
    }
    public boolean isEmpty() {
        // is the queue empty?
        return n == 0;
    }
    public int size() {
        // return the number of items on the queue
        return n;
    }
    public void enqueue(Item item) {
        if (item == null)
            throw new java.lang.NullPointerException();
        // add the item
        if (tail == capacity && n < capacity)
            move();
        if (n == capacity)
            resize(2 * capacity);
        if (tail - head > 1) {
            // swap to random pos
            int pos = StdRandom.uniform(head, tail);
            // System.out.printf("Pos=%d, n=%d, tail=%d, head=%d\n", pos, n, tail, head);
            s[tail] = s[pos];
            s[pos] = item;
        }
        else
            s[tail] = item;
        tail++;
        n++;
    }
    public Item dequeue() {
        // remove and return a random item
        if (isEmpty())
            throw new java.util.NoSuchElementException();
        Item item = s[head];
        s[head] = null;
        head++;
        n--;
        if (n > 0 && n == capacity / 4) {
            resize(capacity / 2);
        }
        return item;
    }
    public Item sample() {
        // return (but do not remove) a random item
        if (isEmpty())
            throw new java.util.NoSuchElementException();
        int pos = StdRandom.uniform(head, tail);
        return s[pos];
    }
    public Iterator<Item> iterator() {
        // return an independent iterator over items in random order
        return new ListIterator();
    }
    private class ListIterator implements Iterator<Item> {
        private int current;
        private Item[] it;

        public ListIterator() {
            it = (Item[]) new Object[n];
            current = 0;
            for (int i = 0; i < n; i++)
                it[i] = s[i + head];
            StdRandom.shuffle(it, 0, n - 1);
        }

        public boolean hasNext() {
            return current != n;
        }
        public void remove() {
            throw new java.util.NoSuchElementException();
        }
        public Item next() {
            if (current == n)
                throw new java.util.NoSuchElementException();
            return it[current++];
        }
    }   
    public static void main(String[] args) {
        // unit testing
    }
}