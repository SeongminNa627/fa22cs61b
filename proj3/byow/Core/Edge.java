package byow.Core;

public class Edge<T> {
    T from;
    T to;
    int w;
    public Edge(T o1, T o2, int weight){
        this.from = o1;
        this.to = o2;
        this.w = weight;
    }
    public int weight(){
        return this.w;
    }
    public Object either(){
        return from;
    }
    public Object other(){
        return to;
    }
}
