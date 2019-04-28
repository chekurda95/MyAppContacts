package com.example.myappcontacts.presentation.contacts.presenter

import com.example.myappcontacts.data.dao.contacts.db.ContactsModel
import java.util.*

interface IContactsPresenter {

    fun loadContact(contactId: UUID)

    fun updateContact(contactsModel: ContactsModel)

    fun onEditSaveButtonClicked()

    fun onPhotoImageClicked()

    fun photoUriLoaded(photoUri: String)
}