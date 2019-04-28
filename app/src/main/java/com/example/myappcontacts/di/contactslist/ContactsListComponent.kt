package com.example.myappcontacts.di.contactslist

import com.example.myappcontacts.presentation.contactslist.presenter.ContactsListPresenter

import dagger.Subcomponent

@Subcomponent(modules = [ContactsListModule::class])
@ContactsListScope
interface ContactsListComponent {
    fun inject(contactsListPresenter: ContactsListPresenter)
}
