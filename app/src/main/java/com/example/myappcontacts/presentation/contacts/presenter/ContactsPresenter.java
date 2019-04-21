package com.example.myappcontacts.presentation.contacts.presenter;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.myappcontacts.App;
import com.example.myappcontacts.business.contact.IContactsInteractor;
import com.example.myappcontacts.data.dao.contacts.db.ContactsModel;
import com.example.myappcontacts.di.contacts.ContactsModule;
import com.example.myappcontacts.presentation.contacts.view.IContactsView;

import java.util.UUID;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class ContactsPresenter extends MvpPresenter<IContactsView> implements IContactsPresenter {

    private static final String TAG = "MY_TAG";

    @Inject
    IContactsInteractor mContactsInteractor;

    private CompositeDisposable mDisposer = new CompositeDisposable();
    private boolean mSaveButtonActive;
    private ContactsModel mContactsModel;

    public ContactsPresenter() {
        App.get().plusContactsComponent(new ContactsModule()).inject(this);
    }

    @Override
    public void loadContact(UUID contactId) {
        if (mContactsModel == null) {
            Log.i("MY_TAG2", "loadContact сработал");
            mDisposer.add(mContactsInteractor.loadContact(contactId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::onSuccess, this::onError));
        } else {
            getViewState().updateUI(mContactsModel, mSaveButtonActive);
        }
    }

    private void onError(Throwable throwable) {
        Log.e(TAG, getClass().getSimpleName() + " onError" + throwable);
    }

    private void onSuccess(ContactsModel contactsModel) {
        if (contactsModel.getTelNumber().equals("")) {
            mSaveButtonActive = true;
            getViewState().updateUI(contactsModel, true);
        } else {
            mSaveButtonActive = false;
            getViewState().updateUI(contactsModel, false);
        }
        mContactsModel = contactsModel;
    }

    @Override
    public void updateContact(ContactsModel contactsModel) {
        mContactsModel = contactsModel;
        mSaveButtonActive = !mSaveButtonActive;
        getViewState().updateUI(contactsModel, mSaveButtonActive);
        mDisposer.add(mContactsInteractor.updateContact(contactsModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onUpdate, this::onError));
    }

    private void onUpdate() {
        Log.i(TAG, " - контакт был успешно обновлен");
    }

    @Override
    public void onEditSaveButtonClicked() {
        if (mSaveButtonActive) {
            getViewState().updateContact();
        } else {
            mSaveButtonActive = !mSaveButtonActive;
            getViewState().updateUI(mContactsModel, mSaveButtonActive);
        }

    }

    @Override
    public void onDestroy() {
        mDisposer.dispose();
        App.get().clearContactsComponent();
        super.onDestroy();
    }
}
