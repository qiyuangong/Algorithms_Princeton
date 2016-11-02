import edu.princeton.cs.algs4.MinPQ;
import java.util.ArrayList;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private Move bestSolution;
    private int minMove;

    private class Move implements Comparable<Move> {
        private Move previous;
        private Board board;
        private int moves;

        public Move(Board board) {
            this.board = board;
            moves = 0;
            previous = null;
        }

        public Move(Board board, Move previous) {
            this.board = board;
            this.previous = previous;
            this.moves = previous.moves + 1;
        }

        public int compareTo(Move other) {
            return (this.board.manhattan() - other.board.manhattan()) + (this.moves - other.moves);
        }
    }

    public Solver(Board initial) {
        // find a solution to the initial board (using the A* algorithm)
        if (null == initial)
            throw new java.lang.NullPointerException();
        MinPQ<Move> mpq = new MinPQ<Move>();
        MinPQ<Move> swapmpq = new MinPQ<Move>();
        bestSolution = null;
        minMove = -1;
        if (initial.isGoal()) {
            bestSolution = new Move(initial);
            minMove = 0;
            return;
        }
        mpq.insert(new Move(initial));
        swapmpq.insert(new Move(initial.twin()));
        Move current, swapCurrent;
        boolean flag = true;
        while (!mpq.isEmpty() && !swapmpq.isEmpty() && flag) {
            // System.out.println(mpq.size());
            current = mpq.delMin();
            swapCurrent = swapmpq.delMin();
            for (Board neighbor : current.board.neighbors()) {
                // StdOut.println(neighbor);
                if (current.previous == null || !neighbor.equals(current.previous.board)) {
                    if (neighbor.isGoal()) {
                        minMove = current.moves + 1;
                        bestSolution = new Move(neighbor, current);
                        flag = false;
                        break;
                    }
                    mpq.insert(new Move(neighbor, current));
                }
            }
            if (!flag)
                break;
            for (Board neighbor : swapCurrent.board.neighbors()) {
                if (swapCurrent.previous == null || !neighbor.equals(swapCurrent.previous.board)) {
                    if (neighbor.isGoal()) {
                        flag = false;
                        break;
                    }
                    swapmpq.insert(new Move(neighbor, swapCurrent));
                }
            }
        }
        
    }
    public boolean isSolvable() {
        // is the initial board solvable?
        return minMove != -1; 
    }
    public int moves() {
        // min number of moves to solve initial board; -1 if unsolvable
        return minMove;
    }

    public Iterable<Board> solution() {
        // sequence of boards in a shortest solution; null if unsolvable
        if (bestSolution == null)
            return null;
        ArrayList<Board> it = new ArrayList<Board>();
        Move curr = bestSolution;
        while (curr != null) {
            it.add(0, curr.board);
            curr = curr.previous;
        }
        return it;
    }
    public static void main(String[] args) {
        // solve a slider puzzle (given below)
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
