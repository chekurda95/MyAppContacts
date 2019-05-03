package com.example.myappcontacts.presentation.contactslist.presenter

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.myappcontacts.App
import com.example.myappcontacts.business.contactslist.IContactsListInteractor
import com.example.myappcontacts.data.dao.contacts.db.ContactsModel
import com.example.myappcontacts.di.contactslist.ContactsListModule
import com.example.myappcontacts.presentation.contactslist.view.IContactsListView
import io.reactivex.disposables.CompositeDisposable
import java.util.*
import javax.inject.Inject

internal const val TAG = "TEST"

@InjectViewState
class ContactsListPresenter : MvpPresenter<IContactsListView>(), IContactsListPresenter {

    @Inject
    lateinit var contactsListInteractor: IContactsListInteractor

    private val disposer = CompositeDisposable()

    init {
        App.get().plusContactsListModule(ContactsListModule()).inject(this)
    }

    override fun addContact() {
        disposer.add(contactsListInteractor
                .addContact()
                .subscribe({ onAddSuccess(it) }, { onError(it) }))
    }

    private fun onAddSuccess(contactId: UUID) = viewState.openContact(contactId)

    private fun onError(throwable: Throwable) =
            Log.e(TAG, this::class.simpleName + " onError " + throwable)

    override fun deleteContact(contactId: UUID) {
        disposer.add(contactsListInteractor
                .deleteContact(contactId)
                .subscribe({ onDeleteSuccess() }, { onError(it) }))
    }

    private fun onDeleteSuccess() =
            Log.i(TAG, this::class.simpleName + " contact deleted")

    override fun loadContactsList() {
        disposer.add(contactsListInteractor
                .loadContactsList()
                .subscribe({ onLoadSuccess(it) }, { onError(it) }))
    }

    private fun onLoadSuccess(contactsList: List<ContactsModel>) {
        viewState.updateContactsList(contactsList)
        Log.i(TAG, "${this::class.simpleName}: onLoadSuccess сработал, загружено ${contactsList.size} контактов")
    }

    override fun onItemSwiped(contactId: UUID) {
        viewState.deleteContact(contactId)
    }

    override fun onContactItemClicked(contactsId: UUID) {
        viewState.openContact(contactsId)
    }

    override fun onShowContactsMapClicked() {
        viewState.openMap()
    }

    override fun onDestroy() {
        disposer.dispose()
        App.get().clearContactsListComponent()
        super.onDestroy()
    }
}