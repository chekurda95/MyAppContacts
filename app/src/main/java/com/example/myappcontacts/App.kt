package com.example.myappcontacts

import android.app.Application
import com.example.myappcontacts.di.application.AppModule
import com.example.myappcontacts.di.application.DaggerAppComponent
import com.example.myappcontacts.di.contacts.ContactsComponent
import com.example.myappcontacts.di.contacts.ContactsModule
import com.example.myappcontacts.di.contactslist.ContactsListComponent
import com.example.myappcontacts.di.contactslist.ContactsListModule
import com.example.myappcontacts.di.contactsmap.ContactsMapComponent
import com.example.myappcontacts.di.contactsmap.ContactsMapModule
import com.facebook.drawee.backends.pipeline.Fresco

class App: Application(){

    companion object {
        private lateinit var app: App
        fun get(): App {
            return app
        }
    }

    init {
        app = this
    }

    private val appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    private var contactsComponent: ContactsComponent? = null
    private var contactsListComponent: ContactsListComponent?= null
    private var contactsMapComponent: ContactsMapComponent? = null

    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this)
    }

    fun plusContactsModule(contactsModule: ContactsModule): ContactsComponent {
        if(contactsComponent == null) {
            contactsComponent = appComponent.plus(contactsModule)
        }
        return contactsComponent!!
    }

    fun plusContactsListModule(contactsListModule: ContactsListModule): ContactsListComponent {
        if(contactsListComponent == null) {
            contactsListComponent = appComponent.plus(contactsListModule)
        }
        return contactsListComponent!!
    }

    fun plusContactsMapModule(contactsMapModule: ContactsMapModule): ContactsMapComponent {
        if(contactsMapComponent == null) {
            contactsMapComponent = appComponent.plus(contactsMapModule)
        }
        return contactsMapComponent!!
    }

    fun clearContactsComponent(){contactsComponent = null}

    fun clearContactsListComponent(){contactsListComponent = null}

    fun clearContactsMapComponent(){contactsMapComponent = null}

}