package com.marius.dagenstegneserie;

import com.marius.dagenstegneserie.parsers.ReplaceDateCartoonProvider;
import org.junit.Test;

public class HeltNormalCartoonProviderTest {

    @Test
    public void testParse() {
        new ReplaceDateCartoonProvider().findUrlFor(Cartoon.tommy_tigern);

    }
}
