package edu.iis.mto.similarity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class SimilarityFinderTest {

    private SimilarityFinder similarityFinder;

    @Before
    public void initialize() {

        similarityFinder = new SimilarityFinder(new SequenceSercherDubler());
    }

    @Test
    public void jackardSimilarityWithEmptySetsShouldReturnOne() {
        int[] set1 = {};
        int[] set2 = {};
        Assert.assertThat(1.0d, is(equalTo(similarityFinder.calculateJackardSimilarity(set1, set2))));

    }

    @Test
    public void jackardSimilarityWithSameSetsShouldReturnOne() {
        int[] set1 = {1, 3, 5};
        int[] set2 = {1, 3, 5};
        Assert.assertThat(1.0d, is(equalTo(similarityFinder.calculateJackardSimilarity(set1, set2))));

    }

    @Test
    public void jackardSimilarityWithNoIntersectShouldReturnZero() {
        int[] set1 = {1, 3, 5};
        int[] set2 = {2, 4, 6};
        Assert.assertThat(0d, is(equalTo(similarityFinder.calculateJackardSimilarity(set1, set2))));

    }

    @Test
    public void jackardSimilarityWithIntersectShouldReturnAHalf() {
        int[] set1 = {1, 2};
        int[] set2 = {1, 2, 3, 4};
        Assert.assertThat(0.5d, is(equalTo(similarityFinder.calculateJackardSimilarity(set1, set2))));
    }
}
