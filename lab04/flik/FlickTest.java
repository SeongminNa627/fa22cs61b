package flik;
import org.junit.Test;
import static org.junit.Assert.*;
import edu.princeton.cs.algs4.StdRandom;
public class FlickTest {
    @Test
    public void randomTest(){
        for (int i = 0; i < 10000; i ++ ){
            int testing = StdRandom.uniform(1, 10000);
            assertTrue(Flik.isSameNumber(testing, testing));
        }
    }

}
