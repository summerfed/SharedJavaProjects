package com.svi.bpo.constants;

public enum ImageFormat {

    GIF("gif", ".gif"),
    JPEG("jpeg", ".jpg", ".jpeg","jpg","jpeg"),
    PNG("png", ".png"),
    TIFF("tiff", ".tif", ".tiff");

    String name;
    String[] extensions;

    ImageFormat(String name, String... extensions) {
        this.name = name;
        this.extensions = extensions;
    }

    public String[] getExtensions() {
        return extensions;
    }

    public String getName() {
        return name;
    }
}
