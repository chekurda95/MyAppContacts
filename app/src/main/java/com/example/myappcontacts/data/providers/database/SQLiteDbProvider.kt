package com.example.myappcontacts.data.providers.database

import com.example.myappcontacts.data.dao.contacts.db.ContactsModel
import com.example.myappcontacts.data.dao.contactsmap.db.MapMarkersModel
import com.example.myappcontacts.data.providers.database.MyStorIOSQLite.IMyStorIOSQLite
import io.reactivex.Completable
import io.reactivex.Single
import java.util.*

class SQLiteDbProvider(private val dataBase: IMyStorIOSQLite) : IDataBaseProvider {

    override fun addContact(): Single<UUID> {
        return Single.fromCallable { dataBase.addContact() }
    }

    override fun updateContact(contactsModel: ContactsModel): Completable {
        return Completable.fromAction { dataBase.updateContact(contactsModel) }
    }

    override fun loadContact(contactId: UUID): Single<ContactsModel> {
        return Single.fromCallable { dataBase.getContact(contactId) }
    }

    override fun loadContactsList(): Single<List<ContactsModel>> {
        return Single.fromCallable { dataBase.getContactsList() }
    }

    override fun deleteContact(contactId: UUID): Completable {
        return Completable.fromAction { dataBase.deleteContact(contactId) }
    }

    override fun loadMapMarkersList(): Single<List<MapMarkersModel>> {
        return Single.fromCallable { dataBase.getMapMarkersList() }
    }
}