package com.example.myappcontacts.data.repositories.contacts;

import com.example.myappcontacts.data.dao.contacts.db.ContactsModel;
import com.example.myappcontacts.data.providers.database.IDataBaseProvider;

import java.util.UUID;

import io.reactivex.Completable;
import io.reactivex.Single;

public class ContactsRepository implements IContactsRepository {

    private IDataBaseProvider mDataBaseProvider;

    public ContactsRepository(IDataBaseProvider sqLiteDbProvider){
        mDataBaseProvider = sqLiteDbProvider;
    }

    @Override
    public Single<ContactsModel> loadContact(UUID contactId) {
        return mDataBaseProvider.loadContact(contactId);
    }

    @Override
    public Completable updateContact(ContactsModel contactsModel) {
        return mDataBaseProvider.updateContact(contactsModel);
    }
}
