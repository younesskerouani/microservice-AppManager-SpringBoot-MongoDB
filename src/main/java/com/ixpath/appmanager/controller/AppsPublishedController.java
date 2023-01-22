package com.ixpath.appmanager.controller;

import com.ixpath.appmanager.exception.AppNotFound;
import com.ixpath.appmanager.exception.PublishedAppAlreadyExist;
import com.ixpath.appmanager.model.App;
import com.ixpath.appmanager.service.AppsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("appmanager")
public class AppsPublishedController {

    @Autowired
    private AppsService appsService;


    // Creer Published App dans l'environnement Published APP
    @PostMapping("/setPublishedApp/{ApptoPublish}")
    public App savePUBapp(@PathVariable(name = "ApptoPublish") String ApptoPublish,
                          @RequestParam(name = "versionLevel") String versionlevel,
                          @RequestHeader(name = "env") String env) {

      // recuperer l'application du Collection de l'environnement
        List<App> appsByName = appsService.findApp(ApptoPublish, env);

        if (appsByName.isEmpty())
            throw new AppNotFound();

        else {

              App appPUb = appsByName.get(0);

            String oldversion = (String) appPUb.getGlobal().get("version");
            String newversion = appsService.Modifyversion(oldversion,versionlevel);

                // recuperer l'application du Collection Published App:
                List<App> appPublier = appsService.findPublishedApp(ApptoPublish);
                // ajouter nouvelle version a l'objet App
                appPUb.getGlobal().put("version", newversion);

                if (appPublier.isEmpty()) {
                    return appsService.savePublishedApp(appPUb);
                }
                else {
                        App appPublish = appPublier.get(0);
                        String versionExisted = (String) appPublish.getGlobal().get("version");

                        if (versionExisted.equals(newversion)) { throw new PublishedAppAlreadyExist();   }

                         return appsService.updatePublishedApp(appPUb, ApptoPublish);
                }
        }

    }

    // recuperer tout les applications published
    @GetMapping("/getAllPublishedApp")
    public List<App> listPublishedApps()
    {
        List<App> apps = appsService.findAllPublishedApps();
        if(apps.isEmpty())
            throw new AppNotFound();
        else
            return apps;
    }

    // recuperer tout les application par ordre alphabetique
    @GetMapping("/getPublishedAppSorted")
    public List<App> PublishedAppSorted()
    {
        List<App> appsSorted = appsService.findPublishedAppsSorted();
        if(appsSorted.isEmpty())
            throw new AppNotFound();
        else
            return appsSorted;

    }

    // rechercher l'application Published par name
    @GetMapping("/getPublishedApp/{appname}")
    public List<App> PublishedApp(@PathVariable(name = "appname") String name)
    {
        List<App> appsByName = appsService.findPubAppName(name);
        if(appsByName.isEmpty())
            throw new AppNotFound();
        else
            return appsByName;
    }

    // installer une application dans l'environnement depuis la Collection PublishedApp
    @PostMapping("/installApp/{appname}")
    public App installApp(@PathVariable(name = "appname") String name,@RequestHeader(name = "env") String env) {
        List<App> apptoinstall = appsService.findPublishedApp(name);
        if (apptoinstall.isEmpty()) {
            throw new AppNotFound();
        }

        List<App> appEnv = appsService.findApp(name, env);
        App applicationInstall = apptoinstall.get(0);

        if (appEnv.isEmpty()) {
                return appsService.save(applicationInstall, env);
            }
        else {
                App applicationEnv = appEnv.get(0);

                String version_a_installer = (String) applicationInstall.getGlobal().get("version");

                  String versionExisted = (String) applicationEnv.getGlobal().get("version");

                  if (version_a_installer.equals(versionExisted)) {
                      throw new PublishedAppAlreadyExist();
                  } else {
                      return appsService.save(applicationInstall, env);
                  }

        }

    }

}
