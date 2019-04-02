package edu.iis.mto.similarity;

import edu.iis.mto.search.SearchResult;
import edu.iis.mto.search.SequenceSearcher;

public class SequenceSercherDubler implements SequenceSearcher {

    private static int callsCounter;

    @Override
    public SearchResult search(int key, int[] seq) {
        callsCounter++;
        SearchResult.Builder search = SearchResult.builder();
        for (int i = 0; i < seq.length; i++) {
            if(seq[i] == key){
                search.withFound(true);
                search.withPosition(i);
                break;
            }
        }
        return search.build();
    }

    public int getCallsCounter() {
        return callsCounter;
    }
}
