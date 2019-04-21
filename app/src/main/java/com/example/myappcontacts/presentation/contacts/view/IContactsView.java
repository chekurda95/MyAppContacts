package com.example.myappcontacts.presentation.contacts.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.example.myappcontacts.data.dao.contacts.db.ContactsModel;

import java.util.UUID;

public interface IContactsView extends MvpView {

    @StateStrategyType(SkipStrategy.class)
    void updateUI(ContactsModel contactsModel, boolean edit);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void updateContact();
}
