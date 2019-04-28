package com.example.myappcontacts.data.providers.database

import com.example.myappcontacts.data.dao.contacts.db.ContactsModel
import com.example.myappcontacts.data.dao.contactsmap.MapMarkersModel
import io.reactivex.Completable
import io.reactivex.Single
import java.util.*

interface IDataBaseProvider {
    fun addContact(): Single<UUID>

    fun updateContact(contactsModel: ContactsModel): Completable

    fun loadContact(contactId: UUID): Single<ContactsModel>

    fun loadContactsList(): Single<List<ContactsModel>>

    fun deleteContact(contactId: UUID): Completable

    fun loadMapMarkersList(): Single<List<MapMarkersModel>>
}