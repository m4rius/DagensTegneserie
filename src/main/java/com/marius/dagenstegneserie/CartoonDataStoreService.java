package com.marius.dagenstegneserie;

import com.google.appengine.api.datastore.*;

import java.util.Date;
import java.util.logging.Logger;

public class CartoonDataStoreService {
    private static final Logger log = Logger.getLogger(CartoonDataStoreService.class.getName());

    private static final String KIND = "Cartoon";

    public void storeAllCartoons() {
        for (Cartoon cartoon : Cartoon.values()) {
            findUrlAndStore(cartoon);
        }
    }

    public String getUrlFor(Cartoon cartoon) {
        Entity entity = findEntity(cartoon);
        if (entity == null) {
            entity = findUrlAndStore(cartoon);
        }

        if (entity != null) {
            return entity.getProperty("url").toString();
        }

        log.severe(String.format("Could not find url for %s", cartoon.name()));
        return "";
    }

    private Entity findUrlAndStore(Cartoon cartoon) {
        String url = findUrlToCartoon(cartoon);
        return store(cartoon, url);
    }

    private Entity store(Cartoon cartoon, String url) {
        Entity entity = findEntity(cartoon);
        if (entity == null) {
            createAndStoreNewEntity(cartoon, url);
        } else {
            entity.setProperty("url", url);
            entity.setProperty("updated", new Date());
            storeEntity(entity);
        }

        return entity;
    }

    private String findUrlToCartoon(Cartoon cartoon) {
        return cartoon.getParser().findUrlFor(cartoon);
    }

    private Entity createAndStoreNewEntity(Cartoon cartoon, String url) {
        Entity entity = new Entity(KIND, cartoon.name());
        entity.setProperty("cartoonType", cartoon.name());
        entity.setProperty("url", url);
        entity.setProperty("created", new Date());
        //entity.setProperty("date", new Date());

        storeEntity(entity);

        return entity;

    }

    private void storeEntity(Entity entity) {
        DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
        datastoreService.put(entity);
    }

    private Entity findEntity(Cartoon cartoon) {
        Query query = new Query(KIND, createKey(cartoon));
        DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
        return datastoreService.prepare(query).asSingleEntity();
    }

    private Key createKey(Cartoon cartoon) {
        return KeyFactory.createKey(KIND, cartoon.name());
    }
}
