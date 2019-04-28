package com.example.myappcontacts.presentation.contacts.view

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.example.myappcontacts.data.dao.contacts.db.ContactsModel

@StateStrategyType(SkipStrategy::class)
interface IContactsView : MvpView {

    fun updateUI(contactsModel: ContactsModel, edit: Boolean)

    fun updatePhoto()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun updateContact()
}
