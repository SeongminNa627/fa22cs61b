import org.junit.Test;
import java.util.Iterator;
public class ArraySet<T> implements Iterable<T>{
    private T[] items;
    private int size;

    public ArraySet() {
        items = (T[]) new Object[100];
        size = 0;
    }
    public int size(){return size;}
    public boolean contains(T x){
        for (int i = 0; i < size; i ++){
            if (x.equals(items[i])){
                return true;
            }
        }
        return false;
    }
    public void add(T x){
        if (x==null){
            throw new IllegalArgumentException("No null can be added");
        }
        if (contains(x)){
            return;
        }
        items[size] = x;
        size ++;
    }
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder("{");
        for (int i = 0; i < size; i ++){
            sb.append(items[i]);
            sb.append(", ");
        }
        sb.append("}");
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj){
        if (this == obj){return true;}
        if (obj instanceof ArraySet oas){
            if (oas.size != this.size){
                return false;
            }
            for (T x: this){
                if(!oas.contains(x)){
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public Iterator<T> iterator(){
        return new ArraySetIterator();
    }
    private class ArraySetIterator implements Iterator<T>{
        private int pos;
        public ArraySetIterator(){

        }
        @Override
        public boolean hasNext(){
            return pos < size;
        }
        @Override
        public T next(){
            T returnItem = items[pos];
            pos ++;
            return returnItem;
        }
    }
    public static void main(String[] args){
        ArraySet<Integer> test = new ArraySet<>();
        test.add(3);
        test.add(4);
        test.add(null);
    }
}