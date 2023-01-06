package deque;
import static org.junit.Assert.*;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import java.util.*;

public class ArrayDequeTest{
    @Test
    public void threeAddRemoveTest(){
        ArrayDeque<Integer> test = new ArrayDeque<>();
        Random rand = new Random();
        Integer val1 = rand.nextInt(100);
        test.addFirst(val1);
        Integer val2 = rand.nextInt(100);
        test.addFirst(val2);
        Integer val3 = rand.nextInt(100);
        test.addFirst(val3);

        assertEquals("Removing last item - should get " + val1, val1, (Integer) test.removeLast());
        assertEquals("Removing last item - should get " + val2, val2, (Integer) test.removeLast());
        assertEquals("Removing last item - should get " + val3, val3, (Integer) test.removeLast());

        assertEquals(0, test.size());
    }

    @Test
    public void isEmptyTest(){
        ArrayDeque<Integer> test = new ArrayDeque<>();
        Random rand = new Random();
        Integer val1 = rand.nextInt(100);
        test.addFirst(val1);
        Integer val2 = rand.nextInt(100);
        test.addFirst(val2);
        Integer val3 = rand.nextInt(100);
        test.addFirst(val3);
        for (int i = 0; i < 2; i ++ ) {
            test.removeLast();
        }
        assertEquals("One item is remained", false, test.isEmpty());
        test.removeLast();
        assertEquals("Empty array, there should be no element left", true, test.isEmpty());
    }

    @Test
    public void sizeTest(){
        ArrayDeque<Integer> test = new ArrayDeque<>();
        Random rand = new Random();
        for (int i = 0; i < 100; i ++){
            Integer val = rand.nextInt(100);
            test.addLast(val);
        }
        int numOfRemove = rand.nextInt(100);
        for (int j = 0; j < numOfRemove; j ++) {
            test.removeFirst();
        }
        int currSize = 100 - numOfRemove;
        assertEquals("There should be " + currSize + " elements in test1", currSize, test.size());
    }
    @Test
    public void equalTest(){
        ArrayDeque<Integer> test1 = new ArrayDeque<>();
        ArrayDeque<Integer> test2 = new ArrayDeque<>();

        Random rand = new Random();
        for (int i = 0; i < 100; i ++){
            Integer val = rand.nextInt(100);
            test1.addLast(val);
            test1.addLast(val);
        }
        int numOfRemove = rand.nextInt(100);
        for (int j = 0; j < numOfRemove; j ++) {
            test1.removeFirst();
            test1.removeFirst();
        }
        assertEquals("Both ArrayDeque are equal to each other.", test1, test2);
    }
    @Test
    /* Check if you can create LinkedListDeques with different parameterized types*/
    public void multipleParamTest() {


        ArrayDeque<String> lld1 = new ArrayDeque<String>();
        ArrayDeque<Double> lld2 = new ArrayDeque<Double>();
        ArrayDeque<Boolean> lld3 = new ArrayDeque<Boolean>();

        lld1.addFirst("string");
        lld2.addFirst(3.14159);
        lld3.addFirst(true);

        String s = lld1.removeFirst();
        double d = lld2.removeFirst();
        boolean b = lld3.removeFirst();
    }
    @Test
    public void emptyNullReturnTest() {
        ArrayDeque<Integer> ad1 = new ArrayDeque<Integer>();

        boolean passed1 = false;
        boolean passed2 = false;
        assertEquals("Should return null when removeFirst is called on an empty Deque,", null, ad1.removeFirst());
        assertEquals("Should return null when removeLast is called on an empty Deque,", null, ad1.removeLast());


    }
    @Test
    public void randomizedIntegerTest() {
        /* Testing Methods:
            0. addFirst
            1. addLast
            2. size
            3. isEmpty
            4. get
            5. getRecursive
            6. equals()
            7. removeFirst --> Can't be called when size == 0
            8. removeLast --> Can't be called when size == 0
         */
        int testSize = 100000;
        ArrayDeque<Integer> lst1 = new ArrayDeque<Integer>();
        ArrayDeque<Integer> lst2 = new ArrayDeque<Integer>();

        for (int i = 0; i < testSize; i++) {
            int opNum = StdRandom.uniform(8);
            int val = StdRandom.uniform(testSize);
            if (lst1.size() < 0) {
                opNum = StdRandom.uniform(6);
            }
            //addFirst() test
            if (opNum == 0) {
                lst1.addFirst(val);
                lst2.addFirst(val);
            }
            //addLast() test
            else if (opNum == 1) {
                lst1.addLast(val);
                lst2.addLast(val);
            }
            //size() test
            else if (opNum == 2) {
                assertEquals(lst1.size(), lst2.size());
            }
            //isEmpty() test
            else if (opNum == 3) {
                assertEquals(lst1.isEmpty(), lst2.isEmpty());
            }
            //get() test
            else if (opNum == 4) {
                assertEquals(lst1.size(), lst2.size());
                int index = StdRandom.uniform(lst1.size() + 1);
                assertEquals(lst1.get(index), lst2.get(index));
            }
            //equals()
            else if (opNum == 5) {
                assertEquals(lst1,lst2);
            }
            //removeFirst()
            else if (opNum == 6) {
                assertEquals(lst1.removeFirst(), lst2.removeFirst());
                assertEquals(lst1.size(), lst2.size());
            }
            //removeLast()
            else {
                assertEquals(lst1.removeLast(), lst2.removeLast());
                assertEquals(lst1.size(), lst2.size());
            }


        }
    }

}