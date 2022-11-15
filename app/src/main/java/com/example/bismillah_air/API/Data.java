package com.example.bismillah_air.API;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("debu_before_filter")
    @Expose
    private Integer debuBeforeFilter;
    @SerializedName("debu_after_filter")
    @Expose
    private Integer debuAfterFilter;
    @SerializedName("jangka_waktu")
    @Expose
    private String jangkaWaktu;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDebuBeforeFilter() {
        return debuBeforeFilter;
    }

    public void setDebuBeforeFilter(Integer debuBeforeFilter) {
        this.debuBeforeFilter = debuBeforeFilter;
    }

    public Integer getDebuAfterFilter() {
        return debuAfterFilter;
    }

    public void setDebuAfterFilter(Integer debuAfterFilter) {
        this.debuAfterFilter = debuAfterFilter;
    }

    public String getJangkaWaktu() {
        return jangkaWaktu;
    }

    public void setJangkaWaktu(String jangkaWaktu) {
        this.jangkaWaktu = jangkaWaktu;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;

    }
}
