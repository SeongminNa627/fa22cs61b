package deque;
import java.util.*;
import static org.junit.Assert.*;
import org.junit.Test;
public class MaxArrayDequeTest {
    private class StringLengthComparator implements Comparator<String>{
        @Override
        public int compare(String s1, String s2){
            return s1.length() - s2.length();
        }
    }
    private class Dog{
        int size;
        String name;
        public Dog(int size, String name){
            this.size = size;
            this.name = name;

        }
    }
    private class DogNameComparator implements Comparator<Dog>{
        @Override
        public int compare(Dog d1, Dog d2){
            return d1.name.length() - d2.name.length();
        }

    }
    @Test
    public void longestStringTest(){
         MaxArrayDeque<String> mad = new MaxArrayDeque<>(new StringLengthComparator());
         mad.addFirst("a");
         mad.addFirst("ab");
         mad.addLast("absc");
         mad.addLast("adkj");
         mad.addFirst("TheLongest");

         assertEquals("Expected longest: ", "TheLongest", mad.max(new StringLengthComparator()));
    }
    @Test
    public void longestDogNameTest(){
        MaxArrayDeque<Dog> dmad = new MaxArrayDeque<>(new DogNameComparator());
        Dog d1 = new Dog(14, "ScoobyDoo");
        Dog d2 = new Dog(23,"Wednesday");

        dmad.addFirst(d1);
        dmad.addLast(d2);


        assertEquals("Expected longest: ", "ScoobyDoo", dmad.max(new DogNameComparator()).name);

    }


}
