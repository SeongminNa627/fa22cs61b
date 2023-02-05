package hashmap;

import java.util.*;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V>, Iterable<K> {

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;
        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    // You should probably define some more!
    int size;
    double loadFactor;
    double resizeFactor = 1.5;
    double minLoadFactor = 0.2;

    /** Constructors */
    public MyHashMap() {
        this.buckets = createTable(16);
        this.size = 0;
        this.loadFactor = 0.75;
    }

    public MyHashMap(int initialSize) {
        this.buckets = createTable(initialSize);
        this.size = 0;
        this.loadFactor = 0.75;
    }

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad maximum load factor
     */
    public MyHashMap(int initialSize, double maxLoad) {
        this.buckets = createTable(initialSize);
        this.size = 0;
        this.loadFactor = maxLoad;
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return new Node(key, value);
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new LinkedList<>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     *
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
        return new Collection[tableSize];
    }

    // TODO: Implement the methods of the Map61B Interface below
    // Your code won't compile until you do so!

    @Override
    public void clear() {
        this.buckets = createTable(buckets.length);
        this.size = 0;
    }

    @Override
    public boolean containsKey(K key) {
        for (int i = 0; i < this.buckets.length; i ++){
            Collection<Node> bucket = buckets[i];
            if (buckets[i]!= null){
                for (Node node: bucket){
                    if (key.equals(node.key)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public V get(K key) {
        int hash = key.hashCode();
        int bucketIndex = Math.floorMod(hash, buckets.length);
        Collection<Node> bucket = buckets[bucketIndex];
        if (bucket == null){
            return null;
        }
        for (Node node: bucket){
            if (key.equals(node.key)) {
                return node.value;
            }
        }
        return null;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void put(K key, V value) {
        if (needBiggerSize()){
            resize((int) (resizeFactor * buckets.length));
        }
        int hash = key.hashCode();
        int bucketIndex = Math.floorMod(hash, buckets.length);
        Collection<Node> bucket = this.buckets[bucketIndex];
        if (bucket == null){
            this.buckets[bucketIndex] = createBucket();
            this.buckets[bucketIndex].add(createNode(key, value));
            this.size ++;
        }
        else{
            //there is a bucket for this key
            for (Node node: bucket){
                //if there is a node with the same key -> change the value
                // no need to resize() whatsoever
                if (key.equals(node.key)){
                    node.value = value;
                    return;
                }
            }
            //if there is no node found in the bucket -> create a new node
            // need to resize() if necessary
            bucket.add(createNode(key, value));
            this.size ++;
        }
    }
    private void resize(int size){
        Collection<Node>[] newBuckets = createTable(size);
        for (Collection<Node> bucket: buckets){
            if (bucket != null){
               for (Node node: bucket){
                   int hash = node.key.hashCode();
                   int newBucketIndex = Math.floorMod(hash, newBuckets.length);
                   if (newBuckets[newBucketIndex] == null){
                       newBuckets[newBucketIndex] = createBucket();
                   }
                   newBuckets[newBucketIndex].add(createNode(node.key,node.value));
               }
            }
        }
        this.buckets = newBuckets;
    }
    private boolean needBiggerSize(){
        //returns true if resize is necessary
        return (((double) (this.size + 1)) / buckets.length) > loadFactor;
    }
    private boolean needSmallerSize(){
        if (size < 6){
            return ((double) (this.size - 1) / buckets.length) < 0.2;
        }
        return ((double) (this.size - 1) / buckets.length) < minLoadFactor;
    }

    @Override
    public Set<K> keySet() {
        Set<K> set = new HashSet<K>();
        for (K key: this){
            set.add(key);
        }
        return set;
    }

    @Override
    public V remove(K key) {
        int hashcode = key.hashCode();
        int bucketIndex = Math.floorMod(hashcode, buckets.length);
        Collection<Node> bucket = buckets[bucketIndex];
        if (bucket == null){
            return null ;
        }
        else {
            for (Node node: bucket){
                if (node.key.equals(key)){
                    bucket.remove(node);
                    if (needSmallerSize()){
                        resize((int) (0.5 * buckets.length));
                    }
                    size --;
                    return node.value;
                }
            }
            return null;
        }
    }

    @Override
    public V remove(K key, V value) {
        int hashcode = key.hashCode();
        int bucketIndex = Math.floorMod(hashcode, buckets.length);
        Collection<Node> bucket = buckets[bucketIndex];
        if (bucket == null){
            return null ;
        }
        else {
            for (Node node: bucket){
                if (node.key.equals(key) && node.value.equals(value)){
                    bucket.remove(node);
                    if (needSmallerSize()){
                        resize((int) (0.5 * buckets.length));
                    }
                    size --;
                    return value;
                }
            }
            return null;
        }

    }
    @Override
    public Iterator<K> iterator(){
        return new hashIter(this);
    }
    private class hashIter implements Iterator<K>{
        Collection[] hashTable;
        LinkedList<K> keyList;
        int size;
        int count;
        public hashIter(MyHashMap<K, V> hashTable){
            this.hashTable = hashTable.buckets;
            this.size = hashTable.size();
            this.keyList = new LinkedList<>();
            for (int i = 0; i < this.hashTable.length; i ++){
                Collection<Node> bucket = this.hashTable[i];
                if (bucket!= null){
                    for (Node node: bucket){
                        keyList.add(node.key);
                    }
                }
            }
        }
        @Override
        public boolean hasNext(){
            return count < this.size;
        }
        @Override
        public K next(){
            count ++;
            return keyList.pollFirst();
        }
    }
    public static void main(String[] args){
        MyHashMap<String, String> q = new MyHashMap<>();
        q.put("c", "a");
        q.put("b", "a");
        q.put("a", "a");
        q.put("d", "a");
        q.put("e", "a"); // a b c d e
        for (Object s: q){
            System.out.println(s);
        }
    }
}
