package com.ixpath.appmanager.service;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Collation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.ixpath.appmanager.model.App;

import java.util.List;
import java.util.Locale;

@Service
public class AppsService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<App> findAllApps(String env){
        return mongoTemplate.findAll(App.class,"App_"+env);
    }

    public List<App> findAllPublishedApps(){
        return mongoTemplate.findAll(App.class,"Published_App");
    }

    // recuperer tout published App sorted by name
    public List<App> findPublishedAppsSorted(){
        Query query = new Query();
        query.with(Sort.by(Sort.Direction.ASC,"global.name"));
        query.collation(Collation.of("en").strength(Collation.ComparisonLevel.secondary()));
        return mongoTemplate.find(query,App.class,"Published_App");

    }

    // recuperer published App with name %isLike%
    public List<App> findPubAppName(final String appName){
        Query query = new Query();
        query.addCriteria(Criteria.where("global.name").regex(".*"+appName+".*", "i"));
        query.with(Sort.by(Sort.Direction.ASC,"global.name"));
        query.collation(Collation.of("en").strength(Collation.ComparisonLevel.secondary()));
         return mongoTemplate.find(query,App.class,"Published_App");
    }

    //recuperer Published App passer en parametre et l'installer dans l'environnement
    public List<App> findPublishedApp(final String appName){
        Query query = new Query();
        query.addCriteria(Criteria.where("global.name").is(appName));
        return mongoTemplate.find(query,App.class,"Published_App");
    }

    public void saveAll(List<App> app){
        mongoTemplate.insertAll(app);
    }
    
    public App save(App app , String env){
       return mongoTemplate.insert(app,"App_"+env);
    }

    // enregistrer Published App
    public App savePublishedApp(App app ){
        return mongoTemplate.insert(app,"Published_App");
    }

    // Modifier Published App qui exist deja
    public App updatePublishedApp(App newapp ,String appName){
        Query query = new Query();
        query.addCriteria(Criteria.where("global.name").is(appName));
        Document doc = new org.bson.Document(); // org.bson.Document
        mongoTemplate.getConverter().write(newapp, doc);
        Update update = Update.fromDocument(doc);
        mongoTemplate.updateFirst(query, update,"Published_App" );

        return newapp;
    }

    // Modifier la version (Published App)
    public String Modifyversion(String oldversion,String versionlevel){
        String[] ver = oldversion.split("\\.");

        String a = ver[0];
        String b = ver[1];
        int A = Integer.parseInt(a);
        int B = Integer.parseInt(b);
        String newversion = null;

        switch (versionlevel) {
            case "minor": {
                ++B;
                String B2 = String.valueOf(B);
                newversion = a + "." + B2;
                break;
            }
            case "major": {
                ++A;
                String A2 = String.valueOf(A);
                newversion = A2 + ".0";
                break;
            }
        }

        return  newversion;
    }


    // rechercher l'application par nom
    public List<App> findApp(final String appName, String env){
        Query query = new Query();
        query.addCriteria(Criteria.where("global.name").is(appName));
        return mongoTemplate.find(query,App.class,"App_"+env);
    }

    //supprimer l'application par nom
    public void removeApp(final String appName,String env){
        Query query = new Query();
        query.addCriteria(Criteria.where("global.name").is(appName));
        mongoTemplate.remove(query,App.class,"App_"+env);
    }

    // modifier l'application
    public App updateApp(App newapp ,String appName, String env){
        Query query = new Query();
        query.addCriteria(Criteria.where("global.name").is(appName));
        Document doc = new org.bson.Document(); // org.bson.Document
        mongoTemplate.getConverter().write(newapp, doc);
        Update update = Update.fromDocument(doc);
        mongoTemplate.updateFirst(query, update,"App_"+env );

        return newapp;
    }

    // activer l'application
    public void enableApp(final String appName,String env){
        Query query = new Query();
        query.addCriteria(Criteria.where("global.name").is(appName));
        Update update = new Update();
        update.set("active", true);
        mongoTemplate.upsert(query,update,App.class,"App_"+env);
    }
    //desactiver l'application
    public void desableApp(final String appName,String env){
        Query query = new Query();
        query.addCriteria(Criteria.where("global.name").is(appName));
        Update update = new Update();
        update.set("active", false);
        mongoTemplate.upsert(query,update,App.class,"App_"+env);
    }

    //recuperer les applications active sans logo
   public List<App> getActifApps(String env){
       Query query = new Query();
       query.fields().exclude("global.logo");
       query.addCriteria(Criteria.where("active").is(true));
       return mongoTemplate.find(query,App.class,"App_"+env);
   }

}
