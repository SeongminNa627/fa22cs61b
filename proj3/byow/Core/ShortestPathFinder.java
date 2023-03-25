package byow.Core;
import java.util.*;
public class ShortestPathFinder{
    RoomQuadTree qdTree;
    Graph<Room> G;
    HashMap<Room, Integer> distTo;
    HashMap<Room, Room> edgeTo;
    HashMap<Room, Boolean> marked;
    PriorityQueue<Node> fringe;
    final int inf = 1000000000;
    final Room source;

    private class Node implements Comparable{
        int knownDist;
        Room room;

        public Node(Room room, int dist){
            this.knownDist = dist;
            this.room = room;
        }
        @Override
        public int compareTo(Object o){
            return this.knownDist - ((Node) o).knownDist;
        }
        /**
         * equals, hashCode? if the fringe doesn't work, this is the culprit.
         */

    }
    public ShortestPathFinder(RoomQuadTree roomQuadTree, Room source){
        this.source = source;
        this.qdTree = roomQuadTree;
        HashSet<HallWay> edges = this.qdTree.getConnection();
        this.G = new Graph();
        this.fringe = new PriorityQueue<Node>();
        for (HallWay hw: edges){
            G.addEdge(hw);
        }
        this.distTo = new HashMap();
        this.edgeTo = new HashMap();
        this.marked = new HashMap();
        this.spt();

    }
    public ArrayDeque<Room> SPto(Room r){
        ArrayDeque<Room> returnDeque = new ArrayDeque<Room>();
        Room curr = r;
        while (curr!= null){
            returnDeque.addFirst(curr);
            curr = edgeTo.get(curr);
        }
        return returnDeque;
    }
    private void changePriority(Node node, int dist){
        this.fringe.remove(node);
        this.fringe.add(new Node(node.room, dist));
    }
    private void spt(){
        distTo.put(this.source, 0);
        fringe.add(new Node(this.source, 0));
        for (Room r: G.vertices()){
            if (!r.equals(source)){
                distTo.put(r, inf);
                fringe.add(new Node(this.source, inf));
            }
            marked.put(r, false);
            edgeTo.put(r, null);
        }
        Node v;
        while (!fringe.isEmpty()){
            v = fringe.poll();
            for (Room r: G.adj(v.room)){
                int potentialDist = 0;
                if (marked.get(r)){
                    // to check if we visited the vertex.
                    potentialDist = distTo.get(v.room) + G.weight(v.room, r);
                }
                if (potentialDist < distTo.get(r)){
                    // to relax the edge.
                    edgeTo.put(r, v.room);
                    changePriority(new Node(r, distTo.get(r)), potentialDist);
                    distTo.put(r, potentialDist);
                }
            }
            marked.put(v.room, true);
        }
    }


}