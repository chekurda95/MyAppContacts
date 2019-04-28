package com.example.myappcontacts.di.contacts

import com.example.myappcontacts.presentation.contacts.presenter.ContactsPresenter
import dagger.Subcomponent

@Subcomponent(modules = [ContactsModule::class])
@ContactsScope
interface ContactsComponent{
    fun inject(contactsPresenter: ContactsPresenter)
}