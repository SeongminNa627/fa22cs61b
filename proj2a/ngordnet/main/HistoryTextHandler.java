package ngordnet.main;

import ngordnet.hugbrowsermagic.NgordnetQueryHandler;
import ngordnet.hugbrowsermagic.NgordnetQuery;
import ngordnet.ngrams.NGramMap;

import java.util.*;
public class HistoryTextHandler extends NgordnetQueryHandler {
    NGramMap ngm;
    public HistoryTextHandler(NGramMap ngm){
        this.ngm = ngm;
    }
    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();

        StringBuilder sb = new StringBuilder();
        for (String word: words){
            sb.append(word + ": " + this.ngm.weightHistory(word, startYear, endYear).toString() + "\n");
        }
        return sb.toString();

    }
}
