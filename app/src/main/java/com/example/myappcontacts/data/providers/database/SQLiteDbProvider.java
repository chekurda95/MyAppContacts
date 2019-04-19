package com.example.myappcontacts.data.providers.database;

import com.example.myappcontacts.data.dao.contacts.db.ContactsModel;
import com.example.myappcontacts.data.providers.database.MyStorIOSQLite.IMyStorIOSQLite;

import java.util.UUID;

import io.reactivex.Completable;
import io.reactivex.Single;

public class SQLiteDbProvider implements IDataBaseProvider{

    private IMyStorIOSQLite mDataBase;

    public SQLiteDbProvider(IMyStorIOSQLite dataBase){
        mDataBase = dataBase;
    }

    @Override
    public Completable saveContact(ContactsModel contactsModel) {
        return Completable.fromAction(() -> mDataBase.addContact(contactsModel));
    }

    @Override
    public Completable updateContact(ContactsModel contactsModel) {
        return Completable.fromAction(() -> mDataBase.updateContact(contactsModel));
    }

    @Override
    public Single<ContactsModel> loadContact(UUID contactId) {
        return Single.fromCallable(() -> mDataBase.getContact(contactId));
    }

    @Override
    public Completable deleteContact(UUID contactId) {
        return Completable.fromAction(() -> mDataBase.deleteContact(contactId));
    }
}
