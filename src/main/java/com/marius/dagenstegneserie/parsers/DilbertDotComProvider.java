package com.marius.dagenstegneserie.parsers;

import com.marius.dagenstegneserie.Cartoon;

import java.io.IOException;
import java.util.logging.Logger;

public class DilbertDotComProvider extends AbstractCartoonProvider {

    private static final Logger log = Logger.getLogger(DilbertDotComProvider.class.getName());

    @Override
    public String findUrlFor(Cartoon cartoon) {
        String pathToCartoon = "";

        try {
            pathToCartoon = findCartoonFromDilbertHtml(readURLContent(cartoon.getExternalUrl()));

        } catch (IOException io) {
            log.severe(io.getMessage());
        }

        log.info(String.format("Path to cartoon %s", pathToCartoon));

        return pathToCartoon;
    }

    private String findCartoonFromDilbertHtml(String html) {
        int indexImgTag = html.indexOf("img-responsive img-comic");
        String fromImgTag = html.substring(indexImgTag);
        int indexSrcAttribute = fromImgTag.indexOf("src") +5;
        String fromSrc = fromImgTag.substring(indexSrcAttribute);
        int last = fromSrc.indexOf("\"");
        String src = fromSrc.substring(0, last);

        return src;
    }
}
