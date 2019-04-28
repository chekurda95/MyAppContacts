package com.example.myappcontacts.presentation.contactsmap.presenter

import com.google.android.gms.maps.model.Marker
import java.util.UUID

interface IContactsMapPresenter {

    fun init()

    fun setShownMarkersMap(markersMap: Map<Marker, UUID>)

    fun onMarkerClicked(marker: Marker)
}
