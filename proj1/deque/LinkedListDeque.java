package deque;

import java.util.*;
/**
 * @author Seongmin Na
 * @Date 11.04.2022
 * Description:
 * This is a very first data structure program. LinkedList that is based on double ended queue.
 * Requirements:
 *
 * 1. add and remove operations must not involve any looping or recursion. A single such operation must take "constant time"
 * i.e. execution time should not depend on the size of the deque. This means that you cannot use loops that go over all/most elements of the deque.
 * 2. get must use iteration, not recursion.
 * 3. size must take constant time.
 * 4. Iterating over the LinkedListDeque using a for-each loop should take time proportional to the number of items.
 * 5. Do not maintain references to items that are no longer in the deque. The amount of memory that your program uses at any given time must be proportional to the number of items.
 * For example, if you add 10,000 items to the deque, and then remove 9,999 items, the resulting memory usage should amount to a deque with 1 item, and not 10,000.
 * Remember that the Java garbage collector will "delete" things for us if and only if there are no pointers to that object.
 */
public class LinkedListDeque<T> implements Iterable<T> {
    private class Node{
        public Node prev;
        public T item;
        public Node next;

        public Node(Node p,T i,Node n){
            prev = p;
            item = i;
            next = n;
        }
    }
    private class LLDequeIterator<T> implements Iterator<T>{
        private LinkedListDeque lst;
        private Node curr;
        private int pos;

        public LLDequeIterator(LinkedListDeque<T> list) {
            lst = list;
            pos = 0;
            curr = lst.sentinel.next;
        }

        @Override
        public boolean hasNext() {
            return pos < lst.size();
        }
        @Override
        public T next() {
            if (hasNext()){
                T item = (T) curr.item;
                curr = curr.next;
                pos ++;
                return item;
            }
            return null;
        }

    }
    private Node sentinel;
    private int size;





    public void addFirst(T item){
        Node newNode = new Node(null, item, null);
        newNode.prev = sentinel;
        newNode.next = sentinel.next;
        sentinel.next.prev = newNode;
        sentinel.next = newNode;
        size = size + 1;
    }
    public void addLast(T item){
        Node newNode = new Node(null, item, null);
        Node last = sentinel.prev;
        last.next = newNode;
        newNode.prev = last;
        newNode.next = sentinel;
        sentinel.prev = newNode;
        size = size + 1;

    }
    public boolean isEmpty(){
        return size == 0;
    }
    public int size(){
        return size;
    }
    public T removeFirst() {
        if (size < 1){
            return null;
        }
        Node first = sentinel.next;
        T item = first.item;
        first.next.prev = sentinel;
        sentinel.next = first.next;
        first = null;
        size = size - 1;
        return item;
    }
    public T removeLast(){
        if (size < 1){
            return null;
        }
        Node last = sentinel.prev;
        T item = last.item;
        Node secondToLast = last.prev;
        secondToLast.next = sentinel;
        sentinel.prev = secondToLast;
        last = null;
        size = size - 1;
        return item;
    }
    public T get(int index){
        if (index >= size || index < 0){
            return null;
        }
        Node p = sentinel.next;
        T item = null;
        while (index >= 0){
            item = p.item;
            p = p.next;
            index --;
        }
        return item;
    }
    public void printDeque(){
        System.out.println(this);
    }
    @Override
    public boolean equals(Object o){
        if (o instanceof LinkedListDeque cmpL) {
            LinkedListDeque thisL = this;
            Node thisNode = this.sentinel.next;
            Node  cmpNode = cmpL.sentinel.next;
            if (thisL.size != cmpL.size) {
                return false;
            }
            for (int i = 0; i < this.size(); i ++ ){
                 if (!thisNode.item.equals(cmpNode.item)){
                     return false;
                 }
                 thisNode = thisNode.next;
                 cmpNode = cmpNode.next;
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
        return new LLDequeIterator(this);
    }
    public T getRecursive(int index){
        return getHelper(index, sentinel.next);
    }
    private T getHelper(int index, Node curr){
        if (index < 0 ){
            return null;
        }
        if (index == 0){
            return curr.item;
        }
        return getHelper(index - 1, curr.next);
    }


    public LinkedListDeque(){
        sentinel = new Node(null, null , null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }
}

