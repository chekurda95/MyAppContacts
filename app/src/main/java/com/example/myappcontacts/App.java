package com.example.myappcontacts;

import android.app.Application;
import android.util.Log;

import com.example.myappcontacts.di.application.AppComponent;
import com.example.myappcontacts.di.application.AppModule;
import com.example.myappcontacts.di.application.DaggerAppComponent;
import com.example.myappcontacts.di.contacts.ContactsComponent;
import com.example.myappcontacts.di.contacts.ContactsModule;

public class App extends Application {
    private static App mApp;
    private AppComponent mAppComponent;

    private ContactsComponent mContactsComponent;

    public App(){
        mApp = this;
    }

    public static App get(){
        return mApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getAppComponent(){
        return mAppComponent;
    }

    public ContactsComponent plusContactsComponent(ContactsModule contactsModule){
        if(mContactsComponent == null){
            mContactsComponent = getAppComponent().plus(contactsModule);
        }
        return mContactsComponent;
    }

    public void clearContactsComponent(){
        mContactsComponent = null;
    }



}
