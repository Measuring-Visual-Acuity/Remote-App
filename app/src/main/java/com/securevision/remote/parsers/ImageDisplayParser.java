package com.securevision.remote.parsers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImageDisplayParser {
    @SerializedName("img_url")
    @Expose
    private String imgurl;
    @SerializedName("length_in_inch")
    @Expose
    private double lenghtInInch;
    @SerializedName("accuity")
    @Expose
    private double accuity;
    @SerializedName("letter")
    @Expose
    private String letter;

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public double getLenghtInInch() {
        return lenghtInInch;
    }

    public void setLenghtInInch(double lenghtInInch) {
        this.lenghtInInch = lenghtInInch;
    }

    public double getAccuity() {
        return accuity;
    }

    public void setAccuity(double accuity) {
        this.accuity = accuity;
    }
}
