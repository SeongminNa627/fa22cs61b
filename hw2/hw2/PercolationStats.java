package hw2;
import java.util.*;
import edu.princeton.cs.algs4.*;
public class PercolationStats {
    private int N;
    private int T;
    private double sumOfFraction;
    private double sumOfSquaredFraction;
    private double[] percSamples;

    public PercolationStats(int N, int T, PercolationFactory pf){
        if (N <= 0 || T <= 0){
            throw new IllegalArgumentException();
        }
        percSamples = new double[T];
        for (int i = 0; i < T; i ++){
            Percolation p = pf.make(N);
            while (!p.percolates()){
                p.open(StdRandom.uniform(N), StdRandom.uniform(N));
            }
            percSamples[i] = ((double) p.numberOfOpenSites())/(N*N);
        }

    }
    public double mean(){
        return StdStats.mean(percSamples);
    }
    public double stddev(){
        return StdStats.stddev(percSamples);
    }
    public double confidenceLow(){
        return this.mean() - (1.96 * this.stddev())/T;
    }
    public double confidenceHigh(){
        return this.mean() + (1.96 * this.stddev())/T;
    }
}
