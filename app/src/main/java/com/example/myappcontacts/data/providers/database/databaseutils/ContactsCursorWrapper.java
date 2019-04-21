package com.example.myappcontacts.data.providers.database.databaseutils;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.myappcontacts.data.dao.contacts.db.ContactsModel;

import java.util.UUID;

import static com.example.myappcontacts.data.providers.database.databaseutils.ContactsDbSchema.*;

//Обертка курсора
public class ContactsCursorWrapper extends CursorWrapper {

    ContactsCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public ContactsModel getContactsModel() {
        //Получам данные модели контакта из курсора
        String uuidString = getString(getColumnIndex(ContactsTable.Cols.UUID));
        String firstName = getString(getColumnIndex(ContactsTable.Cols.FIRST_NAME));
        String lastName = getString(getColumnIndex(ContactsTable.Cols.LAST_NAME));
        String companyName = getString(getColumnIndex(ContactsTable.Cols.COMPANY_NAME));
        String telNumber = getString(getColumnIndex(ContactsTable.Cols.TEL_NUMBER));

        //Создаем модель
        ContactsModel contactsModel = new ContactsModel(UUID.fromString(uuidString));
        contactsModel.setFirstName(firstName != null ? firstName : "");
        contactsModel.setLastName(lastName != null ? lastName : "");
        contactsModel.setCompanyName(companyName != null ? companyName : "");
        contactsModel.setTelNumber(telNumber != null ? telNumber : "");

        return contactsModel;
    }
}
