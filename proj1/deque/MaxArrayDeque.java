package deque;
import java.util.*;

public class MaxArrayDeque<T> extends ArrayDeque<T>{
    private Comparator c;
    public MaxArrayDeque(Comparator<T> c){
        super();
        this.c = c;
    }
    public T max(){
        if (this.isEmpty()){
            return null;
        }
        T max = this.get(0);
        for (int i = 1; i < this.size() - 1; i++){
            T next = this.get(i);
            if (c.compare( max,  next) <= 0){
                max = next;
            }
        }
        return max;
    }
    public T max(Comparator<T> c){
        if (this.isEmpty()){
            return null;
        }
        T max = this.get(0);
        for (int i = 1; i < this.size() - 1; i++){
            T next = this.get(i);
            if (c.compare( max,  next) <= 0){
                max = next;
            }
        }
        return max;
    }

}
