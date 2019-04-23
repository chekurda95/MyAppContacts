package com.example.myappcontacts.presentation.contactsmap.presenter;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.myappcontacts.App;
import com.example.myappcontacts.business.contactsmap.IContactsMapInteractor;
import com.example.myappcontacts.data.dao.contactsmap.MapMarkersModel;
import com.example.myappcontacts.di.contactsmap.ContactsMapModule;
import com.example.myappcontacts.presentation.contactsmap.view.IContactsMapView;
import com.google.android.gms.maps.model.Marker;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

@InjectViewState
public class ContactsMapPresenter extends MvpPresenter<IContactsMapView> implements IContactsMapPresenter {

    private CompositeDisposable mDisposer = new CompositeDisposable();
    private Map<Marker, UUID> mMarkersMap;

    @Inject
    IContactsMapInteractor mIContactsMapInteractor;

    public ContactsMapPresenter() {
        App.get().plusContactsMapModule(new ContactsMapModule()).inject(this);
    }

    @Override
    public void init() {
        loadMarkersList();
    }


    private void loadMarkersList() {
        mDisposer.add(
                mIContactsMapInteractor.loadMapMarkersList()
                        .subscribe(this::onLoadSuccess, this::onLoadError));
    }

    private void onLoadSuccess(List<MapMarkersModel> mapMarkersList) {
        Log.i("MY_TAG3", getClass().getSimpleName() + " onLoadSuccess" + mapMarkersList.size());
        getViewState().showMarkers(mapMarkersList);
    }

    private void onLoadError(Throwable throwable) {
        Log.e("MY_TAG3", getClass().getSimpleName() + " onLoadError " + throwable);
    }

    @Override
    public void setShownMarkersMap(Map<Marker, UUID> markersMap) {
        mMarkersMap = markersMap;
    }

    @Override
    public void onMarkerClicked(Marker marker) {
        getViewState().openContact(mMarkersMap.get(marker));
    }

    @Override
    public void onDestroy() {
        mDisposer.dispose();
        App.get().clearContactsMapComponent();
        super.onDestroy();
    }
}
