package com.example.myappcontacts.data.providers.database.MyStorIOSQLite;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.example.myappcontacts.data.dao.contacts.db.ContactsModel;
import com.example.myappcontacts.data.providers.database.databaseutils.ContactsBaseHelper;
import com.example.myappcontacts.data.providers.database.databaseutils.ContactsCursorWrapper;
import com.example.myappcontacts.data.providers.database.databaseutils.Queries;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.example.myappcontacts.data.providers.database.databaseutils.ContactsDbSchema.ContactsTable;

public class MyStorIOSQLite implements IMyStorIOSQLite {

    private SQLiteDatabase mDb;

    public MyStorIOSQLite(ContactsBaseHelper contactsBaseHelper) {
        mDb = contactsBaseHelper.getWritableDatabase();
    }

    @Override
    public UUID addContact() {
        ContactsModel contactsModel = new ContactsModel();
        ContentValues values = Queries.getContentValues(contactsModel);
        mDb.insert(ContactsTable.NAME, null, values);
        return contactsModel.getContactId();
    }

    @Override
    public void updateContact(ContactsModel contactsModel) {
        String uuidString = contactsModel.getContactId().toString();
        ContentValues values = Queries.getContentValues(contactsModel);
        mDb.update(ContactsTable.NAME,
                values,
                ContactsTable.Cols.UUID + " = ?",
                new String[]{uuidString});
    }

    @Override
    public ContactsModel getContact(UUID contactId) {
        String uuidString = contactId.toString();
        ContactsCursorWrapper cursor = Queries.queryContacts(mDb,
                ContactsTable.Cols.UUID + " = ?",
                new String[]{uuidString});
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getContactsModel();
        } finally {
            cursor.close();
        }
    }

    @Override
    public List<ContactsModel> getContactsList() {
        ContactsCursorWrapper cursor = Queries.queryContacts(mDb,
                null,
                null);
        List<ContactsModel> contactsList = new ArrayList<>();
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                contactsList.add(cursor.getContactsModel());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return contactsList;
    }

    @Override
    public void deleteContact(UUID contactId) {
        String uuidString = contactId.toString();
        mDb.delete(ContactsTable.NAME,
                ContactsTable.Cols.UUID + " = ?",
                new String[]{uuidString});
    }

}
