package com.example.myappcontacts.business.contact

import com.example.myappcontacts.data.dao.contacts.db.ContactsModel
import com.example.myappcontacts.data.repositories.contacts.IContactsRepository
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

class ContactsInteractor(private val contactsRepository: IContactsRepository): IContactsInteractor{

    override fun loadContact(contactId: UUID): Single<ContactsModel> {
        return contactsRepository.loadContact(contactId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun updateContact(contactsModel: ContactsModel): Completable {
        return contactsRepository.updateContact(contactsModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }


}