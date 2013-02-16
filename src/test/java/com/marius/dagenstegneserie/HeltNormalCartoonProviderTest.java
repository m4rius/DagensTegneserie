package com.marius.dagenstegneserie;

import com.marius.dagenstegneserie.parsers.HeltNormaltCartoonProvider;
import org.junit.Test;

public class HeltNormalCartoonProviderTest {

    @Test
    public void testParse() {
        new HeltNormaltCartoonProvider().findUrlFor(Cartoon.tommy_tigern);

    }
}
