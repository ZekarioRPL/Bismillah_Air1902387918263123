package com.example.bismillah_air.API;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Respon {

    @SerializedName("meesage")
    @Expose
    private String meesage;
    @SerializedName("data")
    @Expose
    private Data data;

    public String getMeesage() {
        return meesage;
    }

    public void setMeesage(String meesage) {
        this.meesage = meesage;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
