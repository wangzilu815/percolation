/* *****************************************************************************
 *  Name:              Zilu Wang
 *  Coursera User ID:  wangzilu815
 *  Last modified:     12/7/2021
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int n;
    private boolean[] openStatus;
    private WeightedQuickUnionUF sites;

    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("n must be greater than 0.");
        else {
            this.n = n;
            sites = new WeightedQuickUnionUF(n * n + 2); // including a virtual top and bottom
            openStatus = new boolean[n * n];
            for (int i = 0; i < n * n; i++)
                openStatus[i] = false; // blocked
        }
    }

    private void validate(int row, int col) {
        if (row < 1 || row > this.n || col < 1 || col > this.n)
            throw new IllegalArgumentException("row and/or col out of bounds.");
    }

    private int xyTo1D(int x, int y) {
        return (x - 1) * this.n + y - 1;
    }

    public void open(int row, int col) {
        validate(row, col);
        openStatus[xyTo1D(row, col)] = true; // open this site
        // link this site to its open neighbors.
        if (row > 1 && this.isOpen(row - 1, col))
            sites.union(xyTo1D(row, col), xyTo1D(row - 1, col));
        if (row < n && this.isOpen(row + 1, col))
            sites.union(xyTo1D(row, col), xyTo1D(row + 1, col));
        if (col > 1 && this.isOpen(row, col - 1))
            sites.union(xyTo1D(row, col), xyTo1D(row, col - 1));
        if (col < n && this.isOpen(row, col + 1))
            sites.union(xyTo1D(row, col), xyTo1D(row, col + 1));
        // link to top and bottom virtual elements
        if (row == 1) sites.union(n * n, xyTo1D(row, col));
        if (row == n) sites.union(n * n + 1, xyTo1D(row, col));
    }

    public boolean isOpen(int row, int col) {
        validate(row, col);
        return (openStatus[xyTo1D(row, col)]);
    }

    public boolean isFull(int row, int col) {
        validate(row, col);
        return this.sites.find(xyTo1D(row, col)) == this.sites.find(n * n);
    }

    public int numberOfOpenSites() {
        int count = 0;
        for (int i = 1; i <= this.n; i++) {
            for (int j = 1; j <= this.n; j++) {
                if (this.isOpen(i, j)) count++;
            }
        }
        return count;
    }

    public boolean percolates() {
        return (sites.find(n * n) == sites
                .find(n * n + 1)); // virtual bottom's coordinate is (n+1, 2)
    }

    /*
    public static void main(String[] args) {
        int n = 10;
        Percolation test = new Percolation(n);
        System.out.println("isopen? " + test.isOpen(1, 1) + "  isFUll?" + test.isFull(1, 1));
        test.open(1, 1);
        System.out.println("isopen? " + test.isOpen(1, 1) + "  isFUll?" + test.isFull(1, 1));
    }
     */
}
