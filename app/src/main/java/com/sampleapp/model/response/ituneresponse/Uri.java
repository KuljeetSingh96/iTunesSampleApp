
package com.sampleapp.model.response.ituneresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class Uri extends RealmObject {

    @SerializedName("label")
    @Expose
    private String label;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

}
