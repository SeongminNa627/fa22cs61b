package ngordnet.ngrams;

import org.junit.Test;
import static org.junit.Assert.*;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** Unit Tests for the TimeSeries class.
 *  @author Josh Hug
 */
public class TestTimeSeries {
    @Test
    public void testFromSpec() {
        TimeSeries catPopulation = new TimeSeries();
        catPopulation.put(1991, 0.0);
        catPopulation.put(1992, 100.0);
        catPopulation.put(1994, 200.0);

        TimeSeries dogPopulation = new TimeSeries();
        dogPopulation.put(1994, 400.0);
        dogPopulation.put(1995, 500.0);

        TimeSeries totalPopulation = catPopulation.plus(dogPopulation);
        // expected: 1991: 0,
        //           1992: 100
        //           1994: 600
        //           1995: 500

        List<Integer> expectedYears = new ArrayList<>
                (Arrays.asList(1991, 1992, 1994, 1995));

        assertEquals(expectedYears, totalPopulation.years());

        List<Double> expectedTotal = new ArrayList<>
                (Arrays.asList(0.0, 100.0, 600.0, 500.0));

        for (int i = 0; i < expectedTotal.size(); i += 1) {
            assertEquals(expectedTotal.get(i), totalPopulation.data().get(i), 1E-10);
        }
    }
    @Test
    public void independentPlus(){
        TimeSeries ts1 = new TimeSeries();
        ts1.put(1994, 1.0);
        ts1.put(1995, 1.2);
        ts1.put(1996, 1.3);
        TimeSeries ts2 = new TimeSeries();
        ts2.put(1997, 1.4);
        ts2.put(1998, 1.5);
        ts2.put(1999, 1.6);
        TimeSeries total = ts1.plus(ts2);
        TimeSeries expected = new TimeSeries();
        expected.put(1994, 1.0);
        expected.put(1995, 1.2);
        expected.put(1996, 1.3);
        expected.put(1997, 1.4);
        expected.put(1998, 1.5);
        expected.put(1999, 1.6);
        List<Double> expectedTotal = expected.data();
        for (int i = 0; i < expectedTotal.size(); i += 1) {
            assertEquals(expectedTotal.get(i), total.data().get(i), 1E-10);
        }
    }
    @Test
    public void testDivide(){
        TimeSeries wordTS = new TimeSeries();
        for (int i = 1992; i < 2001; i = i + 2){
            wordTS.put(i, 10.0 + i);
        }
        TimeSeries countTS = new TimeSeries();
        for (int i = 1990; i < 2002; i ++) {
            countTS.put(i, 120.0 + i);
        }
        wordTS.dividedBy(countTS);
    }
} 