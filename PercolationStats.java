import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static final double CONFIDENCE_95 = 1.96;
    private final int trials;
    private final double[] thresholds;


    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException("n or T must be greater than zero.");
        else {
            this.trials = trials;
            thresholds = new double[trials];
            for (int i = 0; i < trials; i++) {
                Percolation sitesArray = new Percolation(n);
                while (!sitesArray.percolates()) {
                    int randomRow = StdRandom.uniform(1, n + 1);
                    int randomCol = StdRandom.uniform(1, n + 1);
                    sitesArray.open(randomRow, randomCol);
                }
                thresholds[i] = sitesArray.numberOfOpenSites() / Math.pow(n, 2);
            }
        }
    }

    public double mean() {
        return StdStats.mean(thresholds);
    }

    public double stddev() {
        return StdStats.stddev(thresholds);
    }

    public double confidenceLo() {
        return this.mean() - CONFIDENCE_95 * this.stddev() / Math.sqrt(trials);
    }

    public double confidenceHi() {
        return this.mean() + CONFIDENCE_95 * this.stddev() / Math.sqrt(trials);
    }

    public static void main(String[] args) {
        // Stopwatch watch = new Stopwatch();
        PercolationStats stats = new PercolationStats(Integer.parseInt(args[0]),
                                                      Integer.parseInt(args[1]));
        System.out.println("mean = " + stats.mean());
        System.out.println("stddev = " + stats.stddev());
        System.out.println(
                "95% confidence interval = [" + stats.confidenceLo() + ", " + stats.confidenceHi()
                        + "]");
    }
}
