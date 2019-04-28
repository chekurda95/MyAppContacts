package com.example.myappcontacts.data.repositories.contacts

import com.example.myappcontacts.data.dao.contacts.db.ContactsModel
import com.example.myappcontacts.data.providers.database.IDataBaseProvider
import io.reactivex.Completable
import io.reactivex.Single
import java.util.*

class ContactsRepository(private val dataBaseProvider: IDataBaseProvider) : IContactsRepository {
    override fun loadContact(contactId: UUID): Single<ContactsModel> {
        return dataBaseProvider.loadContact(contactId)
    }

    override fun updateContact(contactsModel: ContactsModel): Completable {
        return dataBaseProvider.updateContact(contactsModel)
    }
}