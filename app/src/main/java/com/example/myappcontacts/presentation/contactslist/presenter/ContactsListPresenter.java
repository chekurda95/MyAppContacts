package com.example.myappcontacts.presentation.contactslist.presenter;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.myappcontacts.App;
import com.example.myappcontacts.business.contactslist.IContactsListInteractor;
import com.example.myappcontacts.data.dao.contacts.db.ContactsModel;
import com.example.myappcontacts.di.contactslist.ContactsListModule;
import com.example.myappcontacts.presentation.contactslist.view.IContactsListView;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

@InjectViewState
public class ContactsListPresenter extends MvpPresenter<IContactsListView> implements IContactsListPresenter {
    private static final String TAG = "MY_TAG";

    private CompositeDisposable mDisposer = new CompositeDisposable();

    @Inject
    IContactsListInteractor mContactsListInteractor;

    public ContactsListPresenter() {
        App.get().plusContactsListModule(new ContactsListModule()).inject(this);
    }


    @Override
    public void addContact() {
        mDisposer.add(mContactsListInteractor.addContact()
                .subscribe(this::onAddSuccess, this::onError));
    }

    private void onAddSuccess(UUID contactId) {
        getViewState().openContact(contactId);
    }

    private void onError(Throwable throwable) {
        Log.e(TAG, getClass().getSimpleName() + " onError " + throwable);
    }

    @Override
    public void deleteContact(UUID contactId) {
        mDisposer.add(mContactsListInteractor.deleteContact(contactId)
                .subscribe(this::onDeleteSuccess, this::onError));
    }

    private void onDeleteSuccess() {
        Log.i(TAG, getClass().getSimpleName() + " contact deleted");
    }

    @Override
    public void loadContactsList() {
        mDisposer.add(mContactsListInteractor.loadContactsList()
                .subscribe(this::onLoadSuccess, this::onError));
    }

    private void onLoadSuccess(List<ContactsModel> contactsList) {
        Log.i("MY_TAG", getClass().getSimpleName() + " onLoadSuccess сработал " + contactsList.size());
        getViewState().updateContactsList(contactsList);
    }

    @Override
    public void onItemSwiped(UUID contactId) {
        getViewState().deleteContact(contactId);
    }

    @Override
    public void onContactItemClicked(UUID contactId) {
        getViewState().openContact(contactId);
    }

    @Override
    public void onShowContactsMapClicked() {
        getViewState().openMap();
    }

    @Override
    public void onDestroy() {
        mDisposer.dispose();
        App.get().clearContactsListComponent();
        super.onDestroy();
    }
}
