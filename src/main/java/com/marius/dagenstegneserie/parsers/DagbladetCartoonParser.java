package com.marius.dagenstegneserie.parsers;


import com.marius.dagenstegneserie.Cartoon;

import java.io.IOException;
import java.util.logging.Logger;

public class DagbladetCartoonParser extends AbstractCartoonParser implements CartoonParser {

    private static final Logger log = Logger.getLogger(DagbladetCartoonParser.class.getName());

    private String findCartoonFromDbHtml(String webpagehtml) {
        String gifUrl = webpagehtml.substring(webpagehtml.indexOf("<img class=\"tegneserie\""));
        gifUrl = gifUrl.substring(gifUrl.indexOf("src=\"")+5);
        gifUrl = gifUrl.substring(0, gifUrl.indexOf("\""));
        log.info(String.format("Found url %s", gifUrl));
        return (gifUrl);
    }

    public String findUrlFor(Cartoon cartoon) {
        String pathToCartoonImg = "";
        try {
            pathToCartoonImg = findCartoonFromDbHtml(readURLContent(cartoon.getExternalUrl()));
        } catch (IOException e) {
            log.severe(e.getMessage());
        }

        log.info(String.format("Path to cartoon %s", pathToCartoonImg));
        return pathToCartoonImg;
    }
}
