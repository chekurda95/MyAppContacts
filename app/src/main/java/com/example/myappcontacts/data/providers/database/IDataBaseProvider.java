package com.example.myappcontacts.data.providers.database;

import com.example.myappcontacts.data.dao.contacts.db.ContactsModel;

import java.util.UUID;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface IDataBaseProvider {

    Completable saveContact(ContactsModel contactsModel);

    Completable updateContact(ContactsModel contactsModel);

    Single<ContactsModel> loadContact(UUID contactId);

    Completable deleteContact(UUID contactId);
}
