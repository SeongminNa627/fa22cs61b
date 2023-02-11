package ngordnet.main;

import ngordnet.hugbrowsermagic.NgordnetQuery;
import ngordnet.hugbrowsermagic.NgordnetQueryHandler;
import ngordnet.ngrams.NGramMap;
import ngordnet.ngrams.TimeSeries;

import java.util.*;
import java.util.List;


public class HyponymHandler extends NgordnetQueryHandler {
    private WordNet wn;
    private NGramMap ngm;
    public HyponymHandler(WordNet wn, NGramMap ngm){

        this.wn = wn;
        this.ngm = ngm;

    }
    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();
        int k = q.k();

        String[] commonWords = wn.findCommon(words.toArray(new String[words.size()]));
        if (k > 0){
            String[] sortedComWords = sortComWords(commonWords, startYear, endYear);
            if (k >= sortedComWords.length){
                return Arrays.toString(sortedComWords);
            }
            String[] kWords = new String[k];
            System.arraycopy(sortedComWords, 0,kWords, 0, k);
            return Arrays.toString(kWords);
        }
        return Arrays.toString(commonWords);
    }



    public String[] sortComWords(String[] comWords, int startYear, int endYear){
        HashMap<Double, String> freqTable = new HashMap<>();

        for(String word: comWords){
            TimeSeries ts = ngm.countHistory(word, startYear, endYear);
            double totalFreq = 0.0;
            if (ts != null){
                for (int year: ts.keySet()){
                    totalFreq += ts.get(year);
                }
            }
            freqTable.put(totalFreq, word);
        }
        TreeSet<Double> freqSet = new TreeSet<>();
        freqSet.addAll(freqTable.keySet());
        Double[] orderedFreq = freqSet.toArray(new Double[freqSet.size()]);
        String[] orderedComWords = new String[orderedFreq.length];
        for (int i = 0; i < orderedFreq.length; i ++){
            orderedComWords[i] = freqTable.get(orderedFreq[orderedComWords.length - i - 1]);
        }
        return orderedComWords;
    }
}
