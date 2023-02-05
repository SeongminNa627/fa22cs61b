package bstmap;

import java.util.Iterator;
import java.util.*;
import java.util.Set;

public class BSTMap<K extends Comparable, V> implements Map61B, Iterable{
    private class BSTNode{
        K key;
        V value;
        BSTNode left;
        BSTNode right;
        public BSTNode(K key, V value){
            this.key = key;
            this.value = value;
        }
    }
    private int size;
    private BSTNode root;
    public BSTMap(){
    }
    public BSTMap(K key, V value){
        root = new BSTNode(key, value);
        size = 1;
    }

    @Override
    public void clear() {
        size = 0;
        root = null;
    }

    @Override
    public boolean containsKey(Object key) {
        return search(this.root, (K) key)!= null;
    }

    @Override
    public Object get(Object key) {
        BSTNode found = search(this.root, (K) key);
        if (found == null){
            return null;
        }
        return found.value;
    }

    private BSTNode search(BSTNode curr, K k){
        if (curr == null){
            return null;
        }
        if (curr.key.equals(k)){
            return curr;
        }
        int cmp = curr.key.compareTo(k);
        if (cmp > 0){
            return search(curr.left, k);
        }
        return search(curr.right, k);

    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void put(Object key, Object value) {
        root = putHelper(root, key, value);
    }
    private BSTNode putHelper(BSTNode curr, Object key, Object value){
        if (curr == null){
            size ++;
            return new BSTNode((K) key, (V) value);
        }
        int cmp = curr.key.compareTo(key);
        if (cmp > 0){
            // added key is smaller than curr
            curr.left = putHelper(curr.left, key, value);
        }
        else if (cmp <0){
            curr.right = putHelper(curr.right, key, value);
        }
        else if (cmp == 0){
            curr.value = (V) value;
        }
        return curr;
    }

    @Override
    public Set keySet() {
        Set<K> keySet = new TreeSet();
        for (Object o: this){
            keySet.add((K) o);
        }
        return keySet;
    }

    @Override
    public Object remove(Object key) {
        return removeSingleHelper(this.root, key);
    }
    public Object removeSingleHelper(BSTNode root, Object key){
        BSTNode curr = root;
        if (curr == null){
            return null;
        }
        BSTNode parent = null;
        while(curr != null && !curr.key.equals(key)){
            parent = curr;
            int cmp = curr.key.compareTo(key);
            if (cmp > 0) {
                curr = curr.left;
            }
            else{
                curr = curr.right;
            }
        }
        // Case 0: No node was found or empty BST.
        if (curr == null){
            return null;
        }
        // Case 1: Deleting a node with no children
        if (curr.left == null &&  curr.right == null){
            if (parent != null){
                V value;
                if (curr.equals(parent.left)){
                    value = parent.left.value;
                    parent.left = null;
                }
                else{
                    value = parent.right.value;
                    parent.right = null;
                }
                size --;
                return value;
            }
            // Case 1-1: Deleting root with no children
            else{
                V value = this.root.value;
                this.root = null;
                size --;
                return value;
            }
        }
        // Case 2: Deleting a node with two children
        else if (curr.left != null && curr.right != null){
            V value;
            BSTNode successorParent = min(curr);
            // Case 2-1: Deleting a node with right child being the successor
            if (successorParent.equals(curr)){
                value = curr.value;
                BSTNode successor = curr.right;
                successor.left = curr.left;
                this.root = successor;
                size --;
                return value;
            }
            // Case 2-2: Deleting a node with successor somewhere down there
            else {
                value = curr.value;
                K newKey = successorParent.left.key;
                V newVal = successorParent.left.value;
                removeHelper(this.root, successorParent.left.key, successorParent.left.value);
                curr.key = newKey;
                curr.value = newVal;
                size --;
                return value;
            }
        }
        // Case 3: Deleting a node with a single child
        else {
            // Case 3-1: No such node exists
            V value;
            if (curr == null){
                return null;
            }
            value = curr.value;
            BSTNode child;
            if (curr.left != null){
                child = curr.left;
            }
            else{
                child = curr.right;
            }
            if (parent == null){
                this.root = child;
                size --;
                return value;
            }
            else{
                if (curr.equals(parent.left)){
                    parent.left = child;
                    size --;
                    return value;
                }
                else{
                    parent.right = child;
                    size --;
                    return value;
                }
            }
        }
    }

    @Override
    public Object remove(Object key, Object value) {
        return removeHelper(this.root, key, value);
    }
    public Object removeHelper(BSTNode root, Object key, Object value){
        BSTNode curr = root;
        if (curr == null){
            return null;
        }
        BSTNode parent = null;
        while(curr != null && !curr.key.equals(key) && !curr.value.equals(value)){
            parent = curr;
            int cmp = curr.key.compareTo(key);
            if (cmp > 0) {
                curr = curr.left;
            }
            else{
                curr = curr.right;
            }
        }
        if (!curr.value.equals(value)){
            return null;
        }
        // Case 0: No node was found or empty BST.
        if (curr == null){
            return null;
        }
        // Case 1: Deleting a node with no children
        if (curr.left == null &&  curr.right == null){
            if (parent != null){
                if (curr.equals(parent.left)){

                    parent.left = null;
                }
                else{
                    parent.right = null;
                }
                size --;
                return value;
            }
            // Case 1-1: Deleting root with no children
            else{
                this.root = null;
                size --;
                return value;
            }
        }
        // Case 2: Deleting a node with two children
        else if (curr.left != null && curr.right != null){
            BSTNode successorParent = min(curr);
            // Case 2-1: Deleting a node with right child being the successor
            if (successorParent.equals(curr)){
                BSTNode successor = curr.right;
                successor.left = curr.left;
                this.root = successor;
                size --;
                return value;
            }
            else {
                K newKey = successorParent.left.key;
                V newVal = successorParent.left.value;
                removeHelper(this.root, successorParent.left.key, successorParent.left.value);
                curr.key = newKey;
                curr.value = newVal;
                size --;
                return value;
            }
        }
        // Case 3: Deleting a node with a single child
        else {
            // Case 3-1: No such node exists
            if (curr == null){
                return null;
            }
            BSTNode child;
            if (curr.left != null){
                child = curr.left;
            }
            else{
                child = curr.right;
            }
            if (parent == null){
                this.root = child;
                size --;
                return value;
            }
            else{
                if (curr.equals(parent.left)){
                    parent.left = child;
                    size --;
                    return value;
                }
                else{
                    parent.right = child;
                    size --;
                    return value;
                }
            }
        }
    }
    //returns the parent of minimum node bigger than curr
    private BSTNode min(BSTNode curr){
        BSTNode parent = curr;
        if (parent == null){
            return null;
        }
        else if (parent.right.left == null){
            return parent;
        }
        else{
            parent = parent.right;
            while (parent.left.left != null){
                parent = parent.left;
            }
            return parent;
        }
    }

    public void printInorder(){
        inorderHelper(this.root);
    }
    public void inorderHelper(BSTNode curr){
        if(curr == null){
            return;
        }
        inorderHelper(curr.left);
        System.out.print(curr.key + " ");
        inorderHelper(curr.right);
    }


    @Override
    public Iterator<K> iterator() {
        return new BSTMapIterator<K>(this);
    }
    private class BSTMapIterator<K> implements Iterator<K>{
        BSTNode root;
        int size;
        int count;
        ArrayList<K> arrOfKeys;
        public BSTMapIterator(BSTMap map){
            this.root = map.root;
            this.size = map.size;
            this.count = 0;
            BSTNode curr = root;
            arrOfKeys = new ArrayList<>();
            getKeys(curr);

        }
        private void getKeys(BSTNode curr){
            if (curr == null) {
                return;
            }
            getKeys(curr.right);
            getKeys(curr.left);
            arrOfKeys.add((K) curr.key);
        }

        @Override
        public boolean hasNext(){
            return count != size;
        }

        @Override
        public K next(){
            K key = arrOfKeys.get(count);
            count ++;
            return key;
        }
    }

    public static void main(String[] args){
        BSTMap<Integer, String> test = new BSTMap<>();
        test.put(25, "25");
        test.put(20, "20");
        test.put(22, "22");
        test.put(10, "10");
        test.put(12, "12");
        test.put(5, "5");
        test.put(1, "1");
        test.put(8, "8");
        test.put(15, "15");
        test.put(36, "36");
        test.put(30, "30");
        test.put(40, "40");
        test.put(28, "28");
        test.put(38, "38");
        test.put(48, "48");
        test.put(45, "45");
        test.put(50, "50");
        test.remove(40, "32");
        test.printInorder();
    }
}
