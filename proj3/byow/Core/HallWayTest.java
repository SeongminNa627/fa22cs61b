package byow.Core;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;


public class HallWayTest {
    @Test
    public void equalTest(){
        Room r1 = new Room(1,1,1,1,1,1);
        Room r2 = new Room(2,2,1,1,1,1);
        HallWay h1 = new HallWay(r1,r2, 2);
        HallWay h2 = new HallWay(r2,r1, 2);
        assertEquals(true, h1.equals(h2));
    }
    @Test
    public void hashSetContainsTest(){
        Room r1 = new Room(1,1,1,1,1,1);
        Room r2 = new Room(2,2,1,1,1,1);
        HallWay h1 = new HallWay(r1,r2, 2);
        HallWay h2 = new HallWay(r2,r1, 1);

        HashSet<HallWay> HashSet = new HashSet<HallWay>();
        HashSet.add(h1);
        assertEquals(true, HashSet.contains(h2));

    }
}
