package com.example.myappcontacts.presentation.contacts.view;

import com.arellomobile.mvp.MvpView;
import com.example.myappcontacts.data.dao.contacts.db.ContactsModel;

public interface IContactsView extends MvpView {
    void showContact(ContactsModel contactsModel);
    void updateContact();
}
