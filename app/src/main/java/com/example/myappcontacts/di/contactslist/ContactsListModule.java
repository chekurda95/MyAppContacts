package com.example.myappcontacts.di.contactslist;

import com.example.myappcontacts.business.contactslist.ContactsListInteractor;
import com.example.myappcontacts.business.contactslist.IContactsListInteractor;
import com.example.myappcontacts.data.providers.database.IDataBaseProvider;
import com.example.myappcontacts.data.repositories.contactslist.ContactsListRepository;
import com.example.myappcontacts.data.repositories.contactslist.IContactsListRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class ContactsListModule {

    @Provides
    @ContactsListScope
    IContactsListInteractor provideContactsListInteractor(IContactsListRepository contactsListRepository){
        return new ContactsListInteractor(contactsListRepository);
    }

    @Provides
    @ContactsListScope
    IContactsListRepository provideContactsListRepository(IDataBaseProvider dataBaseProvider){
        return new ContactsListRepository(dataBaseProvider);
    }

}
