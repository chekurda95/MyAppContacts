package com.example.myappcontacts.data.repositories.contactslist;

import com.example.myappcontacts.data.dao.contacts.db.ContactsModel;
import com.example.myappcontacts.data.providers.database.IDataBaseProvider;

import java.util.List;
import java.util.UUID;

import io.reactivex.Completable;
import io.reactivex.Single;

public class ContactsListRepository implements IContactsListRepository {

    private IDataBaseProvider mDataBaseProvider;

    public ContactsListRepository(IDataBaseProvider dataBaseProvider) {
        mDataBaseProvider = dataBaseProvider;
    }

    @Override
    public Single<UUID> addContact() {
        return mDataBaseProvider.addContact();
    }

    @Override
    public Completable deleteContact(UUID contactId) {
        return mDataBaseProvider.deleteContact(contactId);
    }

    @Override
    public Single<List<ContactsModel>> loadContactsList() {
        return mDataBaseProvider.loadContactsList();
    }
}
