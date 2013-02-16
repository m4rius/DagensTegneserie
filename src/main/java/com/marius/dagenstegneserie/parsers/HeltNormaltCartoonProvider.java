package com.marius.dagenstegneserie.parsers;

import com.google.appengine.repackaged.com.google.common.base.Strings;
import com.marius.dagenstegneserie.Cartoon;
import org.joda.time.DateTime;

public class HeltNormaltCartoonProvider implements CartoonProvider {

    @Override
    public String findUrlFor(Cartoon cartoon) {
        DateTime dateTime = new DateTime();

        String year = Strings.padStart(String.valueOf(dateTime.getYear()), 4, '0');
        String month = Strings.padStart(String.valueOf(dateTime.getMonthOfYear()), 2, '0');
        String day = Strings.padStart(String.valueOf(dateTime.getDayOfMonth()), 2, '0');

        String url = cartoon.getExternalUrl().replace("$year", year);
        url = url.replace("$month", month);
        url = url.replace("$day", day);
        return url;
    }
}
