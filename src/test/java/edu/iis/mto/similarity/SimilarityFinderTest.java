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
        Assert.assertThat(1.0d, is(equalTo(similarityFinder.calculateJackardSimilarity(set1,set2))));

    }
}
