package com.example.myappcontacts.presentation.contactsmap.presenter;

import com.google.android.gms.maps.model.Marker;

import java.util.Map;
import java.util.UUID;

public interface IContactsMapPresenter {

    void init();

    void setShownMarkersMap(Map<Marker, UUID> markersMap);

    void onMarkerClicked(Marker marker);
}
