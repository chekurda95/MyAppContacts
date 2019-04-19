package com.example.myappcontacts.data.providers.database.MyStorIOSQLite;

import com.example.myappcontacts.data.dao.contacts.db.ContactsModel;

import java.util.UUID;

public interface IMyStorIOSQLite {
    void addContact(ContactsModel contactsModel);
    void updateContact(ContactsModel contactsModel);
    ContactsModel getContact(UUID contactId);
    void deleteContact(UUID contactId);
}
