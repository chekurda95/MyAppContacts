package com.example.myappcontacts.business.contactsmap

import com.example.myappcontacts.data.dao.contactsmap.db.MapMarkersModel
import com.example.myappcontacts.data.repositories.contactsmap.IContactsMapRepository

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ContactsMapInteractor(private val contactsMapRepository: IContactsMapRepository) : IContactsMapInteractor {

    override fun loadMapMarkersList(): Single<List<MapMarkersModel>> {
        return contactsMapRepository
                .loadMapMarkersList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}
