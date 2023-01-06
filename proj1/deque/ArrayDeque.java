package deque;
import java.lang.reflect.Array;
import java.util.*;
import java.lang.System.*;

import static java.lang.System.arraycopy;
import static java.lang.System.setOut;

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

public class ArrayDeque<T> implements Iterable<T>, Deque<T>{
    /** Invariants:
     * size == number of items + 1
     */

    private class ArrayDequeIterator<T> implements Iterator<T>{
        private ArrayDeque ad;
        private int front;
        private int curr;
        private int length;
        public ArrayDequeIterator(ArrayDeque<T> ADeque){
            ad = ADeque;
            front = ad.first;
            length = ad.capacity;
            curr = 0;
        }
        @Override
        public T next(){
            T item = (T) ad.get(curr);
            curr ++;
            return item;
        }
        @Override
        public boolean hasNext(){
            return ad.get(curr+1) != null;
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
    public ArrayDeque(int cap){
        items = (T[]) new Object[cap];
        capacity = cap;
        size = 0;
        first = 0;
        last = 1;
        usageFactor = (float) size / capacity;
    }
    private void resize(int n){
        T[] newItems = (T[]) new Object[n];
        int pos = 0;
        for (int i = (capacity + first + 1) % capacity ; pos < size; i =  (capacity +  i + 1) % capacity){
            newItems[pos] = items[i];
            pos ++;
        }
        items = newItems;
        capacity = n;
        first = capacity - 1;
        last = size;
        usageFactor = (float) size / capacity;
    }
    @Override
    public void addFirst(T item){
        if (size + 1 >= capacity){
            resize((int) (capacity * 1.5));
        }
        items[first] = item;
        first = (capacity + first - 1) % capacity;
        size ++;
        usageFactor = (float) size/capacity;
    }
    @Override
    public void addLast(T item){
        if (size + 1 >= capacity){
            resize((int) (capacity * 1.5));
        }
        items[last] = item;
        last = (capacity + last + 1) % capacity;
        size ++;
        usageFactor = (float) size/capacity;
    }
    @Override
    public int size(){return size;}
    @Override
    public T removeFirst(){
        if (size == 0){
            return null;
        }
        float nextUsage = ((float) (size - 1))/ capacity;
        if (size > 16 && nextUsage < USAGE_FACTOR_CUT){
            resize((int) (capacity * 0.5));
        } else if ( 1 < size && size < 16 && nextUsage < 0.13){
            resize((int) (capacity * 0.7));
        }
        T item = items[(first + 1 + capacity) % capacity];
        items[(first + 1 + capacity) % capacity] = null;
        first = (first + 1 + capacity) % capacity;
        size --;
        usageFactor = (float) size / capacity;
        return item;
    }
    @Override
    public T removeLast(){
        if (size == 0){
            return null;
        }
        float nextUsage = (float) (size - 1)/ capacity;
        if (size > 16 && nextUsage < USAGE_FACTOR_CUT){
            resize((int) (capacity * 0.5));
        } else if ( 1 < size && size < 16 && nextUsage < 0.13){
            resize((int) (capacity * 0.7));
        }
        T item = items[(last - 1 + capacity) % capacity];
        items[(last - 1 + capacity) % capacity] = null;
        last = (last - 1 + capacity) % capacity;
        size --;
        usageFactor = (float) size / capacity;
        return item;
    }
    @Override
    public T get(int index){
        return items[(first + index + 1 + capacity) % capacity];
    }
    @Override
    public void printDeque(){
        System.out.println(this);
    }
    @Override
    public boolean equals(Object o){
        if (o instanceof ArrayDeque ad){
            if (ad.size() != this.size()){
                return false;
            }
            for (int i = 0; i < this.size(); i ++){
                if (!ad.get(i).equals(this.get(i))){
                    return false;
                }
            }
            return true;
        }
        return false;
    }
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder("{");
        for (T item: this){
            sb.append(item);
            sb.append(", ");
        }
        sb.delete(sb.length() - 2, sb.length());
        sb.append("}");
        return sb.toString();
    }
    @Override
    public Iterator<T> iterator(){
        return new ArrayDequeIterator<T>(this);
    }
    public static void main(String[] args){
        ArrayDeque<Integer> test = new ArrayDeque<>();
        for (int i = 0; i < 25; i ++) {
            test.addFirst(i);
            test.addLast((int)(i * 1.2));
        }
        System.out.println(test.get(0));
    }
}
