package com.example.myappcontacts.data.repositories.contacts;

import com.example.myappcontacts.data.dao.contacts.db.ContactsModel;

import java.util.UUID;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface IContactsRepository {
    Single<ContactsModel> loadContact(UUID contactId);
    Completable updateContact(ContactsModel contactsModel);
}
