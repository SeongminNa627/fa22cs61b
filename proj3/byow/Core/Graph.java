package byow.Core;
import java.util.*;

public class Graph<V>{
    HashMap<V, HashSet<Edge>> adjLst;
    int v;
    int e;
    public Graph(){
        this.adjLst = new HashMap<V, HashSet<Edge>>();
        v = 0;
        e = 0;
    }
    public Graph(Edge e){
        this.adjLst = new HashMap<V, HashSet<Edge>>();
        this.v = 0;
        this.e = 0;
        addEdge(e);
    }
    public void addEdge(Edge e){
        Object v = e.either();
        Object w = e.other();

        HashSet<Edge> vSetOfEdges;
        HashSet<Edge> wSetOfEdges;

        if(!adjLst.containsKey(v)){
            vSetOfEdges = new HashSet<Edge>();
            this.v ++;
        }
        else{
            vSetOfEdges = adjLst.get(v);
        }
        if(!adjLst.containsKey(w)){
            wSetOfEdges = new HashSet<Edge>();
            this.v ++;
        }
        else{
            wSetOfEdges = adjLst.get(w);
        }

        if(!vSetOfEdges.contains(e)){
            vSetOfEdges.add(e);
            wSetOfEdges.add(e);
            adjLst.put((V)v, vSetOfEdges);
            adjLst.put((V)w, wSetOfEdges);
            this.e ++;

        }
    }
    public int V(){
        return v;
    }
    public int E(){
        return e;
    }
    public Iterable<V> vertices(){
        Set<V> vertices = adjLst.keySet();
        HashSet<V> returnSet = new HashSet<V>();
        for (V vertex: vertices){
            for (Edge e: adjLst.get(vertex)){
                returnSet.add((V) e.either());
                returnSet.add((V) e.other());
            }
        }
        return returnSet;
    }

    public Iterable<V> adj(V v){
        Set<Edge> adj = adjLst.get(v);
        HashSet<V> returnSet = new HashSet<V>();
        for (Edge e: adj){
            if (e.either().equals(v)){
                returnSet.add((V)e.other());
            }
            else {
                returnSet.add((V)e.either());
            }
        }
        return returnSet;
    }
    public Iterable<Edge> edges(){
        Set<V> vertices = adjLst.keySet();
        HashSet<Edge> returnSet = new HashSet<Edge>();
        for (V vertex: vertices){
            for (Edge e: adjLst.get(vertex)){
                returnSet.add(e);
            }
        }
        return returnSet;
    }

    public int weight(V v, V w){
        for (Edge e: adjLst.get(v)){
            if (e.either().equals(w)){
                return e.weight();
            }
            if (e.other().equals(w)){
                return e.weight();
            }
        }
        return 0;
    }

}
