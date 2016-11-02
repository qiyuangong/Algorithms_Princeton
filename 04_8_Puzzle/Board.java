import edu.princeton.cs.algs4.StdRandom;
import java.util.ArrayList;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Board {
    private int[][] tiles;
    private int n;
    
    public Board(int[][] blocks) {
        // construct a board from an n-by-n array of blocks
        // (where blocks[i][j] = block in row i, column j)
        n = blocks.length;
        this.tiles = copy(blocks);
    }
    private int[][] copy(int[][] blocks) {
        int[][] tmpblocks = new int[n][n];
        for (int i = 0; i < blocks.length; i++)
            for (int j = 0; j < blocks.length; j++)
                tmpblocks[i][j] = blocks[i][j];
        return tmpblocks;
    }
    public int dimension() {
        // board dimension n
        return n;
    }
    public int hamming() {
        // number of blocks out of place
        int distance = 0;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0)
                    continue;
                if (tiles[i][j] != (i * n + j + 1))
                    distance++;
            }
        return distance;
    }
    public int manhattan() {
        // sum of Manhattan distances between blocks and goal
        int distance = 0;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0)
                    continue;
                distance += Math.abs((tiles[i][j] - 1) / n - i) + Math.abs((tiles[i][j] - 1) % n - j);
            }
        return distance;
    }
    public boolean isGoal() {
        // is this board the goal board?
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                if (tiles[i][j] != (i * n + j + 1) % (n * n))
                    return false;
        return true;
    }
    public Board twin() {
        // a board that is obtained by exchanging any pair of blocks
        int[][] tmpblocks = copy(tiles);
        // swap a random pair
        int place = 0;
        int swpalce = 1;
        while (true) {
            place = StdRandom.uniform(0, n * n - 1);
            swpalce = (place + 1) % (n * n);
            if (tmpblocks[place / n][place % n] != 0 && tmpblocks[swpalce / n][swpalce % n] != 0)
                break;
        }
        int tmp = tmpblocks[place / n][place % n];
        tmpblocks[place / n][place % n] = tmpblocks[swpalce / n][swpalce % n];
        tmpblocks[swpalce / n][swpalce % n] = tmp;
        return new Board(tmpblocks);
    }
    public boolean equals(Object y) {
        // does this board equal y?
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board other = (Board) y;
        if (other.dimension() != n)
            return false;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                if (other.tiles[i][j] != tiles[i][j])
                    return false;
        return true;
    }
    public Iterable<Board> neighbors() {
        // all neighboring boards
        ArrayList<Board> it = new ArrayList<Board>();
        int x = 0; 
        int y = 0;
        Board tmpboard;
        int[][] tmpblocks = copy(tiles);
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                if (tmpblocks[i][j] == 0) {
                    x = i;
                    y = j;
                    break;
                }
        int[][] check = {
            {0, 1},
            {1, 0},
            {0, -1},
            {-1, 0},
        };
        int nx, ny;
        for (int i = 0; i < 4; i++) {
            nx = x + check[i][0];
            ny = y + check[i][1];
            if ((nx >= n) || (nx < 0) || (ny >= n) || (ny < 0))
                continue;
            // swap blank square with neightbor
            tmpblocks[x][y] = tmpblocks[nx][ny];
            tmpblocks[nx][ny] = 0;
            it.add(new Board(tmpblocks));
            // reset
            tmpblocks[nx][ny] = tmpblocks[x][y];
            tmpblocks[x][y] = 0;
        }
        return it;
    }
    public String toString() {
        // string representation of this board (in the output format specified below)
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", tiles[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }
    public static void main(String[] args) {
        // unit tests (not graded)
        // solve a slider puzzle (given below)
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        StdOut.println(initial.manhattan());
    }
}