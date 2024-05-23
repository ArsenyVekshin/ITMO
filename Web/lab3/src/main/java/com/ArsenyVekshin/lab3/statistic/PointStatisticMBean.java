package com.ArsenyVekshin.lab3.statistic;

import javax.management.MXBean;

public interface PointStatisticMBean {
    int getDotsCounter();
    int getMissCounter();

    float getHitsPercent();
}
