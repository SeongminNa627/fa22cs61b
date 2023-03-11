package ngordnet.main;
import com.sun.source.tree.Tree;

import java.util.*;
import java.io.*;

public class WordNet {
    private Graph graph;
    private HashMap<Integer, TreeSet<String>> IDtoSynset;
    /**
     * wrapper for a graph
     */

    public WordNet(String synsetFile, String hyponymsFile){
        // build the graph by parsing the data and put the info into the graph
        // -> add all the edges
        // 1. wordID -> word
        // 11 -> action, 12 -> demotion
        // 2. wordID -> hyponyms
        // 11, 12 (action -> demotion)
        // build the graph combining the two
        graph = new Graph();
        //graph helper functions
        this.IDtoSynset = new HashMap<>();
        File ssFile= new File(synsetFile);
        try {
            Scanner ssScanner = new Scanner(ssFile);
            while (ssScanner.hasNext()){
                String[] line = ssScanner.nextLine().split(",");
                int ssID = Integer.parseInt(line[0]);
                String synset = line[1];
                String[] wordsInSynset = synset.split(" ");
                TreeSet<String> wordsInSS = new TreeSet<>();
                wordsInSS.addAll(Arrays.asList(wordsInSynset));
                IDtoSynset.put(ssID, wordsInSS);
            }
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
        File hyponymFile = new File(hyponymsFile);
        try{
            Scanner hypoScanner = new Scanner(hyponymFile);
            while (hypoScanner.hasNext()){
                String[] line = hypoScanner.nextLine().split(",");
                int srcID = Integer.parseInt(line[0]);
                graph.add(srcID);
                for (int i = 1; i < line.length; i ++){
                    graph.connect(srcID, Integer.parseInt(line[i]));
                }
            }
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }

    }
    public String[] getHyponyms(String word) {
        LinkedList<Integer> src = new LinkedList<>();
        for (int ssID: IDtoSynset.keySet()){
            TreeSet synset = IDtoSynset.get(ssID);
            if (synset.contains(word)){
                src.add(ssID);
            }
        }
        TreeSet<String> hyponyms = new TreeSet<>();
        LinkedList<Integer> fringe = new LinkedList<>();
        while (!src.isEmpty()){
            int sor = src.pollFirst();
            fringe.add(sor);
            while (!fringe.isEmpty()){
                int visit = fringe.pollFirst();
                hyponyms.addAll(IDtoSynset.get(visit));
                Collection<Integer> nextToVisit = graph.adj(visit);
                fringe.addAll(nextToVisit);
            }
        }
        return hyponyms.toArray(new String[hyponyms.size()]);
    }
    public String[] findCommon(String[] words){
        HashMap<String, Integer> freqTable = new HashMap<>();
        for (String word: words){
            for(String hyponym: getHyponyms(word)){
                if (freqTable.containsKey(hyponym)){
                    int frequency = freqTable.get(hyponym);
                    freqTable.put(hyponym,frequency + 1);
                }
                else{
                    freqTable.put(hyponym, 1);
                }
            }
        }
        TreeSet<String> commons = new TreeSet<>();
        for (String word: freqTable.keySet()){
            if (freqTable.get(word) >= words.length){
                commons.add(word);
            }
        }
        return commons.toArray(new String[commons.size()]);
    }
}
