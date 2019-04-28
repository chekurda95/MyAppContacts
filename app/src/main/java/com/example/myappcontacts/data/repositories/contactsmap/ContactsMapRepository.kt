package com.example.myappcontacts.data.repositories.contactsmap

import com.example.myappcontacts.data.dao.contactsmap.MapMarkersModel
import com.example.myappcontacts.data.providers.database.IDataBaseProvider

import io.reactivex.Single

class ContactsMapRepository(private val dataBaseProvider: IDataBaseProvider) : IContactsMapRepository {

    override fun loadMapMarkersList(): Single<List<MapMarkersModel>> {
        return dataBaseProvider.loadMapMarkersList()
    }
}
