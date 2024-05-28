package com.ArsenyVekshin.lab3.statistic;

public interface PointAmountTrackerMBean {
    int getAmount();
    int getIncorrectAmount();

    float getCorrectPercent();
    void click(boolean result);
}
