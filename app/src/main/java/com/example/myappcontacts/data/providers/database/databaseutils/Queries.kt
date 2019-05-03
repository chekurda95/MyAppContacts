package com.example.myappcontacts.data.providers.database.databaseutils

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.myappcontacts.data.dao.contacts.db.ContactsModel
import com.example.myappcontacts.data.providers.database.databaseutils.ContactsDBSchema.ContactsTable

class Queries {
    companion object {
        fun queryContacts(db: SQLiteDatabase, whereClause: String?, whereArgs: Array<String>?): ContactsCursorWrapper {
            val cursor: Cursor = db.query(ContactsTable.NAME,
                    null,
                    whereClause,
                    whereArgs,
                    null,
                    null,
                    null)
            return ContactsCursorWrapper(cursor)
        }

        fun getContentValues(contactsModel: ContactsModel): ContentValues {
            val contentValues = ContentValues()
            contentValues.put(ContactsTable.Cols.UUID, contactsModel.contactId.toString())
            contentValues.put(ContactsTable.Cols.FIRST_NAME, contactsModel.firstName)
            contentValues.put(ContactsTable.Cols.LAST_NAME, contactsModel.lastName)
            contentValues.put(ContactsTable.Cols.COMPANY_NAME, contactsModel.companyName)
            contentValues.put(ContactsTable.Cols.TEL_NUMBER, contactsModel.telNumber)
            contentValues.put(ContactsTable.Cols.PHOTO_URI, contactsModel.photoUri)
            contentValues.put(ContactsTable.Cols.ADDRESS, contactsModel.address)
            return contentValues
        }
    }
}