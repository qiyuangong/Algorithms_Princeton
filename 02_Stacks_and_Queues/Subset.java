import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Subset {
    public static void main(String[] args) {
        // unit testing
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        int count = 0;
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            if (count >= k)
                queue.dequeue();
            queue.enqueue(s);
            count++;
        }
        for (String s : queue) {
            StdOut.println(s);
        }
    }
}
