package com.marius.dagenstegneserie.parsers;

import com.marius.dagenstegneserie.Cartoon;
import org.joda.time.DateTime;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

public class StoredDataCartoonProvider extends AbstractCartoonProvider {

    private static final Logger log = Logger.getLogger(StoredDataCartoonProvider.class.getName());

    @Override
    public String findUrlFor(Cartoon cartoon) {
        DateTime dateTime = new DateTime();

        String replaceWithDate = replaceWithDate(cartoon.getExternalUrl(), dateTime);

        for (PictureFormats format : PictureFormats.values()) {

            String fullUrl = replaceWithFormat(replaceWithDate, format);

            try {
                URL url = new URL(fullUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                int code = connection.getResponseCode();

                if (code == 200) {
                    return fullUrl;
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        log.severe("Could not find url that returns 200 for " + cartoon.name());

        return null;
    }

    private String replaceWithFormat(String url, PictureFormats format) {
        return url.replace("$format", format.getFormat());
    }
}
