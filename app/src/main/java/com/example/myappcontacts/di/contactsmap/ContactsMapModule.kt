package com.example.myappcontacts.di.contactsmap

import com.example.myappcontacts.business.contactsmap.ContactsMapInteractor
import com.example.myappcontacts.business.contactsmap.IContactsMapInteractor
import com.example.myappcontacts.data.providers.database.IDataBaseProvider
import com.example.myappcontacts.data.repositories.contactsmap.ContactsMapRepository
import com.example.myappcontacts.data.repositories.contactsmap.IContactsMapRepository

import dagger.Module
import dagger.Provides

@Module
class ContactsMapModule {

    @Provides
    @ContactsMapScope
    fun provideContactsMapInteractor(contactsMapRepository: IContactsMapRepository): IContactsMapInteractor {
        return ContactsMapInteractor(contactsMapRepository)
    }

    @Provides
    @ContactsMapScope
    fun provideContactsMapRepository(dataBaseProvider: IDataBaseProvider): IContactsMapRepository {
        return ContactsMapRepository(dataBaseProvider)
    }
}
