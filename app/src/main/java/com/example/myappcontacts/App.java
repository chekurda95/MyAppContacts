package com.example.myappcontacts;

import android.app.Application;
import android.util.Log;

import com.example.myappcontacts.di.application.AppComponent;
import com.example.myappcontacts.di.application.AppModule;
import com.example.myappcontacts.di.application.DaggerAppComponent;
import com.example.myappcontacts.di.contacts.ContactsComponent;
import com.example.myappcontacts.di.contacts.ContactsModule;
import com.example.myappcontacts.di.contactslist.ContactsListComponent;
import com.example.myappcontacts.di.contactslist.ContactsListModule;
import com.facebook.drawee.backends.pipeline.Fresco;

public class App extends Application {
    private static App mApp;
    private AppComponent mAppComponent;

    private ContactsComponent mContactsComponent;
    private ContactsListComponent mContactsListComponent;

    public App() {
        mApp = this;
    }

    public static App get() {
        return mApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }

    public ContactsComponent plusContactsComponent(ContactsModule contactsModule) {
        if (mContactsComponent == null) {
            mContactsComponent = getAppComponent().plus(contactsModule);
        }
        return mContactsComponent;
    }

    public ContactsListComponent plusContactsListComponent(ContactsListModule contactsListModule) {
        if (mContactsListComponent == null) {
            mContactsListComponent = getAppComponent().plus(contactsListModule);
        }
        return mContactsListComponent;
    }

    public void clearContactsComponent() {
        mContactsComponent = null;
    }

    public void clearContactsListComponent() {
        mContactsListComponent = null;
    }


}
