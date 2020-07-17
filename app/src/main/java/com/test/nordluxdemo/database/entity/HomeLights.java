package com.test.nordluxdemo.database.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "home_lights")
public class HomeLights  {
    @PrimaryKey
    @NonNull
    Integer id;
    @ColumnInfo(name = "locId")
    String locId;
    @ColumnInfo(name = "netKey")
    String netKey;
    @ColumnInfo(name = "netKeyIndex")
    int netKeyIndex;
    @ColumnInfo(name = "appKey")
    String appKey;
    @ColumnInfo(name = "appKeyIndex")
    int appKeyIndex;
    @ColumnInfo(name = "locName")
    String locName;
    @ColumnInfo(name = "hlName")
    String hlName;
    @ColumnInfo(name = "hlIcon")
    String hlIcon;
    @ColumnInfo(name = "hlPower")
    Boolean hlPower;

    @NonNull
    public Integer getId() {
        return id;
    }

    public void setId(@NonNull Integer id) {
        this.id = id;
    }

    public String getLocId() {
        return locId;
    }

    public void setLocId(String locId) {
        this.locId = locId;
    }

    public String getNetKey() {
        return netKey;
    }

    public void setNetKey(String netKey) {
        this.netKey = netKey;
    }

    public int getNetKeyIndex() {
        return netKeyIndex;
    }

    public void setNetKeyIndex(int netKeyIndex) {
        this.netKeyIndex = netKeyIndex;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public int getAppKeyIndex() {
        return appKeyIndex;
    }

    public void setAppKeyIndex(int appKeyIndex) {
        this.appKeyIndex = appKeyIndex;
    }

    public String getLocName() {
        return locName;
    }

    public void setLocName(String locName) {
        this.locName = locName;
    }

    public String getHlName() {
        return hlName;
    }

    public void setHlName(String hlName) {
        this.hlName = hlName;
    }

    public String getHlIcon() {
        return hlIcon;
    }

    public void setHlIcon(String hlIcon) {
        this.hlIcon = hlIcon;
    }

    public Boolean getHlPower() {
        return hlPower;
    }

    public void setHlPower(Boolean hlPower) {
        this.hlPower = hlPower;
    }
}

