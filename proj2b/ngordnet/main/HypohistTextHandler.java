package ngordnet.main;

import ngordnet.hugbrowsermagic.NgordnetQuery;

import ngordnet.ngrams.*;
import ngordnet.plotting.Plotter;
import org.knowm.xchart.XYChart;
import java.util.*;

public class HypohistTextHandler extends HyponymHandler{
    public HypohistTextHandler(WordNet wn, NGramMap ngm){
        super(wn, ngm);
    }
    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();
        int k = q.k();

        String[] commonWords = wn.findCommon(words.toArray(new String[words.size()]));

        if (k == 0 || k < 0){
            k = 4;
        }
        String[] kWords = new String[k];
        String[] sortedComWords = sortComWords(commonWords, startYear, endYear);
        if (k >= sortedComWords.length) {
            kWords = sortedComWords;
        } else {
            System.arraycopy(sortedComWords, 0, kWords, 0, k);
            Arrays.sort(kWords);
        }
        ArrayList<TimeSeries> arrOfTS = new ArrayList<>();
        for (int i = 0; i < kWords.length; i ++){
            arrOfTS.add(ngm.weightHistory(kWords[i], startYear, endYear));
        }
        StringBuilder sb = new StringBuilder(kWords[0]);
        sb.append(": " + arrOfTS.get(0));
        for (int i = 1; i < kWords.length; i ++){
            sb.append("\n" + kWords[i] + ": " + arrOfTS.get(i));
        }
        return sb.toString();
    }
}
