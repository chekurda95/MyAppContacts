package com.example.myappcontacts.business.contactslist;

import com.example.myappcontacts.data.dao.contacts.db.ContactsModel;
import com.example.myappcontacts.data.repositories.contactslist.IContactsListRepository;

import java.util.List;
import java.util.UUID;

import io.reactivex.Completable;
import io.reactivex.Single;

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
        return mContactsListRepository.deleteContact(contactId);
    }

    @Override
    public Single<List<ContactsModel>> loadContactsList() {
        return mContactsListRepository.loadContactsList();
    }
}
