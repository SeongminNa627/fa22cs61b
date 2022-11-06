package deque;

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
 * For arrays of length 16 or more,, your usage factor should always be at least 25% the length of the array, you should resize of the array down.
 * For arrays under length 16, your usage factor can be arbitrarily low.
 */

public class ArrayDeque<T> {
    public void addFirst(T item){
    }
    public void addLast(T item){
    }
    public boolean isEmpty(){
        return true;
    }
    public int size(){
        return 0;
    }
    public void printDeque(){
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
    public boolean equals(Object o){
        return true;
    }
    public ArrayDeque(){
    }
}
