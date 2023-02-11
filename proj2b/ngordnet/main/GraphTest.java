package ngordnet.main;
import org.junit.Test;

import javax.print.DocFlavor;
import java.util.*;
import static org.junit.Assert.*;
public class GraphTest {
    @Test
    public void addMultipleComponentTest(){
        Graph test = new Graph();
        for (int i = 1; i < 9; i ++){
            test.add(i);
        }
        assertEquals(8, test.size());
        for (int i = 1; i < 9; i ++){
            assertTrue(test.adj(i).isEmpty());
        }

    }
    @Test
    public void testConnect(){
        Graph test = new Graph();
        for (int i = 1; i < 9; i ++){
            test.add(i);
        }
        test.connect(8, 1);
        test.connect(8, 6);
        test.connect(8, 4);
        test.connect(1, 2);
        test.connect(1, 3);
        test.connect(6, 7);
        test.connect(6, 5);
        test.connect(7, 5);
        test.connect(4, 5);
        assertEquals(8, test.size());
        assertTrue(test.adj(2).isEmpty());
        assertTrue(test.adj(3).isEmpty());
        assertTrue(test.adj(5).isEmpty());
        assertTrue(test.adj(8).contains(1));
        assertTrue(test.adj(8).contains(6));
        assertTrue(test.adj(8).contains(4));
        assertTrue(!test.adj(8).contains(7));
        assertTrue(test.adj(6).contains(7));
        assertTrue(test.adj(6).contains(5));

    }
}
