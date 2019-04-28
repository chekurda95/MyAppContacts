package com.example.myappcontacts.data.repositories.contactslist

import com.example.myappcontacts.data.dao.contacts.db.ContactsModel
import com.example.myappcontacts.data.providers.database.IDataBaseProvider
import java.util.UUID

import io.reactivex.Completable
import io.reactivex.Single

class ContactsListRepository(private val dataBaseProvider: IDataBaseProvider) : IContactsListRepository {

    override fun addContact(): Single<UUID> {
        return dataBaseProvider.addContact()
    }

    override fun deleteContact(contactId: UUID): Completable {
        return dataBaseProvider.deleteContact(contactId)
    }

    override fun loadContactsList(): Single<List<ContactsModel>> {
        return dataBaseProvider.loadContactsList()
    }
}
