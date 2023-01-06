package hw2;

import edu.princeton.cs.algs4.Stopwatch;

public class PercolationRuntimeTest {
    private double[] elapsedTimesQU;
    private double[] elapsedTimesQF;
    private int size;
    private int factor;
    public PercolationRuntimeTest(int N, int factor){
        this.factor = factor;
        elapsedTimesQU = new double[20];
        elapsedTimesQF = new double[20];
        size = N;
        int index = 0;
        for (int i = 1 ; i < N; i = i * this.factor){
            Stopwatch stopwatch = new Stopwatch();
            PercolationStats ps = new PercolationStats(i, 1, new PercolationFactory());
            double time = stopwatch.elapsedTime();
            elapsedTimesQU[index] = time;
            stopwatch = new Stopwatch();
            PercolationStatsQuickFind psqf = new PercolationStatsQuickFind(i, 1, new PercolationFactory());
            time = stopwatch.elapsedTime();
            elapsedTimesQF[index] = time;
            index ++;
        }
    }
    public void display(){
        System.out.println("          i      |      QU     |      QF      ");
        System.out.println("----------------------------------------------");
        int index = 0;
        for (int i = 1; i < size; i = i * factor) {
            System.out.printf("%12s %12.4f %12.4f",i,elapsedTimesQU[index], elapsedTimesQF[index]);
            System.out.println();
            index ++;
        }
    }
    public static void main(String[] args){
        PercolationRuntimeTest test = new PercolationRuntimeTest(1036, 2);
        test.display();
    }
}
