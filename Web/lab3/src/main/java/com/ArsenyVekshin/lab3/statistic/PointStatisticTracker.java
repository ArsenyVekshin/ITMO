package com.ArsenyVekshin.lab3.statistic;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Destroyed;
import javax.enterprise.context.Initialized;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Named;
import java.io.Serializable;

@Named()
@ApplicationScoped
public class PointStatisticTracker implements Serializable, PointStatisticMBean{

    public void init(@Observes @Initialized(ApplicationScoped.class) Object unused) {
        MBeanRegistry.registerBean(this, "PointStatisticTracker");
    }


    public void destroy(@Observes @Destroyed(ApplicationScoped.class) Object unused) {
        MBeanRegistry.unregisterBean(this);
    }


    @Override
    public int getDotsCounter() {
        return 1;
    }

    @Override
    public int getMissCounter() {
        return 2;
    }

    @Override
    public float getHitsPercent() {
        return 3;
    }
}
