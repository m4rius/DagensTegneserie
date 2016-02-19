package com.marius.dagenstegneserie.parsers;

public enum PictureFormats {
    PNG("png"),
    JPG("jpg");
    private String format;

    PictureFormats(String format) {
        this.format = format;
    }

    public String getFormat() {
        return format;
    }
}
