package ngordnet.ngrams;

import java.sql.Time;
import java.util.Collection;
import java.io.*;
import java.util.*;
/** An object that provides utility methods for making queries on the
 *  Google NGrams dataset (or a subset thereof).
 *
 *  An NGramMap stores pertinent data from a "words file" and a "counts
 *  file". It is not a map in the strict sense, but it does provide additional
 *  functionality.
 *
 *  @author Josh Hug
 */
public class NGramMap {
    HashMap<String, TimeSeries> wordMap;
    TimeSeries countTS;
    /** Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME. */
    public NGramMap(String wordsFilename, String countsFilename){
        this.wordMap = new HashMap<>();
        File wordFile = new File(wordsFilename);
        try {
            Scanner wordScanner = new Scanner(wordFile);
            while (wordScanner.hasNext()){
                String[] line = wordScanner.nextLine().split("\t");
                String word = line[0];
                int year = Integer.parseInt(line[1]);
                Double occurrence = Double.parseDouble(line[2]);
                TimeSeries ts = new TimeSeries();
                ts.put(year, occurrence);
                if (!wordMap.containsKey(word)){
                    wordMap.put(word, ts);
                }
                else{
                    wordMap.put(word, ts.plus(wordMap.get(word)));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        countTS = new TimeSeries();
        File countFile = new File(countsFilename);
        try {
            Scanner countScanner = new Scanner(countFile);
            while (countScanner.hasNext()){
                String[] line = countScanner.nextLine().split(",");
                Integer year = Integer.parseInt(line[0]);
                Double volume = Double.parseDouble(line[1]);
                countTS.put(year, volume);
            }
        } catch (FileNotFoundException e){
            System.out.println("No such file exists.");
        }


//        for(Integer i: countMap.keySet()){
//            System.out.print(i + " ");
//            for (Integer year: countMap.get(i).keySet()){
//                System.out.print(countMap.get(i).get(i) + " ");
//            }
//            System.out.println();
//        }

    }

    /** Provides the history of WORD. The returned TimeSeries should be a copy,
     *  not a link to this NGramMap's TimeSeries. In other words, changes made
     *  to the object returned by this function should not also affect the
     *  NGramMap. This is also known as a "defensive copy". */
    public TimeSeries countHistory(String word) {
        TimeSeries thisTS = wordMap.get(word);
        return new TimeSeries(thisTS,thisTS.firstKey(), thisTS.lastKey());
    }

    /** Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     *  returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other words,
     *  changes made to the object returned by this function should not also affect the
     *  NGramMap. This is also known as a "defensive copy". */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        TimeSeries thisTS = wordMap.get(word);
        return new TimeSeries(thisTS,startYear, endYear);
    }

    /** Returns a defensive copy of the total number of words recorded per year in all volumes. */
    public TimeSeries totalCountHistory() {
        return new TimeSeries(countTS, countTS.firstKey(), countTS.lastKey());
    }

    /** Provides a TimeSeries containing the relative frequency per year of WORD compared to
     *  all words recorded in that year. */
    public TimeSeries weightHistory(String word) {
        TimeSeries wordTS = wordMap.get(word);
        TimeSeries divided = wordTS.dividedBy(countTS);
        return divided;
    }

    /** Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     *  and ENDYEAR, inclusive of both ends. */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        TimeSeries wordTS = wordMap.get(word);
        TimeSeries divided = wordTS.dividedBy(countTS);
        return new TimeSeries(divided, startYear, endYear);
    }

    /** Returns the summed relative frequency per year of all words in WORDS. */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        String[] arrOfWords = words.toArray(new String[words.size()]);
        TimeSeries first = wordMap.get(arrOfWords[0]);
        TimeSeries returnTS = new TimeSeries(first, first.firstKey(), first.lastKey());
        for (int i = 0; i < arrOfWords.length - 1; i ++){
            returnTS = returnTS.plus(wordMap.get(arrOfWords[i + 1]));
        }
        return returnTS.dividedBy(countTS);
    }

    /** Provides the summed relative frequency per year of all words in WORDS
     *  between STARTYEAR and ENDYEAR, inclusive of both ends. If a word does not exist in
     *  this time frame, ignore it rather than throwing an exception. */
    public TimeSeries summedWeightHistory(Collection<String> words,
                              int startYear, int endYear) {
        return new TimeSeries(summedWeightHistory(words), startYear, endYear);
    }

}
