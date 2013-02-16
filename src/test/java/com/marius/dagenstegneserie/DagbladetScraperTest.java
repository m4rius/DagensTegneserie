package com.marius.dagenstegneserie;

import junit.framework.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import static junit.framework.Assert.assertNotNull;

@Ignore
public class DagbladetScraperTest {

    @Test
    public void testScrapping() throws IOException {
        DagbladetScraper scraper = new DagbladetScraper();
        String url = scraper.getImageUrl();
        assertNotNull(url);
    }
}
