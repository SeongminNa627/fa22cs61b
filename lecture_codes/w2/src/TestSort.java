
import org.junit.Test;
import static org.junit.Assert.*;

public class TestSort {
    /**
     * Test Sort.sort
     *
     *
     * put org.junit.Test
     * erase static
     * erase main
     */

    @Test
    public void testSort(){
        /**
         * Sort the strings
         */
        String[] input = {"they", "changed", "the", "system"};
        String[] expected = {"changed", "system", "the", "they"};


        Sort.sort(input);
        // If our code works, input will equal expected

        assertArrayEquals(expected, input);
    }


    @Test
    public void testSmallest(){
        /**
         * Sort the strings
         */
        String[] input = {"they", "changed", "the", "system"};
        int expected = 1;


        int actual = Sort.smallest(input, 0);
        // If our code works, input will equal expected

        assertEquals(actual, expected);
    }
    @Test
    public void testSwap(){
        String[] input = {"they", "changed", "the", "system"};
        Sort.swap(input, 1, 3);
        String[] expected = {"they","system" , "the", "changed"};
        assertArrayEquals(expected, input);
    }
}
