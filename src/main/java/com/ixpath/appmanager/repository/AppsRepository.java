package com.ixpath.appmanager.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ixpath.appmanager.model.App;

public interface AppsRepository  extends MongoRepository<App,String> {
}
