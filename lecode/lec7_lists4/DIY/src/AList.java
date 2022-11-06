/** This is a fill in the blanks version of the SLList class
 *  in case you want to try to figure out how to write it yourself.
 *  After writing your methods, you can run the AListTest file.
 */
public class AList<Item> {
    //         0 1 2 3 4 ...
    //items = [1 2 0 0 0 ... ]
    //size = 2
    //invariants
    /*addLast: The next item we want to add, will go into position size
     *getLast: size - 1;
     *size: the number of items in the list should be size
     */
    private Item[] items;
    private int size;
    /** Creates an empty list. */
    public AList() {
        items = (Item[] )new Object[100];
        size = 0;
    }
    /** 2. resizes the underlying array to the target capacity
     */
    private void resize(int capacity){
//        Item[] a = new Item[capacity];
        // 4. Generic arrays are not allowed in Java, so just trust me for now, let's put Object and cast it to Item[]
        Item[] a = (Item[])new Object[100];
        System.arraycopy(items, 0, a, 0, size);
        items = a;
    }
    /** Inserts X into the back of the list. */
    public void addLast(Item x) {
        // 2. resizing
        if (size == items.length){
//            resize(size + 1); //resize twice would be slow!!! size = 100 to 1000? how many total array memory boxes will we need to create?
            // parabola!
            //3. just adding a few more constant is not helpful
            /*
            Just multiply a constant.
             */
            resize(size * 2); //but, what if we created 1000 and delete 990? waste of memory! --> usage ratio!
            /*
            if R = size/items.length < 0.25 --> half the array size.
             */
        }
        items[size] = x;
        size = size + 1;
    }

    /** Returns the item from the back of the list. */
    public Item getLast() {
        return items[size -1];
    }

    /** Gets the ith item in the list (0 is the front). */
    public Item get(int i) {
        return items[i];
    }

    /** Returns the number of items in the list. */
    public int size() {
        return size;
    }

    /** Deletes item from back of the list and
      * returns deleted item. */
    public Item removeLast() {
        /**
         * what to change and what not?: think about it, {5, 2, 1, -1} --> {5, 2, 1} how do we remove the last one?
         * Actually, we are just going to reduce the size
         */
            Item x = getLast();
            items[size] = 0; //not necessary
        // 4. Let's null out the deleted items for generic ones
        // We save memories by destorying the unneeded ones. Stop loitering
            size --;
        return x;
    }
}
// What happens if exceeds 100?
/**
 * resize()! when size == items.length;
 * int[] a = new int[size + 1];
 * System.arraycopy(...);
 * a[size] = 11;
 * items = a;
 * size += 1;
 *
 * creating a new array and copying everything into the new one is resizing.
 */