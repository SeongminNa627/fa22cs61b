package byow.Core;

import java.util.*;
public class QuadTree<K extends Comparable, V> implements Iterable<K> {
    class Node{
        K key;
        V value;
        Node NW;
        Node NE;
        Node SE;
        Node SW;
        public Node(K key, V value){
            this.key = key;
            this.value = value;
        }
    }
    int size;
    Node root;
    public QuadTree(){
        size = 0;
        root = null;
    }
    public QuadTree(K key, V value){
        size = 1;
        root = new Node(key, value);
    }
    public void insert(K key, V value){
        this.root = insertHelper(key, value, root);
    }
    public Node insertHelper(K key, V value, Node curr){
        if (curr == null) {
            size ++;
            return new Node(key, value);
        }
        int cmp = curr.key.compareTo(key);
        //When the given item is on the QI of curr-> NE
        if (cmp == 1){
            curr.NE = insertHelper(key, value, curr.NE);
        }
        //When the given item is on the QII of curr -> NW
        else if (cmp == 2){
            curr.NW = insertHelper(key, value, curr.NW);
        }
        //When the given item is on the QIII of curr -> SW
        else if (cmp == 3){
            curr.SW = insertHelper(key, value, curr.SW);
        }
        //When the given item is on the QIV of curr -> SE
        else if (cmp == 4){
            curr.SE = insertHelper(key, value, curr.SE);
        }
        else if (cmp == 0){
            curr.key = key;
            curr.value = value;
        }
        return curr;
    }
    public int size(){
        return size;
    }
    public Set<K> keySet(){
        Set<K> keySet = new TreeSet();
        for (K key: this){
            keySet.add(key);
        }
        return keySet;
    }

    @Override
    public Iterator<K> iterator() {
        return new QuadIter(this);
    }

    private class QuadIter implements Iterator<K>{
        QuadTree tree;
        Node root;
        ArrayList<K> keys = new ArrayList<K>();
        int pos;
        public QuadIter(QuadTree tree){
            this.tree = tree;
            this.root = tree.root;
            getKeys(root);
            int pos = 0;
        }
        public void getKeys(Node curr){
            if (curr == null){
                return;
            }
            getKeys(curr.NE);
            getKeys(curr.NW);
            getKeys(curr.SW);
            getKeys(curr.SE);
            keys.add(curr.key);
        }
        @Override
        public boolean hasNext(){
            return pos != this.tree.size;
        }
        @Override
        public K next(){
            K key = keys.get(pos);
            pos++;
            return key;
        }
        @Override
        public void remove(){
        }

    }
}
