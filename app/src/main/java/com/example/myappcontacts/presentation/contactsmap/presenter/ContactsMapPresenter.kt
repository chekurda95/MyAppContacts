package com.example.myappcontacts.presentation.contactsmap.presenter

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.myappcontacts.App
import com.example.myappcontacts.business.contactsmap.IContactsMapInteractor
import com.example.myappcontacts.data.dao.contactsmap.db.MapMarkersModel
import com.example.myappcontacts.di.contactsmap.ContactsMapModule
import com.example.myappcontacts.presentation.contactsmap.view.IContactsMapView
import com.google.android.gms.maps.model.Marker
import io.reactivex.disposables.CompositeDisposable
import java.util.*
import javax.inject.Inject

@InjectViewState
class ContactsMapPresenter: MvpPresenter<IContactsMapView>(), IContactsMapPresenter {
    private val disposer = CompositeDisposable()
    private lateinit var markersMap: Map<Marker, UUID>

    companion object {
        private val TAG = "TEST"
    }

    @Inject
    lateinit var contactsMapInteractor: IContactsMapInteractor

    init {
        App.get().plusContactsMapModule(ContactsMapModule()).inject(this)
    }

    override fun init() = loadMarkersList()

    private fun loadMarkersList() {
        disposer.add(contactsMapInteractor.loadMapMarkersList()
                        .subscribe({onLoadSuccess(it)}, {onLoadError(it)}))
    }

    private fun onLoadSuccess(mapMarkersList: List<MapMarkersModel>) {
        Log.i(TAG, "${this::class.simpleName} onLoadSuccess ${mapMarkersList.size})")
        viewState.showMarkers(mapMarkersList)
    }

    private fun onLoadError(throwable: Throwable) {
        Log.e(TAG, """${this::class.simpleName} onLoadError $throwable)""")
    }

    override fun setShownMarkersMap(markersMap: Map<Marker, UUID>) {
        this.markersMap = markersMap
    }

    override fun onMarkerClicked(marker: Marker) {
        viewState.openContact(markersMap.getValue(marker))
    }

    override fun onDestroy() {
        disposer.dispose()
        App.get().clearContactsMapComponent()
        super.onDestroy()
    }

}