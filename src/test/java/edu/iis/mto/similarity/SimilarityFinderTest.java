package edu.iis.mto.similarity;

import edu.iis.mto.search.SearchResult;
import edu.iis.mto.search.SequenceSearcher;
import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class SimilarityFinderTest {

    private SimilarityFinder similarityFinder;
    private SearchResult.Builder builder;

    @Test
    public void jackardSimilarityWithEmptySetsShouldReturnOne() {
        similarityFinder = new SimilarityFinder((key, seq) -> builder.build());
        int[] set1 = {};
        int[] set2 = {};
        Assert.assertThat(1.0d, is(equalTo(similarityFinder.calculateJackardSimilarity(set1, set2))));

    }

    @Test
    public void jackardSimilarityWithSameSetsShouldReturnOne() {
        similarityFinder = new SimilarityFinder(new SequenceSearcher() {

            @Override
            public SearchResult search(int key, int[] seq) {
                if (key == seq[0] || key == seq[1] || key == seq[2]) {
                    builder = SearchResult.builder().withFound(true);
                    return builder.build();
                }
                return builder.withFound(false).build();
            }
        });
        int[] set1 = {1, 3, 5};
        int[] set2 = {1, 3, 5};
        Assert.assertThat(1.0d, is(equalTo(similarityFinder.calculateJackardSimilarity(set1, set2))));
    }
}
