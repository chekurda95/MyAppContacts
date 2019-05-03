package com.example.myappcontacts.presentation.contactsmap.view

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.example.myappcontacts.data.dao.contactsmap.db.MapMarkersModel
import java.util.UUID

interface IContactsMapView : MvpView {

    @StateStrategyType(SkipStrategy::class)
    fun showMarkers(mapMarkersList: List<MapMarkersModel>)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun openContact(contactId: UUID)

}
