package deque;
import java.util.*;
import java.lang.System.*;

import static java.lang.System.arraycopy;

/**
 * @Author Seongmin Na
 * @Date 11.04.2022
 * @Description:
 * The second double ended queue based on array.
 * Requirements:
 * 1. Any helper method is allowed.
 * 2. add and remove must take constant time, except during resizing operations.
 * 3. The starting size of your array should be 8.
 * 4. The amount of memory that your program uses at any given time must be proportional to the number of items.
 * For example, if you add 100,000 items to the deque, and then remove 9,999 items, you shouldn't still be using an array of length 10,000ish.
 * For arrays of length 16 or more, your usage factor should always be at least 25% the length of the array, you should resize of the array down.
 * For arrays under length 16, your usage factor can be arbitrarily low.
 */

public class ArrayDeque<T> implements Iterable<T>{
    /** Invariants:
     * size == number of items + 1
     */

    private class ArrayDequeIterator<T> implements Iterator<T>{
        private ArrayDeque L;
        public ArrayDequeIterator(ArrayDeque<T> ADeque){
            L = ADeque;
        }
        @Override
        public T next(){
            return null;
        }
        @Override
        public boolean hasNext(){
            return true;
        }
    }
    private T[] items;
    private int size;
    private int capacity;
    private float usageFactor;
    private final float USAGE_FACTOR_CUT = 0.25f;
    private int first;
    private int last;


    public ArrayDeque(){
        items = (T[]) new Object[8];
        capacity = 8;
        size = 0;
        first = 0;
        last = 1;
        usageFactor = (float) size / capacity;
    }


    public void addFirst(T item){
        /**
         * Java.lang.System.arraycopy(src,dest,srcPos, destPos, length)
         * pre      = {null, null, null, null, null, null, null, null}
         *              0     1     2     3     4     5     6     7
         *            first  last
         * existing = {a, null ,null, f  ,e ,d ,c ,b}
         *             0   1     2    3   4  5  6  7
         *                last first
         * size = 6
         * capacity = 8
         * first = 2 (f)
         * last = 1 (a)
         * usageFator = 6/8;
         * resize()?
         */
        if (size + 1 >= capacity){
            resize((int) (capacity * 1.5));
        }
        items[first] = item;
        first = (capacity + first - 1) % capacity;
        size ++;
        usageFactor = (float) (size + 1)/capacity;
    }
    public void addLast(T item){
        if (size + 1 >= capacity){
            resize((int) (capacity * 1.5));
        }
        items[last] = item;
        last = (capacity + last + 1) % capacity;
        size ++;
        usageFactor = (float) (size + 1)/capacity;
    }
    private void resize(int n){
         //1. resize properly.
         //      - decrease the capacity (delete)
         //      - increase the capacity (add)
         T[] newItems = (T[]) new Object[n];
         //      - copy everything over from the older array.
         int pos = 0;
         for (int i = (capacity + first + 1) % capacity ; pos < size; i =  (capacity +  i + 1) % capacity){
             newItems[pos] = items[i];
             pos ++;
         }
         items = newItems;
         // 2. relocate the two pointers: first, last.
         capacity = n;
         first = capacity - 1;
         last = size;

         // 3. adjust the usageFactor
         usageFactor = size / capacity;


    }
    public boolean isEmpty(){
        return size == 0;
    }
    public int size(){
        return size;
    }
    public void printDeque(){
        System.out.println(this);
    }
    public T removeFirst(){
        return null;
    }
    public T removeLast(){
        return null;
    }
    public T get(int index){
        return null;
    }
    @Override
    public boolean equals(Object o){
        return true;
    }
    @Override
    public String toString(){
        return null;
    }
    @Override
    public Iterator<T> iterator(){
        return new ArrayDequeIterator<T>(this);
    }
    public static void main(String[] args){
        ArrayDeque<Integer> test = new ArrayDeque<>();
        for (int i = 0; i < 25; i ++){
            test.addFirst(i);
        }
    }
}
