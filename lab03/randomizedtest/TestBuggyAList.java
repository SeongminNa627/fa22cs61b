package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
    @Test
    public void testThreeAddThreeRemove(){
        AListNoResizing<Integer> correct = new AListNoResizing<>();
        BuggyAList<Integer> wrong = new BuggyAList<>();

        for (int i = 1; i < 4; i ++){
            correct.addLast(i);
            wrong.addLast(i);
        }
        assertEquals(correct.size(), wrong.size());
        assertEquals(correct.removeLast(), wrong.removeLast());
        assertEquals(correct.size(), wrong.size());
        assertEquals(correct.removeLast(), wrong.removeLast());
        assertEquals(correct.size(), wrong.size());
        assertEquals(correct.removeLast(), wrong.removeLast());
    }
    @Test
    public void randomizedTest(){
        AListNoResizing<Integer> correct = new AListNoResizing<>();
        BuggyAList<Integer> wrong = new BuggyAList<>();

        int N = 10000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber;
            if (correct.size() > 0){
                operationNumber = StdRandom.uniform(0, 4);
            }
            else {
                operationNumber = StdRandom.uniform(0, 2);
            }
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                correct.addLast(randVal);
                wrong.addLast(randVal);

            } else if (operationNumber == 1) {
                // size
                int sizeC = correct.size();
                int sizeW = wrong.size();
                assertEquals(sizeC, sizeW);
            } else if (operationNumber == 2) {
                // getLast
                int lastC = correct.getLast();
                int lastW = wrong.getLast();
                assertEquals(lastC, lastW);
            } else {
                // removeLast
                int removedC = correct.removeLast();
                int removedW = wrong.removeLast();
                assertEquals(removedC, removedW);
            }
        }
    }
}
