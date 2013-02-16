package com.marius.dagenstegneserie.parsers;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Logger;

public abstract class AbstractCartoonParser {

    private static final Logger log = Logger.getLogger(AbstractCartoonParser.class.getName());

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
}
