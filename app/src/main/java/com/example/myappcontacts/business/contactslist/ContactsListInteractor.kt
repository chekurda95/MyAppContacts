package com.example.myappcontacts.business.contactslist

import com.example.myappcontacts.data.dao.contacts.db.ContactsModel
import com.example.myappcontacts.data.repositories.contactslist.IContactsListRepository
import java.util.UUID

import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ContactsListInteractor(private val contactsListRepository: IContactsListRepository) : IContactsListInteractor {

    override fun addContact(): Single<UUID> {
        return contactsListRepository.addContact()
    }

    override fun deleteContact(contactId: UUID): Completable {
        return contactsListRepository.deleteContact(contactId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun loadContactsList(): Single<List<ContactsModel>> {
        return contactsListRepository.loadContactsList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}
