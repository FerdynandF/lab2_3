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
                    return SearchResult.builder().withFound(true).build();
                }
                return SearchResult.builder().withFound(false).build();
            }
        });
        int[] set1 = {1, 3, 5};
        int[] set2 = {1, 3, 5};
        Assert.assertThat(1.0d, is(equalTo(similarityFinder.calculateJackardSimilarity(set1, set2))));
    }

    @Test
    public void jackardSimilarityWithNoIntersectShouldReturnZero() {
        similarityFinder = new SimilarityFinder((key, seq) -> SearchResult.builder().withFound(false).build());
        int[] set1 = {1, 3, 5};
        int[] set2 = {2, 4, 6};
        Assert.assertThat(0d, is(equalTo(similarityFinder.calculateJackardSimilarity(set1, set2))));
    }

    @Test
    public void jackardSimilarityWithIntersectShouldReturnAHalf() {
        similarityFinder = new SimilarityFinder((key, seq) -> {
            if (key == seq[1] || key == seq[2])
                return SearchResult.builder().withFound(true).build();
            return SearchResult.builder().withFound(false).build();
        });
        int[] set1 = {2, 3};
        int[] set2 = {1, 2, 3, 4};
        Assert.assertThat(0.5d, is(equalTo(similarityFinder.calculateJackardSimilarity(set1, set2))));
    }

    @Test
    public void jackardSimilarityWithDifferentSetsShouldReturnZero() {
        similarityFinder = new SimilarityFinder((key, seq) -> SearchResult.builder().withFound(false).build());
        int[] set1 = {1, 2, 3, 8};
        int[] set2 = {4, 5, 7, 10};
        Assert.assertThat(0d, is(equalTo(similarityFinder.calculateJackardSimilarity(set1, set2))));
    }

    @Test
    public void jackardSimilarityCountCallsOfSearchMethod() {
        SequenceSearcherDublerForTest searcherDubler = new SequenceSearcherDublerForTest();

        int[] set1 = {1, 2, 3, 4, 5, 6};
        int[] set2 = {11, 22, 33, 44, 55, 66, 77};

        similarityFinder = new SimilarityFinder(searcherDubler);
        similarityFinder.calculateJackardSimilarity(set1, set2);
        Assert.assertThat(set1.length, is(equalTo(searcherDubler.getCallCount())));
    }

    class SequenceSearcherDublerForTest implements edu.iis.mto.search.SequenceSearcher {

        private int callCount;

        @Override
        public SearchResult search(int key, int[] seq) {
            callCount++;
            return SearchResult.builder().withFound(false).build();
        }

        int getCallCount() {
            return callCount;
        }
    }
}
