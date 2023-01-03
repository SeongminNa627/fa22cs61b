package deque;

import org.junit.Test;
import static org.junit.Assert.*;
import edu.princeton.cs.algs4.StdRandom;


/** Performs some basic linked list tests. */
public class LinkedListDequeTest {

    @Test
    /** Adds a few things to the list, checking isEmpty() and size() are correct,
     * finally printing the results.
     *
     * && is the "and" operation. */
    public void addIsEmptySizeTest() {
        LinkedListDeque<String> lld1 = new LinkedListDeque<String>();

        assertTrue("A newly initialized LLDeque should be empty", lld1.isEmpty());
        lld1.addFirst("front");

        // The && operator is the same as "and" in Python.
        // It's a binary operator that returns true if both arguments true, and false otherwise.
        assertEquals(1, lld1.size());
        assertFalse("lld1 should now contain 1 item", lld1.isEmpty());

        lld1.addLast("middle");
        assertEquals(2, lld1.size());

        lld1.addLast("back");
        assertEquals(3, lld1.size());

        System.out.println("Printing out deque: ");
        lld1.printDeque();


    }

    @Test
    /** Adds an item, then removes an item, and ensures that dll is empty afterwards. */
    public void addRemoveTest() {


        LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();
        // should be empty
        assertTrue("lld1 should be empty upon initialization", lld1.isEmpty());

        lld1.addFirst(10);
        // should not be empty
        assertFalse("lld1 should contain 1 item", lld1.isEmpty());

        lld1.removeFirst();
        // should be empty
        assertTrue("lld1 should be empty after removal", lld1.isEmpty());

    }

    @Test
    /* Tests removing from an empty deque */
    public void removeEmptyTest() {


        LinkedListDeque<Integer> lld1 = new LinkedListDeque<>();
        lld1.addFirst(3);

        lld1.removeLast();
        lld1.removeFirst();
        lld1.removeLast();
        lld1.removeFirst();

        int size = lld1.size();
        String errorMsg = "  Bad size returned when removing from empty deque.\n";
        errorMsg += "  student size() returned " + size + "\n";
        errorMsg += "  actual size() returned 0\n";

        assertEquals(errorMsg, 0, size);

    }

    @Test
    /* Check if you can create LinkedListDeques with different parameterized types*/
    public void multipleParamTest() {


        LinkedListDeque<String> lld1 = new LinkedListDeque<String>();
        LinkedListDeque<Double> lld2 = new LinkedListDeque<Double>();
        LinkedListDeque<Boolean> lld3 = new LinkedListDeque<Boolean>();

        lld1.addFirst("string");
        lld2.addFirst(3.14159);
        lld3.addFirst(true);

        String s = lld1.removeFirst();
        double d = lld2.removeFirst();
        boolean b = lld3.removeFirst();

    }

    @Test
    /* check if null is return when removing from an empty LinkedListDeque. */
    public void emptyNullReturnTest() {


        LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();

        boolean passed1 = false;
        boolean passed2 = false;
        assertEquals("Should return null when removeFirst is called on an empty Deque,", null, lld1.removeFirst());
        assertEquals("Should return null when removeLast is called on an empty Deque,", null, lld1.removeLast());


    }

    @Test
    /* Add large number of elements to deque; check if order is correct. */
    public void bigLLDequeTest() {


        LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();
        for (int i = 0; i < 1000000; i++) {
            lld1.addLast(i);
        }

        for (double i = 0; i < 500000; i++) {
            assertEquals("Should have the same value", i, (double) lld1.removeFirst(), 0.0);
        }

        for (double i = 999999; i > 500000; i--) {
            assertEquals("Should have the same value", i, (double) lld1.removeLast(), 0.0);
        }


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
        LinkedListDeque<Integer> lst1 = new LinkedListDeque<Integer>();
        LinkedListDeque<Integer> lst2 = new LinkedListDeque<Integer>();

        for (int i = 0; i < testSize; i++) {
            int opNum = StdRandom.uniform(9);
            int val = StdRandom.uniform(testSize);
            if (lst1.size() < 0) {
                opNum = StdRandom.uniform(7);
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
            //getRecursive() test
            else if (opNum == 5) {
                assertEquals(lst1.size(), lst2.size());
                int index = StdRandom.uniform(lst1.size() + 1);
                assertEquals(lst1.getRecursive(index), lst2.getRecursive(index));
            }
            //equals()
            else if (opNum == 6) {
                assertEquals(lst1,lst2);
            }
            //removeFirst()
            else if (opNum == 7) {
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
    @Test
    public void randomizedStringTest() {
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
        LinkedListDeque<String> lst1 = new LinkedListDeque<>();
        LinkedListDeque<String> lst2 = new LinkedListDeque<>();

        for (int i = 0; i < testSize; i++) {
            int opNum = StdRandom.uniform(9);
            double randVal = Math.random();
            String val = String.valueOf(randVal);
            if (lst1.size() < 0) {
                opNum = StdRandom.uniform(7);
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
            //getRecursive() test
            else if (opNum == 5) {
                assertEquals(lst1.size(), lst2.size());
                int index = StdRandom.uniform(lst1.size() + 1);
                assertEquals(lst1.getRecursive(index), lst2.getRecursive(index));
            }
            //equals()
            else if (opNum == 6) {
                assertEquals(lst1,lst2);
            }
            //removeFirst()
            else if (opNum == 7) {
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

