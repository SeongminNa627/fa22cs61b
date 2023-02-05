package ngordnet.ngrams;

import java.sql.Time;
import java.util.*;

/** An object for mapping a year number (e.g. 1996) to numerical data. Provides
 *  utility methods useful for data analysis.
 *  @author Josh Hug
 */
public class TimeSeries extends TreeMap<Integer, Double> {
    /** Constructs a new empty TimeSeries. */
    public TimeSeries() {
        super();
    }

    /** Creates a copy of TS, but only between STARTYEAR and ENDYEAR,
     *  inclusive of both end points. */
    public TimeSeries(TimeSeries ts, int startYear, int endYear) {
        super();
        for (int i = startYear; i <= endYear; i ++){
            if (ts.containsKey(i)){
                this.put(i, ts.get(i));
            }
        }
    }

    /** Returns all years for this TimeSeries (in any order). */
    public List<Integer> years() {
        Set<Integer> keys = this.keySet();
        List<Integer> arr;
        arr = List.copyOf(keys);
        return arr;
    }

    /** Returns all data for this TimeSeries (in any order).
     *  Must be in the same order as years(). */
    public List<Double> data() {
        Collection<Double> values = this.values();
        List<Double> arr;
        arr = List.copyOf(values);
        return arr;
    }

    /** Returns the yearwise sum of this TimeSeries with the given TS. In other words, for
     *  each year, sum the data from this TimeSeries with the data from TS. Should return a
     *  new TimeSeries (does not modify this TimeSeries). */
    public TimeSeries plus(TimeSeries ts) {
        TimeSeries returnTs = new TimeSeries(this, this.firstKey(), this.lastKey());
        Set<Integer> tsYears = ts.keySet();
        for (Integer i: tsYears){
            Double data = ts.get(i);
            if (this.containsKey(i)){
                returnTs.put(i, this.get(i) + data);
            }else {
                returnTs.put(i, data);
            }
        }
        return returnTs;
    }

     /** Returns the quotient of the value for each year this TimeSeries divided by the
      *  value for the same year in TS. If TS is missing a year that exists in this TimeSeries,
      *  throw an IllegalArgumentException. If TS has a year that is not in this TimeSeries, ignore it.
      *  Should return a new TimeSeries (does not modify this TimeSeries). */
     public TimeSeries dividedBy(TimeSeries ts) {
         TimeSeries dividedTs = new TimeSeries(this, this.firstKey(), this.lastKey());
         Set<Integer> thisYear = this.keySet();
         for (Integer year: thisYear){
             if (ts.containsKey(year)){
                 dividedTs.put(year, dividedTs.get(year) / ts.get(year));
             }else{
                 throw new IllegalArgumentException();
             }
         }
        return dividedTs;
    }
}
