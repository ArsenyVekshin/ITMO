package com.ArsenyVekshin.lab3.statistic;

public interface PointStatisticMBean {
    int getDotsCounter();
    int getMissCounter();

    float getHitsPercent();
}
