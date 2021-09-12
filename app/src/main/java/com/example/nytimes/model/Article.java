package com.example.nytimes.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Article implements Parcelable {
    public String url, title, artcile_abstract, author, published;
    public List<Media> media;

    public Article(String url, String title, String artcile_abstract, String author, String published, List<Media> media) {
        this.url = url;
        this.title = title;
        this.artcile_abstract = artcile_abstract;
        this.author = author;
        this.published = published;
        this.media = media;
    }


    protected Article(Parcel in) {
        url = in.readString();
        title = in.readString();
        artcile_abstract = in.readString();
        author = in.readString();
        published = in.readString();
    }

    public static final Creator<Article> CREATOR = new Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel in) {
            return new Article(in);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtcile_abstract() {
        return artcile_abstract;
    }

    public void setArtcile_abstract(String artcile_abstract) {
        this.artcile_abstract = artcile_abstract;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublished() {
        return published;
    }

    public void setPublished(String published) {
        this.published = published;
    }

    public List<Media> getMedia() {
        return media;
    }

    public void setMedia(List<Media> media) {
        this.media = media;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeString(title);
        dest.writeString(artcile_abstract);
        dest.writeString(author);
        dest.writeString(published);
    }
}
