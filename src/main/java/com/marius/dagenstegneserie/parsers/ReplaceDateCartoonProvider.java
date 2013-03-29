package com.marius.dagenstegneserie.parsers;

import com.marius.dagenstegneserie.Cartoon;
import org.joda.time.DateTime;

public class ReplaceDateCartoonProvider extends AbstractCartoonProvider {

    @Override
    public String findUrlFor(Cartoon cartoon) {
        DateTime dateTime = new DateTime();

        return replaceWithDate(cartoon.getExternalUrl(), dateTime);
    }

}
