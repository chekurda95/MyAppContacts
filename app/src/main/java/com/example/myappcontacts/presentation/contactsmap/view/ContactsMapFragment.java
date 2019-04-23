package com.example.myappcontacts.presentation.contactsmap.view;

import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.myappcontacts.R;
import com.example.myappcontacts.data.dao.contactsmap.MapMarkersModel;
import com.example.myappcontacts.presentation.contacts.view.ContactsFragment;
import com.example.myappcontacts.presentation.contactsmap.presenter.ContactsMapPresenter;
import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import androidx.navigation.fragment.NavHostFragment;

public class ContactsMapFragment extends MvpAppCompatFragment implements IContactsMapView, OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;

    @InjectPresenter
    ContactsMapPresenter mContactsMapPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contacts_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //GoogleMap settings
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mContactsMapPresenter.init();
    }

    @Override
    public void showMarkers(List<MapMarkersModel> mapMarkersList) {
        if (mMap == null) {
            return;
        }
        Map<Marker, UUID> markersMap = new HashMap<>();
        Geocoder geocoder = new Geocoder(getActivity());
        List<LatLng> llList = new ArrayList<>();

        for (MapMarkersModel mapMarkersModel : mapMarkersList) {
            UUID contactId = mapMarkersModel.getContactsId();
            String addressString = mapMarkersModel.getAddress();
            String photoUriString = mapMarkersModel.getPhotoUri();
            String name = mapMarkersModel.getName();
            List<Address> addresses;
            Address address = null;
            try {
                addresses = geocoder.getFromLocationName(addressString, 1);
                if (addresses.size() == 0) {
                    continue;
                } else {
                    address = addresses.get(0);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            llList.add(new LatLng(address.getLatitude(), address.getLongitude()));
            LatLng contactPoint = new LatLng(address.getLatitude(), address.getLongitude());

            MarkerOptions contactMarker = new MarkerOptions()
                    .position(contactPoint);

            if (!name.equals("")) {
                contactMarker.title(name);
            } else {
                contactMarker.title(getString(R.string.no_name));
            }
            Marker marker = mMap.addMarker(contactMarker);
            markersMap.put(marker, contactId);

            if (photoUriString != null) {

                ImageRequest imageRequest = ImageRequestBuilder
                        .newBuilderWithSource(Uri.parse(photoUriString))
                        .build();

                ImagePipeline imagePipeline = Fresco.getImagePipeline();
                final DataSource<CloseableReference<CloseableImage>>
                        dataSource = imagePipeline.fetchDecodedImage(imageRequest, this);

                dataSource.subscribe(new BaseBitmapDataSubscriber() {

                    @Override
                    public void onNewResultImpl(@Nullable Bitmap bitmap) {
                        if (dataSource.isFinished() && bitmap != null) {
                            int width = 100;
                            int height = 100;
                            Bitmap bi = Bitmap.createBitmap(bitmap);
                            Bitmap bit = Bitmap.createScaledBitmap(bi, width, height, false);
                            BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(bit);
                            marker.setIcon(bitmapDescriptor);
                            dataSource.close();
                        }
                    }

                    @Override
                    public void onFailureImpl(DataSource dataSource) {
                        if (dataSource != null) {
                            dataSource.close();
                        }
                    }
                }, CallerThreadExecutor.getInstance());
            }
        }
        mContactsMapPresenter.setShownMarkersMap(markersMap);

        if (llList.size() >= 2) {
            LatLngBounds bounds = new LatLngBounds.Builder()
                    .include(llList.get(0))
                    .include(llList.get(1))
                    .build();
            int margin = 250;
            CameraUpdate update = CameraUpdateFactory.newLatLngBounds(bounds, margin);
            mMap.animateCamera(update);
        } else {
            mMap.animateCamera(CameraUpdateFactory.newLatLng(llList.get(0)));
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.clear();
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setScrollGesturesEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.setOnMarkerClickListener(this);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        mContactsMapPresenter.onMarkerClicked(marker);
        return true;
    }

    @Override
    public void openContact(UUID contactId) {
        Bundle args = new Bundle();
        args.putString(ContactsFragment.ARG_CONTACT_ID, contactId.toString());
        NavHostFragment.findNavController(this).navigate(R.id.contactsFragment, args);
    }
}

