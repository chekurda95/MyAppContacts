package com.example.myappcontacts.business.contactslist

import com.example.myappcontacts.data.dao.contacts.db.ContactsModel
import java.util.UUID

import io.reactivex.Completable
import io.reactivex.Single

interface IContactsListInteractor {

    fun addContact(): Single<UUID>

    fun deleteContact(contactId: UUID): Completable

    fun loadContactsList(): Single<List<ContactsModel>>
}
