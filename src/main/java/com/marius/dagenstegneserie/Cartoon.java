package com.marius.dagenstegneserie;

import com.marius.dagenstegneserie.parsers.CartoonProvider;
import com.marius.dagenstegneserie.parsers.DagbladetCartoonProvider;
import com.marius.dagenstegneserie.parsers.HeltNormaltCartoonProvider;

public enum Cartoon {

    lunsh("lunsh", "http://www.dagbladet.no/tegneserie/lunch/", new DagbladetCartoonProvider(), true),
    pondus("pondus", "http://www.dagbladet.no/tegneserie/pondus/", new DagbladetCartoonProvider(), true),
    nemi("nemi", "http://www.dagbladet.no/tegneserie/nemi/", new DagbladetCartoonProvider(), true),
    rocky("rocky", "http://www.dagbladet.no/tegneserie/rocky/", new DagbladetCartoonProvider(), true),
    zelda("zelda", "http://www.dagbladet.no/tegneserie/zelda/", new DagbladetCartoonProvider(), true),
    tommy_tigern("tt", "http://heltnormalt.no/img/tommytigern/$year/$month/$day.jpg", new HeltNormaltCartoonProvider(), false),
    dilbert("dilbert", "http://heltnormalt.no/img/dilbert/$year/$month/$day.jpg", new HeltNormaltCartoonProvider(), false),
    hjalmar("hjalmar", "http://heltnormalt.no/img/hjalmar/$year/$month/$day.jpg", new HeltNormaltCartoonProvider(), false),
    wumo("wumo", "http://heltnormalt.no/img/wumo/$year/$month/$day.jpg", new HeltNormaltCartoonProvider(), false);

    private Cartoon(String appUrl, String externalUrl, CartoonProvider provider, boolean store) {
        this.appUrl = appUrl;
        this.externalUrl = externalUrl;
        this.provider = provider;
        this.store = store;
    }

    private String appUrl;
    private String externalUrl;
    private CartoonProvider provider;
    private boolean store;

    public String getAppUrl() {
        return appUrl;
    }

    public String getExternalUrl() {
        return externalUrl;
    }

    public CartoonProvider getProvider() {
        return provider;
    }

    public boolean isStore() {
        return store;
    }
}
