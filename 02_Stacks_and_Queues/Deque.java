import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    private int n;
    private Node first;
    private Node last;
    private class Node {
        private Item item;
        private Node next;
        private Node previous;
    }
    public Deque() {
        // construct an empty deque
        first = null;
        last = null;
        n = 0;
    }
    public boolean isEmpty() {
        // is the deque empty?
        return first == null;
    }
    public int size() {
        // return the number of items on the deque
        return n;
    }
    public void addFirst(Item item) {
        // add the item to the front
        if (item == null)
            throw new java.lang.NullPointerException();
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
        if (oldfirst != null)
            oldfirst.previous = first;
        else
            last = first;
        n++;
    }
    public void addLast(Item item) {
        // add the item to the end
        if (item == null)
            throw new java.lang.NullPointerException();
        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        last.previous = oldlast;
        if (oldlast != null)            
            oldlast.next = last;
        else
            first = last;
        n++;
    }
    public Item removeFirst() {
        // remove and return the item from the front
        if (isEmpty())
            throw new java.util.NoSuchElementException();
        Item item = first.item;
        first = first.next;
        if (first != null)
            first.previous = null;
        else
            last = null;
        n--;
        return item;
    }
    public Item removeLast() {
        // remove and return the item from the end
        if (isEmpty())
            throw new java.util.NoSuchElementException();
        Item item = last.item;
        last = last.previous;
        if (last != null)
            last.next = null;
        else
            first = null;
        n--;
        return item;
    }
    public Iterator<Item> iterator() {
        // return an iterator over items in order from front to end
        return new ListIterator();
    }
    private class ListIterator implements Iterator<Item> {
        private Node current = first;
        public boolean hasNext() {
            return current != null;
        }
        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }
        public Item next() {
            if (current == null)
                throw new java.util.NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }
    public static void main(String[] args) {
        // unit testing
    }
}