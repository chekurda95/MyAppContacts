package com.example.myappcontacts.data.providers.database;

import com.example.myappcontacts.data.dao.contacts.db.ContactsModel;

import java.util.List;
import java.util.UUID;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface IDataBaseProvider {

    Single<UUID> addContact();

    Completable updateContact(ContactsModel contactsModel);

    Single<ContactsModel> loadContact(UUID contactId);

    Single<List<ContactsModel>> loadContactsList();

    Completable deleteContact(UUID contactId);

}
