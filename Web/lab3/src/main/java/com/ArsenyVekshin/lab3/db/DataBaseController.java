package com.ArsenyVekshin.lab3.db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.util.List;
import java.util.logging.Logger;


@Named
@ApplicationScoped
public class DataBaseController {
    private static Logger log = Logger.getLogger(DataBaseController.class.getName());
    private SessionFactory factory;
    private Session session;

    public DataBaseController() {
        try {
            this.factory = new Configuration()
                    .configure("hibernate.cfg.xml")
                    .addAnnotatedClass(HitResult.class)
                    .buildSessionFactory();

            this.createSession();
        } catch (Exception e) {
            System.out.println("Exception during session factory init: " + e.getMessage());
        }
    }

    private void createSession() {
        this.session = factory.getCurrentSession();
    }

    public List<HitResult> getUserHits(String sessionId) {
        if (sessionId == null) return null;
        createSession();

        this.session.beginTransaction();

        String sqlRequest = "SELECT hit FROM HitResult hit WHERE hit.sessionId= :sessionId AND hit.removed=false";

        List<HitResult> results = this.session.createQuery(sqlRequest, HitResult.class)
                .setParameter("sessionId", sessionId)
                .getResultList();

        this.session.getTransaction().commit();

        System.out.println("Get hits from db: " + results.size());

        return results;
    }

    public void addHitResult(HitResult hitResult) {
        System.out.println("check");
        if (hitResult == null) return;
        createSession();

        System.out.println("Start saving hit");
        this.session.beginTransaction();
        this.session.save(hitResult);
        this.session.getTransaction().commit();

        System.out.println("Save hit to db");
    }

    public void markUserHitsRemoved(String sessionId) {
        if (sessionId == null) return;
        createSession();

        this.session.beginTransaction();
        log.info("User " + sessionId + " removed all his points");
        this.session.createQuery("DELETE HitResult WHERE sessionId= :sessionId")
                .setParameter("sessionId", sessionId)
                .executeUpdate();

        this.session.getTransaction().commit();
    }
}
