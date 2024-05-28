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
public class Test extends NotificationBroadcasterSupport implements TestMBean {

    public void init(@Observes @Initialized(ApplicationScoped.class) Object unused) {
        MBeanRegistryUtil.registerBean(this, "TestMBean");
    }

    public void destroy(@Observes @Destroyed(ApplicationScoped.class) Object unused) {
        MBeanRegistryUtil.unregisterBean(this);
    }

	private long sequenceNumber = 1L;
    private String message = "Hello World";
     
    public Test() {
        addNotificationListener((notification, handback) -> {
            System.out.println("*** Handling new notification ***");
            System.out.println("Message: " + notification.getMessage());
            System.out.println("Seq: " + notification.getSequenceNumber());
            System.out.println("*********************************");
        }, null, null);
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public void sayHello() {
        System.out.println(message);
    }

    @Override
    public void setMessage(String message) {
        String oldMessage = this.message;
        this.message = message;
        Notification n = new AttributeChangeNotification(this, sequenceNumber++, System.currentTimeMillis(), "Message changed", "Message", "String", oldMessage, this.message);
        sendNotification(n);
    }
}
