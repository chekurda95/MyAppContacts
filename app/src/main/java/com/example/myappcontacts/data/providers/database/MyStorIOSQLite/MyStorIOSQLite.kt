package com.example.myappcontacts.data.providers.database.MyStorIOSQLite

import com.example.myappcontacts.data.dao.contacts.db.ContactsModel
import com.example.myappcontacts.data.dao.contactsmap.MapMarkersModel
import com.example.myappcontacts.data.providers.database.databaseutils.ContactsBaseHelper
import com.example.myappcontacts.data.providers.database.databaseutils.ContactsDBSchema.ContactsTable
import com.example.myappcontacts.data.providers.database.databaseutils.Queries
import java.util.*

class MyStorIOSQLite(contactsBaseHelper: ContactsBaseHelper) : IMyStorIOSQLite {
    private val db = contactsBaseHelper.writableDatabase

    override fun addContact(): UUID {
        val contactsModel = ContactsModel()
        val values = Queries.getContentValues(contactsModel)
        db.insert(ContactsTable.NAME, null, values)
        return contactsModel.contactId
    }

    override fun updateContact(contactsModel: ContactsModel) {
        val uuidString = contactsModel.contactId.toString()
        val values = Queries.getContentValues(contactsModel)
        db.update(ContactsTable.NAME,
                values,
                ContactsTable.Cols.UUID + " = ?",
                arrayOf(uuidString))
    }

    override fun getContact(contactId: UUID): ContactsModel? {
        val uuidString = contactId.toString()
        val cursor = Queries.queryContacts(db,
                ContactsTable.Cols.UUID + " = ?",
                arrayOf(uuidString))
        cursor.use { cursor ->
            if (cursor.count == 0) return null
            cursor.moveToFirst()
            return cursor.getContactsModel()
        }
    }

    override fun getContactsList(): List<ContactsModel> {
        val cursor = Queries.queryContacts(db,
                null,
                null)
        val contactsList = ArrayList<ContactsModel>()
        cursor.use { cursor ->
            if (cursor.count == 0) return contactsList
            cursor.moveToFirst()
            while (!cursor.isAfterLast) {
                contactsList.add(cursor.getContactsModel())
                cursor.moveToNext()
            }
        }
        return contactsList
    }

    override fun deleteContact(contactId: UUID) {
        val uuidString = contactId.toString()
        db.delete(ContactsTable.NAME,
                ContactsTable.Cols.UUID + " = ?",
                arrayOf(uuidString))
    }

    override fun getMapMarkersList(): List<MapMarkersModel> {
        val cursor = Queries.queryContacts(db,
                null,
                null)
        val mapMarkersList = ArrayList<MapMarkersModel>()
        cursor.use { cursor ->
            if (cursor.count == 0) return mapMarkersList
            cursor.moveToFirst()
            while (!cursor.isAfterLast) {
                val mapMarkersModel = cursor.getMapMarkersModel()
                when (mapMarkersModel) {
                    null -> cursor.moveToNext()
                    else -> {
                        mapMarkersList.add(mapMarkersModel)
                        cursor.moveToNext()
                    }
                }
            }
        }
        return mapMarkersList
    }
}