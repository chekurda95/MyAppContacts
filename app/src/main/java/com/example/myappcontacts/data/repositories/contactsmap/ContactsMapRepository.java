package com.example.myappcontacts.data.repositories.contactsmap;

import com.example.myappcontacts.data.dao.contactsmap.MapMarkersModel;
import com.example.myappcontacts.data.providers.database.IDataBaseProvider;

import java.util.List;

import io.reactivex.Single;

public class ContactsMapRepository implements IContactsMapRepository {

    private IDataBaseProvider mDataBaseProvider;

    public ContactsMapRepository(IDataBaseProvider dataBaseProvider) {
        mDataBaseProvider = dataBaseProvider;
    }

    @Override
    public Single<List<MapMarkersModel>> loadMapMarkersList() {
        return mDataBaseProvider.loadMapMarkersList();
    }
}
