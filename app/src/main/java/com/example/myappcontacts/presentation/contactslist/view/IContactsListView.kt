package com.example.myappcontacts.presentation.contactslist.view

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.example.myappcontacts.data.dao.contacts.db.ContactsModel
import java.util.UUID

@StateStrategyType(SkipStrategy::class)
interface IContactsListView : MvpView {

    fun addContact()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun deleteContact(contactId: UUID)

    fun updateContactsList(contactsList: List<ContactsModel>)

    fun openContact(contactId: UUID)

    fun openMap()
}
