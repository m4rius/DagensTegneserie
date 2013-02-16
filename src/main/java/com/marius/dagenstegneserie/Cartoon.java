package com.marius.dagenstegneserie;

import com.marius.dagenstegneserie.parsers.CartoonParser;
import com.marius.dagenstegneserie.parsers.DagbladetCartoonParser;

public enum Cartoon {

    lunsh("lunsh", "http://www.dagbladet.no/tegneserie/lunch/", new DagbladetCartoonParser()),
    pondus("pondus", "http://www.dagbladet.no/tegneserie/pondus/", new DagbladetCartoonParser()),
    nemi("pondus", "http://www.dagbladet.no/tegneserie/nemi/", new DagbladetCartoonParser()),
    rocky("rocky", "http://www.dagbladet.no/tegneserie/rocky/", new DagbladetCartoonParser()),
    zelda("zelda", "http://www.dagbladet.no/tegneserie/zelda/", new DagbladetCartoonParser());

    private Cartoon(String appUrl, String externalUrl, CartoonParser parser) {
        this.appUrl = appUrl;
        this.externalUrl = externalUrl;
        this.parser = parser;
    }

    private String appUrl;
    private String externalUrl;
    private CartoonParser parser;

    public String getAppUrl() {
        return appUrl;
    }

    public String getExternalUrl() {
        return externalUrl;
    }

    public CartoonParser getParser() {
        return parser;
    }
}
