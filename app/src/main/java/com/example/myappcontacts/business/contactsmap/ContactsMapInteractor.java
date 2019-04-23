package com.example.myappcontacts.business.contactsmap;

import com.example.myappcontacts.data.dao.contactsmap.MapMarkersModel;
import com.example.myappcontacts.data.repositories.contactsmap.IContactsMapRepository;

import java.util.List;

import io.reactivex.Single;

public class ContactsMapInteractor implements IContactsMapInteractor {

    private IContactsMapRepository mIContactsMapRepository;

    public ContactsMapInteractor(IContactsMapRepository IContactsMapRepository) {
        mIContactsMapRepository = IContactsMapRepository;
    }

    @Override
    public Single<List<MapMarkersModel>> loadMapMarkersList() {
        return mIContactsMapRepository.loadMapMarkersList();
    }
}
