package com.example.myappcontacts.presentation.contactslist.presenter

import java.util.UUID

interface IContactsListPresenter {
    fun addContact()

    fun deleteContact(contactId: UUID)

    fun loadContactsList()

    fun onItemSwiped(contactId: UUID)

    fun onContactItemClicked(contactsId: UUID)

    fun onShowContactsMapClicked()
}
