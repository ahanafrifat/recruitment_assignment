package com.aah.recruitmentassignment.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CvFile {
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("tsync_id")
    @Expose
    private String tsyncId;

    public CvFile() {
    }

    public CvFile(String id, String tsyncId) {
        this.id = id;
        this.tsyncId = tsyncId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTsyncId() {
        return tsyncId;
    }

    public void setTsyncId(String tsyncId) {
        this.tsyncId = tsyncId;
    }
}
