package com.example.myappcontacts.presentation.contactslist.presenter;

import java.util.UUID;

public interface IContactsListPresenter {
    void addContact();

    void deleteContact(UUID contactId);

    void loadContactsList();

    void onContactItemClicked(UUID contactsId);

    void onShowContactsMapClicked();
}
