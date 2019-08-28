package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private WeightedQuickUnionUF alg;
    private WeightedQuickUnionUF alg2;
    private int[][] sitesGrid;
    private int numOpenSites;
    private int N;
    private int topSite;
    private int bottomSite;

    /**
     * Create N-by-N grid, with all sites initially blocked.
     * If a site has a positive value, it is blocked.
     * If a site has a negative value, it is open.
     * N*N is the imaginary top site
     * N*N + 1 is the imaginary bottom site
     */
    public Percolation(int N) {
        if (N <= 0) {
            throw new java.lang.IllegalArgumentException();
        } else {
            sitesGrid = new int[N][N];
            alg = new WeightedQuickUnionUF((N * N) + 3);
            alg2 = new WeightedQuickUnionUF((N * N) + 2);
            numOpenSites = 0;
            this.N = N;
            int val = 1;
            topSite = (N * N) + 1;
            bottomSite = (N * N) + 2;
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    this.sitesGrid[i][j] = val;
                    val += 1;
                }
            }
        }
    }

    /**
     * Checks to see if potential checking place is
     * in bounds.
     */
    private boolean isInBounds(int row, int col) {
        if (row < 0 || row > N - 1 || col < 0 || col > N - 1) {
            return false;
        }
        return true;
    }

    /**
     * Helper method for makeConnection.
     */
    private void neighborConnect(String direction, int row, int col) {
        if (direction.equals("left")) {
            if (isOpen(row, col - 1)) {
                alg.union(Math.abs(sitesGrid[row][col]), Math.abs(sitesGrid[row][col - 1]));
                alg2.union(Math.abs(sitesGrid[row][col]), Math.abs(sitesGrid[row][col - 1]));

            }
        } else if (direction.equals("right")) {
            if (isOpen(row, col + 1)) {
                alg.union(Math.abs(sitesGrid[row][col]), Math.abs(sitesGrid[row][col + 1]));
                alg2.union(Math.abs(sitesGrid[row][col]), Math.abs(sitesGrid[row][col + 1]));

            }
        } else if (direction.equals("top")) {
            if (isOpen(row - 1, col)) {
                alg.union(Math.abs(sitesGrid[row][col]), Math.abs(sitesGrid[row - 1][col]));
                alg2.union(Math.abs(sitesGrid[row][col]), Math.abs(sitesGrid[row - 1][col]));

            }
        } else if (direction.equals("bottom")) {
            if (isOpen(row + 1, col)) {
                alg.union(Math.abs(sitesGrid[row][col]), Math.abs(sitesGrid[row + 1][col]));
                if (row != N - 1) {
                    alg2.union(Math.abs(sitesGrid[row][col]), Math.abs(sitesGrid[row + 1][col]));
                }
            }
        }
        return;
    }

    /**
     * Connects the passed in site with the sites around it, if
     * the sites around it are open and in bounds.
     */
    private void makeConnection(int row, int col) {
        if (isInBounds(row, col + 1)) {
            neighborConnect("right", row, col);
        }
        if (isInBounds(row, col - 1)) {
            neighborConnect("left", row, col);
        }
        if (row == N - 1) {
            alg.union(Math.abs(sitesGrid[row][col]), bottomSite);
        } else {
            if (isInBounds(row + 1, col)) {
                neighborConnect("bottom", row, col);
            }
        }
        if (row == 0) {
            alg.union(Math.abs(sitesGrid[row][col]), topSite);
            alg2.union(Math.abs(sitesGrid[row][col]), topSite);
        } else {
            if (isInBounds(row - 1, col)) {
                neighborConnect("top", row, col);
            }
        }
    }

    /**
     * Open the site (row, col) if it is not open already.
     */
    public void open(int row, int col) {
        if (row >= this.N || col >= this.N || col < 0 || row < 0) {
            throw new java.lang.IndexOutOfBoundsException();
        } else {
            if (!isOpen(row, col)) {
                sitesGrid[row][col] *= -1;
                makeConnection(row, col);
                numOpenSites += 1;
            }
        }
    }

    /**
     * Is the site (row, col) open?
     */
    public boolean isOpen(int row, int col) {
        if (row >= this.N || col >= this.N || col < 0 || row < 0) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        return sitesGrid[row][col] < 0;
    }

    /**
     * Is the site (row, col) full?
     */
    public boolean isFull(int row, int col) {
        if (row >= this.N || col >= this.N || col < 0 || row < 0) {
            throw new java.lang.IndexOutOfBoundsException();
        } else {
            return alg2.connected(Math.abs(topSite), Math.abs(sitesGrid[row][col]));
        }
    }

    /**
     * Returns the number of open sites.
     */
    public int numberOfOpenSites() {
        return numOpenSites;
    }

    /**
     * Does the system percolate?
     */
    public boolean percolates() {
        return alg.connected(topSite, bottomSite);
    }

    public static void main(String[] args) { // use for unit testing (not required)

    }

}
