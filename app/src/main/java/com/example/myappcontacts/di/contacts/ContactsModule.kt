package com.example.myappcontacts.di.contacts

import com.example.myappcontacts.business.contact.ContactsInteractor
import com.example.myappcontacts.business.contact.IContactsInteractor
import com.example.myappcontacts.data.providers.database.IDataBaseProvider
import com.example.myappcontacts.data.repositories.contacts.ContactsRepository
import com.example.myappcontacts.data.repositories.contacts.IContactsRepository
import dagger.Module
import dagger.Provides

@Module
class ContactsModule {

    @Provides
    @ContactsScope
    fun provideContactsInteractor(contactsRepository: IContactsRepository):IContactsInteractor = ContactsInteractor(contactsRepository)

    @Provides
    @ContactsScope
    fun provideContactsRepository(dataBaseProvider: IDataBaseProvider):IContactsRepository = ContactsRepository(dataBaseProvider)
}