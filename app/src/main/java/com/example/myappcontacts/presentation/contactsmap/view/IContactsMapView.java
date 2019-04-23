package com.example.myappcontacts.presentation.contactsmap.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.example.myappcontacts.data.dao.contactsmap.MapMarkersModel;

import java.util.List;
import java.util.UUID;

public interface IContactsMapView extends MvpView {

    @StateStrategyType(SkipStrategy.class)
    void showMarkers(List<MapMarkersModel> mapMarkersList);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void openContact(UUID contactId);

}
