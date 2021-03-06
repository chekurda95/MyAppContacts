package com.example.myappcontacts.di.contactsmap;

import com.example.myappcontacts.business.contactsmap.ContactsMapInteractor;
import com.example.myappcontacts.business.contactsmap.IContactsMapInteractor;
import com.example.myappcontacts.data.providers.database.IDataBaseProvider;
import com.example.myappcontacts.data.repositories.contactsmap.ContactsMapRepository;
import com.example.myappcontacts.data.repositories.contactsmap.IContactsMapRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class ContactsMapModule {

    @Provides
    @ContactsMapScope
    IContactsMapInteractor provideContactsMapInteractor(IContactsMapRepository contactsMapRepository) {
        return new ContactsMapInteractor(contactsMapRepository);
    }

    @Provides
    @ContactsMapScope
    IContactsMapRepository provideContactsMapReporitory(IDataBaseProvider dataBaseProvider) {
        return new ContactsMapRepository(dataBaseProvider);
    }
}
