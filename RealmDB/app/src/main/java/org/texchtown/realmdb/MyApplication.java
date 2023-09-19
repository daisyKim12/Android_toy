package org.texchtown.realmdb;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MyApplication extends Application {

    RealmConfiguration config;
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        if(config == null) {
            config = new RealmConfiguration.Builder()
                    .name("myrealm.realm")
                    .build();
        }
        Realm.setDefaultConfiguration(config);

    }
}
