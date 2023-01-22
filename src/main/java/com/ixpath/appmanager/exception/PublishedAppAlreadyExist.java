package com.ixpath.appmanager.exception;

import com.ixpath.appmanager.model.App;
import com.ixpath.appmanager.service.AppsService;
import org.springframework.beans.factory.annotation.Autowired;

public class PublishedAppAlreadyExist extends Exception {


    public PublishedAppAlreadyExist() {
        super("Version de L'appilcation Published Exist deja");
        this.setCodeError("400");
    }


}
