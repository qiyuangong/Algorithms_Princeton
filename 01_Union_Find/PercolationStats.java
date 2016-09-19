
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

import static java.lang.Math.sqrt;


public class PercolationStats {
    private double resMean;
    private double resStddev;
    private int t;
    private double interval;

    public PercolationStats(int n, int trials) {
        // perform trials independent experiments on an n-by-n grid
        int i, j, count;
        this.t = trials;
        if (n <= 0 || trials <= 0)
            throw new java.lang.IllegalArgumentException();
        double[] res = new double[trials];
        for (int index = 0; index < trials; index++) {
            Percolation currCase = new Percolation(n);
            count = 0;
            while (!currCase.percolates()) {
                i = StdRandom.uniform(1, n + 1);
                j = StdRandom.uniform(1, n + 1);
                if (!currCase.isOpen(i, j)) {
                    currCase.open(i, j);
                    count++;
                }
            }
            res[index] = count * 1.0 / (n * n);
        }
        this.resMean = StdStats.mean(res);
        this.resStddev = StdStats.stddev(res);
        this.interval = 1.96 * this.resStddev / sqrt(this.t * 1.0);

    }
    public double mean() {
        // sample mean of percolation threshold
        return this.resMean;
    }
    public double stddev() {
        // sample standard deviation of percolation threshold
        return this.resStddev;
    }
    public double confidenceLo() {
        // low  endpoint of 95% confidence interval
        return this.resMean - this.interval;
    }
    public double confidenceHi() {
        // high endpoint of 95% confidence interval
        return this.resMean + this.interval;
    }

    public static void main(String[] args) {
        PercolationStats test = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        System.out.printf("mean                    = %f\n", test.mean());
        System.out.printf("stddev                  = %f\n", test.stddev());
        System.out.printf("95%% confidence interval = %f, %f\n", test.confidenceLo(), test.confidenceHi());
    }
}
