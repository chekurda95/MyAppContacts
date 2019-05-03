package com.example.myappcontacts.presentation.contacts.presenter

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.myappcontacts.App
import com.example.myappcontacts.business.contact.IContactsInteractor
import com.example.myappcontacts.data.dao.contacts.db.ContactsModel
import com.example.myappcontacts.di.contacts.ContactsModule
import com.example.myappcontacts.presentation.contacts.view.IContactsView
import io.reactivex.disposables.CompositeDisposable
import java.util.*
import javax.inject.Inject

internal const val TAG = "TEST"

@InjectViewState
class ContactsPresenter : MvpPresenter<IContactsView>(), IContactsPresenter {

    @Inject
    lateinit var contactsInteractor: IContactsInteractor

    private var saveButtonActive = false
    lateinit var contactsModel: ContactsModel

    private val disposer = CompositeDisposable()

    init {
        App.get().plusContactsModule(ContactsModule()).inject(this)
    }

    override fun loadContact(contactId: UUID) {
        if (::contactsModel.isInitialized) {
            Log.i(TAG, " loadContact вызван, котанкт уже загружен")
            viewState.updateUI(contactsModel, saveButtonActive)
        } else {
            Log.i(TAG, " loadContact вызван, котанкт загружается")
            disposer.add(contactsInteractor
                    .loadContact(contactId)
                    .subscribe({ onSuccess(it) }) { onError(it) })
        }
    }

    private fun onSuccess(contactsModel: ContactsModel) {
        Log.i(TAG, " onSuccess контакт загружен")
        this.contactsModel = contactsModel
        if (contactsModel.telNumber.isBlank()) saveButtonActive = true
        viewState.updateUI(contactsModel, saveButtonActive)
    }

    private fun onError(throwable: Throwable) =
            Log.e(TAG, this::class.simpleName + " onError " + throwable)

    private fun onUpdate() =
            Log.i(TAG, " - контакт был успешно обновлен")

    override fun updateContact(contactsModel: ContactsModel) {
        saveButtonActive = !saveButtonActive

        viewState.updateUI(contactsModel, saveButtonActive)
        disposer.add(contactsInteractor
                .updateContact(contactsModel)
                .subscribe({ onUpdate() }, { onError(it) }))
    }

    override fun saveContactsModel(contactsModel: ContactsModel) {
        if (saveButtonActive) {
            contactsModel.photoUri = this.contactsModel.photoUri
            this.contactsModel = contactsModel
        }
    }

    override fun onEditSaveButtonClicked() {
        if (saveButtonActive) {
            viewState.updateContact()
        } else {
            saveButtonActive = !saveButtonActive
            viewState.updateUI(contactsModel, saveButtonActive)
        }
    }

    override fun onPhotoImageClicked() {
        if (saveButtonActive) viewState.updatePhoto()
    }

    override fun photoUriLoaded(photoUri: String) {
        contactsModel.photoUri = photoUri
    }

    override fun onDestroy() {
        disposer.dispose()
        App.get().clearContactsComponent()
        super.onDestroy()
    }
}