package com.example.myappcontacts.business.contactsmap;

import com.example.myappcontacts.data.dao.contactsmap.MapMarkersModel;
import com.example.myappcontacts.data.repositories.contactsmap.IContactsMapRepository;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ContactsMapInteractor implements IContactsMapInteractor {

    private IContactsMapRepository mIContactsMapRepository;

    public ContactsMapInteractor(IContactsMapRepository IContactsMapRepository) {
        mIContactsMapRepository = IContactsMapRepository;
    }

    @Override
    public Single<List<MapMarkersModel>> loadMapMarkersList() {
        return mIContactsMapRepository.loadMapMarkersList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
