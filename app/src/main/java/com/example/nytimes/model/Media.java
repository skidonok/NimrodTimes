package com.example.nytimes.model;

import android.net.Uri;

public class Media {
    public String type, caption, copyright;
    public Uri url;

    public Media(String type, String caption, String copyright, Uri url) {
        this.type = type;
        this.caption = caption;
        this.copyright = copyright;
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public Uri getUrl() {
        return url;
    }

    public void setUrl(Uri url) {
        this.url = url;
    }
}
