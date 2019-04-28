package com.example.myappcontacts.data.repositories.contactsmap

import com.example.myappcontacts.data.dao.contactsmap.MapMarkersModel

import io.reactivex.Single

interface IContactsMapRepository {

    fun loadMapMarkersList(): Single<List<MapMarkersModel>>
}
