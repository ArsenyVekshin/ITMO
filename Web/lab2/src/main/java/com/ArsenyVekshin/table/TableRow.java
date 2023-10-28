package com.ArsenyVekshin.table;

import java.io.Serializable;

public final class TableRow implements Serializable {
    private float x;
    private float y;
    private float r;
    private boolean hit;
    private String clientDate;
    private double scriptWorkingTime;

    public TableRow(float x, float y, float r, boolean hit, String clientDate, double scriptWorkingTime) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.hit = hit;
        this.clientDate = clientDate;
        this.scriptWorkingTime = scriptWorkingTime;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getR() {
        return r;
    }

    public boolean isHit() {
        return hit;
    }

    public String getClientDate() {
        return clientDate;
    }

    public double getScriptWorkingTime() {
        return scriptWorkingTime;
    }
}
