package ngordnet.main;
import java.util.*;

public class Graph {
    public String youDoSomething(){
        return "Hello!";
    }
    HashMap<Integer, HashSet<Integer>> adjLst;
    int size;
    public Graph(){
        adjLst = new HashMap<>();
        size = 0;
    }
    public void add(int v){
        if (adjLst.containsKey(v)){
            return;
        }
        adjLst.put(v, new HashSet<>());
        size ++;

    }
    public boolean connect(int from, int to){
        if (!adjLst.containsKey(from)){
            return false;
        }
        //from exists
        else {
            // if to does not exist, we create it first
            if (!adjLst.containsKey(to)){
                adjLst.put(to, new HashSet());
                size ++;
            }
            // if they are already connected, we do nothing
            HashSet neighbors = adjLst.get(from);
            if (neighbors.contains(to)){
                return true;
            }
            // if they are not connected, we
            neighbors.add(to);
            adjLst.put(from, neighbors);
            return true;
        }
    }
    public HashSet<Integer> adj(int v){
        return adjLst.get(v);
    }
    public int size(){
        return size;
    }
}
