package com.example.myappcontacts.di.contactslist

import com.example.myappcontacts.business.contactslist.ContactsListInteractor
import com.example.myappcontacts.business.contactslist.IContactsListInteractor
import com.example.myappcontacts.data.providers.database.IDataBaseProvider
import com.example.myappcontacts.data.repositories.contactslist.ContactsListRepository
import com.example.myappcontacts.data.repositories.contactslist.IContactsListRepository

import dagger.Module
import dagger.Provides

@Module
class ContactsListModule {

    @Provides
    @ContactsListScope
    fun provideContactsListInteractor(contactsListRepository: IContactsListRepository): IContactsListInteractor {
        return ContactsListInteractor(contactsListRepository)
    }

    @Provides
    @ContactsListScope
    fun provideContactsListRepository(dataBaseProvider: IDataBaseProvider): IContactsListRepository {
        return ContactsListRepository(dataBaseProvider)
    }

}
