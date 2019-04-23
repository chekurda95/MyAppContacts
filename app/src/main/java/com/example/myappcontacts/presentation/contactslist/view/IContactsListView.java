package com.example.myappcontacts.presentation.contactslist.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.example.myappcontacts.data.dao.contacts.db.ContactsModel;

import java.util.List;
import java.util.UUID;

@StateStrategyType(SkipStrategy.class)
public interface IContactsListView extends MvpView {

    void addContact();

    void deleteContact(UUID contactId);

    void updateContactsList(List<ContactsModel> contactsList);

    void openContact(UUID contactId);

    void openMap();
}
