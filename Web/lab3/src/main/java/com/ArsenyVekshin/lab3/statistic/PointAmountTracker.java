package com.ArsenyVekshin.lab3.statistic;


import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Destroyed;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Named;
import javax.management.AttributeChangeNotification;
import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;

@Named
@ApplicationScoped
public class PointAmountTracker extends NotificationBroadcasterSupport implements PointAmountTrackerMBean {

	public void init(@Observes @Initialized(ApplicationScoped.class) Object unused) {
		MBeanRegistryUtil.registerBean(this, "PointAmountTrackerMBean");
	}

	public void destroy(@Observes @Destroyed(ApplicationScoped.class) Object unused) {
		MBeanRegistryUtil.unregisterBean(this);
	}

	private int amount;
	private int incorrectAmount;
	
    public PointAmountTracker() {
        addNotificationListener((notification, handback) -> {
            System.out.println("*** Handling new notification ***");
            System.out.println("Message: " + notification.getMessage());
            System.out.println("Seq: " + notification.getSequenceNumber());
            System.out.println("*********************************");
        }, null, null);
    }

	@Override
	public int getAmount() {
		return amount;
	}

	@Override
	public int getIncorrectAmount() {
		return incorrectAmount;
	}

	@Override
	public float getCorrectPercent() {
		if(amount==0) return 0f;
		return (float) (amount - incorrectAmount) / amount ;
	}

	@Override
	public void click(boolean result) {
		amount++;
		if(!result) incorrectAmount++;
		if (amount%15 == 0) {
	        Notification n = new AttributeChangeNotification(
					this,
					amount,
					System.currentTimeMillis(),
					"15 points accepted",
					"amount",
					"int",
					2,
					3
			);
	        sendNotification(n);
		}
	}
}
