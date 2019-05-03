package com.example.myappcontacts.data.providers.database.databaseutils

import android.database.Cursor
import android.database.CursorWrapper
import com.example.myappcontacts.data.dao.contacts.db.ContactsModel
import com.example.myappcontacts.data.dao.contactsmap.db.MapMarkersModel
import com.example.myappcontacts.data.providers.database.databaseutils.ContactsDBSchema.*
import java.util.*

//Обертка курсора
class ContactsCursorWrapper(cursor: Cursor) : CursorWrapper(cursor) {
    //Получам данные модели контакта из курсора
    fun getContactsModel(): ContactsModel {
        return ContactsModel(
                contactId = UUID.fromString(getString(getColumnIndex(ContactsTable.Cols.UUID))),
                firstName = getString(getColumnIndex(ContactsTable.Cols.FIRST_NAME)) ?: "",
                lastName = getString(getColumnIndex(ContactsTable.Cols.LAST_NAME)) ?: "",
                companyName = getString(getColumnIndex(ContactsTable.Cols.COMPANY_NAME)) ?: "",
                telNumber = getString(getColumnIndex(ContactsTable.Cols.TEL_NUMBER)) ?: "",
                photoUri = getString(getColumnIndex(ContactsTable.Cols.PHOTO_URI)) ?: "",
                address = getString(getColumnIndex(ContactsTable.Cols.ADDRESS)) ?: "")
    }

    fun getMapMarkersModel(): MapMarkersModel? {
        val address = getString(getColumnIndex(ContactsTable.Cols.ADDRESS))
        if (address == "") return null

        return MapMarkersModel(
                contactId = UUID.fromString(getString(getColumnIndex(ContactsTable.Cols.UUID))),
                name = "${getString(getColumnIndex(ContactsTable.Cols.LAST_NAME))
                        ?: ""} ${getString(getColumnIndex(ContactsTable.Cols.FIRST_NAME))
                        ?: ""}".trim(),
                photoUri = getString(getColumnIndex(ContactsTable.Cols.PHOTO_URI)) ?: "",
                address = address)
    }
}