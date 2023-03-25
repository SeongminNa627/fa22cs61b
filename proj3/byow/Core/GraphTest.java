package byow.Core;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;
public class GraphTest {
    Room r1 = new Room(20,20,1,1,1,1);
    Room r2 = new Room(10,20, 1,1,1,1);
    Room r3 = new Room(10,10,1,1,1,1);
    Room r4 = new Room(20,10,1,1,1,1);
    Room r5 = new Room(30, 15, 1,1,1,1);
    HallWay edge12 = new HallWay(r1, r2, 1);
    HallWay edge23 = new HallWay(r2, r3, 1);
    HallWay edge15 = new HallWay(r1, r5, 1);
    HallWay edge45 = new HallWay(r4, r5, 1);
    HallWay edge31 = new HallWay(r3, r1, 1);

    @Test
    public void graphConstructorTest(){
        Graph testG = new Graph(edge12);
        assertEquals(2,testG.V());
        assertEquals(1,testG.E());
    }

    @Test
    public void addEdgeTest(){
        /**
         *
         * Room1076 = r1
         * Room1077 = r2
         * Room1078 = r3
         *
         */
        Graph testG = new Graph(edge12);
        testG.addEdge(edge23);
        assertEquals(3,testG.V());
        assertEquals(2,testG.E());
    }
    @Test
    public void addSameEdgeTest(){
        Graph testG = new Graph(edge12);
        testG.addEdge(edge23);
        HallWay edge21 = new HallWay(r2, r1, 1);
        HallWay edge32 = new HallWay(r3, r2, 2);



        testG.addEdge(edge21);
        testG.addEdge(edge32);

        assertEquals(3,testG.V());
        assertEquals(2,testG.E());
    }
    @Test
    public void edgesTest(){
        HashSet<Edge> setOfEdges = new HashSet<Edge>();
        HallWay edge21 = new HallWay(r2, r1, 1);


        setOfEdges.add(edge23);
        setOfEdges.add(edge21);
        Graph testG = new Graph(edge12);
        testG.addEdge(edge23);

        assertEquals(setOfEdges, testG.edges());


    }
    @Test
    public void verticesTest(){
        HashSet<Room> setOfRooms = new HashSet<Room>();

        setOfRooms.add(r1);
        setOfRooms.add(r2);
        setOfRooms.add(r3);
        Graph testG = new Graph(edge12);
        testG.addEdge(edge23);

        assertEquals(setOfRooms, testG.vertices());
    }


    @Test
    public void adjTest(){
        HashSet<Room> setOfRooms = new HashSet<Room>();
        Graph testG = new Graph(edge12);
        testG.addEdge(edge23);

        setOfRooms.add(r1);
        setOfRooms.add(r3);

        assertEquals(setOfRooms, testG.adj(r2));

    }

    @Test
    public void SPTtest(){
        Graph testG = new Graph(edge12);
        testG.addEdge(edge23);
        HallWay edge34 = new HallWay(r3, r4, 1);
        testG.addEdge(edge34);
        testG.addEdge(edge15);
        testG.addEdge(edge45);

        RoomQuadTree qdTree = new RoomQuadTree(new Random(), 50,50, 3);
        qdTree.insert(r1, null);
        qdTree.insert(r2, null);
        qdTree.insert(r3, null);
        qdTree.insert(r4, null);
        qdTree.insert(r5, null);

        ShortestPathFinder spt = new ShortestPathFinder(qdTree,r3);


        ArrayDeque<Room> expected = new ArrayDeque<Room>();
        expected.add(r3);
        expected.add(r4);
        expected.add(r5);


        ArrayDeque<Room> result = spt.SPto(r5);

        assertEquals(expected.removeFirst(), result.removeFirst());



    }
    @Test
    public void roomHashCodeTest(){
        r1 = new Room(10,10,1,1,1,1);
        r2 = new Room(10,10,23,3,3,21);


        assertEquals(r1.hashCode(), r2.hashCode());
    }
}
