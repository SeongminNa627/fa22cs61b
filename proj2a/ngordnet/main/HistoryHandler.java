package ngordnet.main;

import ngordnet.hugbrowsermagic.NgordnetQuery;
import ngordnet.hugbrowsermagic.NgordnetQueryHandler;
import ngordnet.ngrams.NGramMap;
import ngordnet.ngrams.TimeSeries;
import ngordnet.plotting.Plotter;
import org.knowm.xchart.XYChart;

import java.util.*;

public class HistoryHandler extends NgordnetQueryHandler {
    NGramMap ngm;
    public HistoryHandler(NGramMap ngm){
        this.ngm = ngm;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();
        ArrayList<TimeSeries> arrOfTS = new ArrayList<>();
        for (int i = 0; i < words.size(); i ++){
            arrOfTS.add(ngm.weightHistory(words.get(i), startYear, endYear));
        }
        XYChart chart = Plotter.generateTimeSeriesChart(words, arrOfTS);
        String encodedImage = Plotter.encodeChartAsString(chart);
        return encodedImage;
    }
}
