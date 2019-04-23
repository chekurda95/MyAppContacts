package com.example.myappcontacts.business.contactsmap;

import com.example.myappcontacts.data.dao.contactsmap.MapMarkersModel;

import java.util.List;

import io.reactivex.Single;

public interface IContactsMapInteractor {

    Single<List<MapMarkersModel>> loadMapMarkersList();
}
