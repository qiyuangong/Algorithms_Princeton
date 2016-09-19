import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private boolean[][] block;
    private int size;
    private WeightedQuickUnionUF components;

    public Percolation(int n) {
        // create n-by-n grid, with all sites blocked
        if (n <= 0)
            throw new java.lang.IllegalArgumentException();
        this.size = n;
        this.block = new boolean[n + 1][n + 1];
        for (int i = 1; i <= n; i++)
            for (int j = 1; j <= n; j++)
                this.block[i][j] = false;
        // virtual site n + 1
        // from n + 1 -- n + n * n
        this.components = new WeightedQuickUnionUF(n * n + n + 1);
        for (int i = 1; i <= n; i++) {
            // connect virtual site (n * n) with top sites
            this.components.union(0, n + i);
            // connect virtual site (n * n + 1) with bottom sites
            this.components.union(1, n * n + i);
        }

    }
    public void open(int i, int j) {
        // open site (row i, column j) if it is not open already
        if (i <= 0 || j <= 0 || i > this.size || j > this.size)
            throw new java.lang.IndexOutOfBoundsException();
        if (!isOpen(i, j)) {
            int curr = i * this.size + j;
            this.block[i][j] = true;
            if (i - 1 > 0 && isOpen(i - 1, j))
                this.components.union(curr - this.size, curr);
            if (j - 1 > 0 && isOpen(i, j - 1))
                this.components.union(curr - 1, curr);
            if (i + 1 <= this.size && isOpen(i + 1, j))
                this.components.union(curr + this.size, curr);
            if (j + 1 <= this.size && isOpen(i, j + 1))
                this.components.union(curr + 1, curr);
        }
    }
    public boolean isOpen(int i, int j) {
        if (i <= 0 || j <= 0 || i > this.size || j > this.size)
            throw new java.lang.IndexOutOfBoundsException();
        // is site (row i, column j) open?
        return this.block[i][j];
    }
    public boolean isFull(int i, int j) {
        // is site (row i, column j) full?
        if (isOpen(i, j))
            return this.components.connected(i * this.size + j, 0);
        return false;
    }
    public boolean percolates() {
        // does the system percolate?
        for (int i = 1; i <= this.size; i++) {
            if (isOpen(this.size, i))
                break;
        }
        return this.components.connected(1, 0);
    }


    public static void main(String[] args) {
        // write your code here
        System.out.println("Hello");
    }
}
