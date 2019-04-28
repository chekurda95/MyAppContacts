package com.example.myappcontacts.data.repositories.contacts

import com.example.myappcontacts.data.dao.contacts.db.ContactsModel
import io.reactivex.Completable
import io.reactivex.Single
import java.util.*

interface IContactsRepository{

    fun loadContact(contactId: UUID): Single<ContactsModel>

    fun updateContact(contactsModel: ContactsModel): Completable

}