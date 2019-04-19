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
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class ContactsPresenter extends MvpPresenter<IContactsView> implements IContactsPresenter {

    private static final String TAG = "MY_TAG";

    @Inject
    IContactsInteractor mContactsInteractor;

    private ContactsModel mContactsModel;

    public ContactsPresenter(){
        App.get().plusContactsComponent(new ContactsModule()).inject(this);
    }

    @Override
    public void loadContact(UUID contactId) {
        mContactsInteractor.loadContact(contactId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onSuccess, this::onError);
    }

    private void onError(Throwable throwable) {
        Log.e(TAG, getClass().getSimpleName() + " onError" + throwable);
    }

    private void onSuccess(ContactsModel contactsModel){
        if(getViewState()!=null) {
            mContactsModel = contactsModel;
            getViewState().showContact(contactsModel);
        }
    }

    @Override
    public void updateContact(ContactsModel contactsModel) {
        mContactsInteractor.updateContact(contactsModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onUpdate, this::onError);
    }

    private void onUpdate(){
        Log.e(TAG, mContactsModel.getLastName() + " was updated successful");
    }


    @Override
    public void onBackToContactsList() {
    }

    @Override
    public void onDestroy() {
        App.get().clearContactsComponent();
        super.onDestroy();
    }
}
