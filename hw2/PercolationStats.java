package hw2;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private int numTimes;
    private double[] items;
    private double openSitesFraction;

    /**
     * Perform T independent experiments on an N-by-N grid.
     */
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new java.lang.IllegalArgumentException();
        } else {
            numTimes = T;
            items = new double[T];
            for (int i = 0; i < T; i++) {
                Percolation perc = pf.make(N);
                while (!perc.percolates()) {
                    perc.open(StdRandom.uniform(N), StdRandom.uniform(N));
                }
                openSitesFraction = (double) perc.numberOfOpenSites() / (N * N);
                items[i] = openSitesFraction;
            }
        }
    }

    /**
     * Sample mean of percolation threshold.
     */
    public double mean() {
        return StdStats.mean(items);
    }

    /**
     * Sample standard deviation of percolation threshold.
     */
    public double stddev() {
        return StdStats.stddev(items);
    }

    /**
     * Low endpoint of 95% confidence interval.
     */
    public double confidenceLow() {
        return this.mean() - ((1.96) * stddev() / Math.sqrt(numTimes));
    }

    /**
     * High endpoint of 95% confidence interval.
     */
    public double confidenceHigh() {
        return this.mean() + ((1.96) * stddev() / Math.sqrt(numTimes));
    }

}
