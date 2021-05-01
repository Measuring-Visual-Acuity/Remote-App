package com.securevision.remote.parsers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SendDetail {
    @SerializedName("status")
    @Expose
    String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
