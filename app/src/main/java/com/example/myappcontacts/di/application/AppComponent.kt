package com.example.myappcontacts.di.application

import com.example.myappcontacts.di.contacts.ContactsComponent
import com.example.myappcontacts.di.contacts.ContactsModule
import com.example.myappcontacts.di.contactslist.ContactsListComponent
import com.example.myappcontacts.di.contactslist.ContactsListModule
import com.example.myappcontacts.di.contactsmap.ContactsMapComponent
import com.example.myappcontacts.di.contactsmap.ContactsMapModule
import com.example.myappcontacts.presentation.contacthost.view.ContactsHostActivity
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class])
@Singleton
interface AppComponent {
    fun inject(contactsHostActivity: ContactsHostActivity)

    fun plus(contactsModule: ContactsModule): ContactsComponent

    fun plus(contactsListModule: ContactsListModule): ContactsListComponent

    fun plus(contactsMapModule: ContactsMapModule): ContactsMapComponent
}