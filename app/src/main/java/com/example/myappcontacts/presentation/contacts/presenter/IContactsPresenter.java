package com.example.myappcontacts.presentation.contacts.presenter;

import com.example.myappcontacts.data.dao.contacts.db.ContactsModel;

import java.util.UUID;

public interface IContactsPresenter {
    void loadContact(UUID contactId);

    void updateContact(ContactsModel contactsModel);

    void onEditSaveButtonClicked();

    void onPhotoImageClicked();

    void photoUriLoaded(String photoUri);
}
