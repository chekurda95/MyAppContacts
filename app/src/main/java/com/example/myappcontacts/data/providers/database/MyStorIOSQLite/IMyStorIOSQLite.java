package com.example.myappcontacts.data.providers.database.MyStorIOSQLite;

import com.example.myappcontacts.data.dao.contacts.db.ContactsModel;
import com.example.myappcontacts.data.dao.contactsmap.MapMarkersModel;

import java.util.List;
import java.util.UUID;

public interface IMyStorIOSQLite {
    UUID addContact();

    void updateContact(ContactsModel contactsModel);

    ContactsModel getContact(UUID contactId);

    List<ContactsModel> getContactsList();

    void deleteContact(UUID contactId);

    List<MapMarkersModel> getMapMarekersList();

}
