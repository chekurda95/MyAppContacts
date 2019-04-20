package com.example.myappcontacts.presentation.contactslist.presenter;

import android.content.Intent;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.myappcontacts.App;
import com.example.myappcontacts.business.contact.IContactsInteractor;
import com.example.myappcontacts.business.contactslist.IContactsListInteractor;
import com.example.myappcontacts.data.dao.contacts.db.ContactsModel;
import com.example.myappcontacts.di.contactslist.ContactsListModule;
import com.example.myappcontacts.presentation.contacthost.view.ContactsHostActivity;
import com.example.myappcontacts.presentation.contactslist.view.IContactsListView;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class ContactsListPresenter extends MvpPresenter<IContactsListView> implements IContactsListPresenter {
    private static final String TAG = "MY_TAG";

    @Inject
    IContactsListInteractor mContactsListInteractor;

    public ContactsListPresenter(){
        App.get().plusContactsListComponent(new ContactsListModule()).inject(this);
    }


    @Override
    public void addContact() {
        mContactsListInteractor.addContact()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onAddSuccess, this::onError);
    }

    private void onAddSuccess(UUID contactId){
        ContactsHostActivity.goToContact(contactId);
    }

    private void onError(Throwable throwable){
        Log.e(TAG, getClass().getSimpleName() + "onError " + throwable);
    }

    @Override
    public void deleteContact(UUID contactId) {
        mContactsListInteractor.deleteContact(contactId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onDeleteSuccess, this::onError);
    }

    private void onDeleteSuccess(){
        Log.e(TAG, getClass().getSimpleName() + "contact deleted");
    }

    @Override
    public void loadContactsList() {
        mContactsListInteractor.loadContactsList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onLoadSuccess, this::onError);
    }

    private void onLoadSuccess(List<ContactsModel> contactsList){
        Log.i("MY_TAG", getClass().getSimpleName() + " onLoadSuccess сработал " + contactsList.size());
        getViewState().updateContactsList(contactsList);
    }

    @Override
    public void onContactItemClicked(UUID contactId){
        ContactsHostActivity.goToContact(contactId);
    }
}
