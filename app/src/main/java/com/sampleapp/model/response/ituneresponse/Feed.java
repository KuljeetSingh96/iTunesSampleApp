
package com.sampleapp.model.response.ituneresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

public class Feed extends RealmObject {

    @SerializedName("author")
    @Expose
    private Author author;
    @SerializedName("entry")
    @Expose
    private RealmList<Entry> entry = null;
    @SerializedName("title")
    @Expose
    private Title_ title;

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public List<Entry> getEntry() {
        return entry;
    }

    public void setEntry(RealmList<Entry> entry) {
        this.entry = entry;
    }

    public Title_ getTitle() {
        return title;
    }

    public void setTitle(Title_ title) {
        this.title = title;
    }

}
