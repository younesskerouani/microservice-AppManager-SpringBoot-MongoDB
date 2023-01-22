package com.ixpath.appmanager.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ixpath.appmanager.exception.AppNotFound;
import com.ixpath.appmanager.model.App;
import com.ixpath.appmanager.service.AppsService;



import java.util.List;

@RestController
@RequestMapping("appmanager")
public class AppsController {

    @Autowired
    private AppsService appsService;

    // recuperer tout les applications de l'environnement
    @GetMapping("/getApp")
    public List<App> listApps(@RequestHeader(name = "env") String env)
    {
        return appsService.findAllApps(env);
    }

    // recuperer l'application passer en parametre
    @GetMapping("/getApp/{name}")
    public  App getAppName(@PathVariable(name = "name") String name,@RequestHeader(name = "env") String env){
        
    	List<App> appsByName = appsService.findApp(name,env);
    	if(appsByName.isEmpty())
    		throw new AppNotFound();
    	else
    		return appsByName.get(0);
    }

   // inserer nouvelle application dans l'environement
    @PostMapping("/setApp")
    public App saveApps(@RequestBody App app, @RequestHeader(name = "env") String env)
    {
    	//TODO :Vérifier l'existence (AppName)
    		// Si exist ==> Update 
    		// sinon insert
      String appName=String.valueOf(app.getGlobal().get("name"));
        List<App> nomApp = appsService.findApp(appName,env);
         if(nomApp.isEmpty()){
            return appsService.save(app,env);}
         else {
           return  appsService.updateApp(app,appName,env);
         }
    }

    // supprimer l'application par nom
    @DeleteMapping("deleteApp/{name}")
    public void deleteApp(@PathVariable(name = "name") String name,@RequestHeader(name = "env") String env){
        appsService.removeApp(name,env);
    }

    // activer l'application
    @PostMapping("/enable/{name}")
    public void enableApps(@PathVariable(name = "name") String name,@RequestHeader(name = "env") String env){
        appsService.enableApp(name,env);
    }

    // desactiver l'application
    @PostMapping("/desable/{name}")
    public void desableApps(@PathVariable(name = "name") String name,@RequestHeader(name = "env") String env){
        appsService.desableApp(name,env);
    }

    // recuperer les application installer(Actif)
    @GetMapping("/getInstalledApps")
    public List<App> noIcon(@RequestHeader(name = "env") String env){
        List<App> appsByName = appsService.getActifApps(env);
        if(appsByName.isEmpty())
            throw new AppNotFound();
        else
            return appsByName;

    }

}
