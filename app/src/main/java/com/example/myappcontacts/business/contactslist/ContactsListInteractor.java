package com.example.myappcontacts.business.contactslist;

import com.example.myappcontacts.data.dao.contacts.db.ContactsModel;
import com.example.myappcontacts.data.repositories.contactslist.IContactsListRepository;

import java.util.List;
import java.util.UUID;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ContactsListInteractor implements IContactsListInteractor {

    private IContactsListRepository mContactsListRepository;

    public ContactsListInteractor(IContactsListRepository contactsListRepository) {
        mContactsListRepository = contactsListRepository;
    }

    @Override
    public Single<UUID> addContact() {
        return mContactsListRepository.addContact();
    }

    @Override
    public Completable deleteContact(UUID contactId) {
        return mContactsListRepository.deleteContact(contactId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<List<ContactsModel>> loadContactsList() {
        return mContactsListRepository.loadContactsList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
