package com.example.myappcontacts.di.contacts;

import com.example.myappcontacts.business.contact.ContactsInteractor;
import com.example.myappcontacts.business.contact.IContactsInteractor;
import com.example.myappcontacts.data.providers.database.IDataBaseProvider;
import com.example.myappcontacts.data.repositories.contacts.ContactsRepository;
import com.example.myappcontacts.data.repositories.contacts.IContactsRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class ContactsModule {

    @Provides
    @ContactsScope
    IContactsInteractor provideContactsInteractor(IContactsRepository contactsRepository){
        return new ContactsInteractor(contactsRepository);
    }

    @Provides
    @ContactsScope
    IContactsRepository provideContactsRepository(IDataBaseProvider dataBaseProvider){
        return new ContactsRepository(dataBaseProvider);
    }

}
