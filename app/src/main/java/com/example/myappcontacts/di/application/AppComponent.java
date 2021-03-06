package com.example.myappcontacts.di.application;

import com.example.myappcontacts.di.contacts.ContactsComponent;
import com.example.myappcontacts.di.contacts.ContactsModule;
import com.example.myappcontacts.di.contactslist.ContactsListComponent;
import com.example.myappcontacts.di.contactslist.ContactsListModule;
import com.example.myappcontacts.di.contactsmap.ContactsMapComponent;
import com.example.myappcontacts.di.contactsmap.ContactsMapModule;
import com.example.myappcontacts.presentation.contacthost.view.ContactsHostActivity;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {AppModule.class})
@Singleton
public interface AppComponent {
    void inject(ContactsHostActivity contactsHostActivity);

    ContactsComponent plus(ContactsModule contactsModule);

    ContactsListComponent plus(ContactsListModule contactsListModule);

    ContactsMapComponent plus(ContactsMapModule contactsMapModule);
}
