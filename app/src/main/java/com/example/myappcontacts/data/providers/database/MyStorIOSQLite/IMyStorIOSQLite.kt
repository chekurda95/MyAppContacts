package com.example.myappcontacts.data.providers.database.MyStorIOSQLite

import com.example.myappcontacts.data.dao.contacts.db.ContactsModel
import com.example.myappcontacts.data.dao.contactsmap.MapMarkersModel
import java.util.*

interface IMyStorIOSQLite {

    fun addContact(): UUID

    fun updateContact(contactsModel: ContactsModel)

    fun getContact(contactId: UUID): ContactsModel?

    fun getContactsList(): List<ContactsModel>

    fun deleteContact(contactId: UUID)

    fun getMapMarkersList(): List<MapMarkersModel>
}