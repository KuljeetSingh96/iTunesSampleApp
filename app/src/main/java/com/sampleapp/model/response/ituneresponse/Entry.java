
package com.sampleapp.model.response.ituneresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Entry extends RealmObject {

    @SerializedName("im:name")
    @Expose
    private ImName imName;
    @SerializedName("im:image")
    @Expose
    private RealmList<ImImage> imImage = null;


    @SerializedName("title")
    @Expose
    private Title title;
    @SerializedName("im:artist")
    @Expose
    private ImArtist imArtist;
    @PrimaryKey
    private long transactionID=System.currentTimeMillis();

    public ImName getImName() {
        return imName;
    }

    public void setImName(ImName imName) {
        this.imName = imName;
    }

    public List<ImImage> getImImage() {
        return imImage;
    }

    public void setImImage(RealmList<ImImage> imImage) {
        this.imImage = imImage;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public ImArtist getImArtist() {
        return imArtist;
    }

    public void setImArtist(ImArtist imArtist) {
        this.imArtist = imArtist;
    }

    public long getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(long transactionID) {
        this.transactionID = transactionID;
    }
}
