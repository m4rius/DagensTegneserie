package com.marius.dagenstegneserie.parsers;


import com.google.appengine.repackaged.com.google.common.base.Strings;
import com.marius.dagenstegneserie.Cartoon;
import org.joda.time.DateTime;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Logger;

public abstract class AbstractCartoonProvider implements CartoonProvider {

    private static final Logger log = Logger.getLogger(AbstractCartoonProvider.class.getName());

    protected String readURLContent(String urls) throws IOException {

        try {
            return getContent(urls);
        } catch (IOException e) {
            log.info("Error reading url. Try again");
            try {
                return getContent(urls);
            } catch (IOException e2) {
                log.severe("Error on second try: " + e2.getMessage());
                throw e2;
            }

        }
    }

    private String getContent(String urls) throws IOException {

        URL url = new URL(urls);
        URLConnection conn = url.openConnection();
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuffer sb = new StringBuffer();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        return sb.toString();
    }

    protected String replaceWithDate(String cartoonUrl, DateTime dateTime) {
        String year = Strings.padStart(String.valueOf(dateTime.getYear()), 4, '0');
        String month = Strings.padStart(String.valueOf(dateTime.getMonthOfYear()), 2, '0');
        String day = Strings.padStart(String.valueOf(dateTime.getDayOfMonth()), 2, '0');

        String url = cartoonUrl.replace("$year", year);
        url = url.replace("$month", month);
        url = url.replace("$day", day);
        return url;
    }
}
