package com.example.myappcontacts.data.repositories.contactslist;

import com.example.myappcontacts.data.dao.contacts.db.ContactsModel;

import java.util.List;
import java.util.UUID;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface IContactsListRepository {
    Single<UUID> addContact();
    Completable deleteContact(UUID contactId);
    Single<List<ContactsModel>> loadContactsList();
}
