package com.example.myappcontacts.di.contactsmap

import com.example.myappcontacts.presentation.contactsmap.presenter.ContactsMapPresenter

import dagger.Subcomponent

@Subcomponent(modules = [ContactsMapModule::class])
@ContactsMapScope
interface ContactsMapComponent {

    fun inject(contactsMapPresenter: ContactsMapPresenter)
}
