package com.example.myappcontacts.data.repositories.contactsmap;

import com.example.myappcontacts.data.dao.contactsmap.MapMarkersModel;

import java.util.List;

import io.reactivex.Single;

public interface IContactsMapRepository {

    Single<List<MapMarkersModel>> loadMapMarkersList();
}
