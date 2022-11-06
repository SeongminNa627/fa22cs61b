/** An SLList is a list of integers, which hides the terrible truth of the nakedness within. */
//Adding another layer of abstraction
public class SLList {
    // 3. Bring IntNode as a subordinate class.
    // 3. If you protect it from outsider (outside of the class), make it private.
    // 3. You can add "static" if nested class is never going to look out.
    public static class IntNode {
        public int item;
        public IntNode next;

        public IntNode(int i, IntNode n) {
            item = i;
            next = n;
        }
    }
    // 2. (instead of public) Hey, stop touching it, I will make it private. Other classes from using it.
    private IntNode first;
    // 5. new variable
    private int size;
    // 6. Sentinel Node: The first item (if it exists) is at sentinel.next.
    private IntNode sentinel;
    // 2. Less for user of class to understand
    // 2. Safe for you to change private methods
    // 2. Anything public, you are anouncing that it should stay forever, true.
    public SLList(int x){
//        first = new IntNode(x, null);
        //5. Instance variable of size! (Caching)
        size  = 1;
        //6. with sentinel.
        sentinel = new IntNode(63, null);
        sentinel.next = new IntNode(x, null);
    }
    //6. Empty List! It becomes a problem if you try to create an empty list, and use it, and then addLast.
    //6. We cannot go to p.next because p is null. (Refer to the implementation of addLast)
    public SLList SLList(){
        // 6. SOL: Instead of having it null, create an IntNode that holds nothing but prepares to point to the next one.
        // 6. We call this sentinel node.
        sentinel  = new IntNode(213, null);
        size = 0;
    }

    public void addFirst(int x){
        // 6. W/ sentinel, NO SPECIAL CASE!!!!
        sentinel.next = new IntNode(x, sentinel.next);
        size = size + 1;
//        first = new IntNode(x,first);
    }
    public int getFirst(){
//        return first.item;
        return sentinel.next.item;
    }
    //4. addLast & size
    public void addLast(int x){
        size = size + 1;
//        IntNode p = first;
        IntNode p = sentinel;
        /* Move p until it reaches the end of the list.*/
        while (p.next != null){
            p = p.next;
        }
        p.next = new IntNode(x, null);
    }

    public int size(){
        //weird if you try to find it recursively because SLList does not have any pointer.
        //So, we need our helper method.
//        return size(first);
        return size;
    }
    // * Common approach! --> need help from Naked version, private version.
    //Returns the size of the list starting at IntNode p.
//    private int size(IntNode p){
//        if (p.next == null){
//            return 1;
//        }
//        return 1 + size(p.next);
//
//    }
    //5. Let's just have it as an instance variable --> O(1) (called caching).


    public static void main(String[] args){
        // 0. No need to worry about the naked details about specifying null.
        SLList L = new SLList(10);

        // 1. Let's compare with one naked.
        L.addFirst(10);
        L.addFirst(5);
        System.out.println(L.getFirst());

        // 2. Still, you can do L.first.next.next = L.first.next; no good.

    }
}

/**
 * 7. Invariants
 * The sentinel reference always points to a sentinel node
 * The front item (if it exists), is always at sentinel.next.item.
 * The size variable is always the total number of items that have been added.
 * Invariants make it easier to reason about code, and also give you specific goals to strive for in making sure your code works.
 */
