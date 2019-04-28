package com.example.myappcontacts.business.contactsmap

import com.example.myappcontacts.data.dao.contactsmap.MapMarkersModel

import io.reactivex.Single

interface IContactsMapInteractor {

    fun loadMapMarkersList(): Single<List<MapMarkersModel>>
}