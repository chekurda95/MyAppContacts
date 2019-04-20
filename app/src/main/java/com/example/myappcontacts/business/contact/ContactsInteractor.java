package com.example.myappcontacts.business.contact;

import com.example.myappcontacts.data.dao.contacts.db.ContactsModel;
import com.example.myappcontacts.data.repositories.contacts.IContactsRepository;

import java.util.UUID;

import io.reactivex.Completable;
import io.reactivex.Single;

public class ContactsInteractor implements IContactsInteractor {

    private IContactsRepository mContactsRepository;

    public ContactsInteractor(IContactsRepository contactsRepository) {
        mContactsRepository = contactsRepository;
    }

    @Override
    public Single<ContactsModel> loadContact(UUID contactId) {
        return mContactsRepository.loadContact(contactId);
    }

    @Override
    public Completable updateContact(ContactsModel contactsModel) {
        return mContactsRepository.updateContact(contactsModel);
    }
}
