package bstmap;
import static org.junit.Assert.*;
import org.junit.Test;

public class TestBSTDelete {
    @Test
    public void testTwoChildrenDel(){
        BSTMap<Integer, String> test = new BSTMap<>();
        test.put(25, "25");
        test.put(20, "20");
        test.put(10, "10");
        test.put(12, "12");
        test.put(5, "5");
        test.put(1, "1");
        test.put(8, "8");
        test.put(15, "15");
        test.put(36, "36");
        test.put(30, "30");
        test.put(40, "40");
        test.put(28, "28");
        test.put(38, "38");
        test.put(48, "48");
        test.put(45, "45");
        test.put(50, "50");
        test.printInorder();
    }
}
