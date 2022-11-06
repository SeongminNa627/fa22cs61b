package timingtest;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 */
public class TimeSLList {
    private static void printTimingTable(AList<Integer> Ns, AList<Double> times, AList<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    public static void main(String[] args) {
        timeGetLast();
    }

    public static void timeGetLast(){
        AList<Integer> NsAList = new AList<>();
        AList<Double> timesAList = new AList<>();
        AList<Integer> opsCountAList = new AList<>();

        for (int i = 1000; i <= 128000; i = i * 2){
            SLList<Integer> testSLList = new SLList<>();
            for (int el = 0; el < i; el = el + 1){
                testSLList.addLast(el);
            }
            int M = 10000;
            Stopwatch sw = new Stopwatch();
            for (int j = 0; j < M; j = j + 1){
                testSLList.getLast();
            }
            double tInSec = sw.elapsedTime();
            NsAList.addLast(i);
            opsCountAList.addLast(M);
            timesAList.addLast(tInSec);
        }
        printTimingTable(NsAList, timesAList, opsCountAList);

    }

}
