package com.example.myappcontacts.presentation.contactsmap.view

import android.graphics.Bitmap
import android.location.Address
import android.location.Geocoder
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.myappcontacts.R
import com.example.myappcontacts.data.dao.contactsmap.MapMarkersModel
import com.example.myappcontacts.presentation.contacts.view.ContactsFragment
import com.example.myappcontacts.presentation.contactsmap.presenter.ContactsMapPresenter
import com.facebook.common.executors.CallerThreadExecutor
import com.facebook.common.references.CloseableReference
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber
import com.facebook.imagepipeline.image.CloseableImage
import com.facebook.imagepipeline.request.ImageRequestBuilder
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList

class ContactsMapFragment : MvpAppCompatFragment(), IContactsMapView, OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    private var map: GoogleMap? = null

    @InjectPresenter
    lateinit var contactsMapPresenter: ContactsMapPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_contacts_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //GoogleMap settings
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        contactsMapPresenter.init()
    }

    override fun showMarkers(mapMarkersList: List<MapMarkersModel>) {
        if (map == null) return
        if (mapMarkersList.isEmpty()) return

        val markersMap = HashMap<Marker, UUID>()
        val geocoder = Geocoder(activity)
        val latLngList = ArrayList<LatLng>()

        for (mapMarkersModel in mapMarkersList) {
            val contactId = mapMarkersModel.contactId
            val addressString = mapMarkersModel.address
            val photoUriString = mapMarkersModel.photoUri
            val name = mapMarkersModel.name

            var address: Address? = null
            try {
                val addresses = geocoder.getFromLocationName(addressString, 1)
                if (addresses.isEmpty()) continue
                address = addresses[0]
            } catch (e: IOException) {
                e.printStackTrace()
            }
            latLngList.add(LatLng(address!!.latitude, address.longitude))

            val contactMarker = MarkerOptions()
                    .position(latLngList.last())

            if (name == "") contactMarker.title(getString(R.string.no_name))
            else contactMarker.title(name)

            val marker = map!!.addMarker(contactMarker)
            markersMap[marker] = contactId

            if (photoUriString != "") {
                val imageRequest = ImageRequestBuilder
                        .newBuilderWithSource(Uri.parse(photoUriString))
                        .build()

                val dataSource = Fresco.getImagePipeline().fetchDecodedImage(imageRequest, this)
                dataSource.subscribe(object : BaseBitmapDataSubscriber() {
                    override fun onNewResultImpl(bitmap: Bitmap?) {
                        if (dataSource.isFinished && bitmap != null) {
                            val width = 100
                            val height = 100
                            val myBitmap = Bitmap.createScaledBitmap(Bitmap.createBitmap(bitmap), width, height, false)
                            val bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(myBitmap)
                            marker.setIcon(bitmapDescriptor)
                            dataSource.close()
                        }
                    }

                    override fun onFailureImpl(dataSource: com.facebook.datasource.DataSource<CloseableReference<CloseableImage>>?) {
                        dataSource?.close()
                    }
                }, CallerThreadExecutor.getInstance())
            }
        }
        contactsMapPresenter.setShownMarkersMap(markersMap)

        if (latLngList.size >= 2) {
            val bounds = LatLngBounds.builder()
                    .include(latLngList[0])
                    .include(latLngList[1])
                    .build()
            val margin = 250
            val update = CameraUpdateFactory.newLatLngBounds(bounds, margin)
            map!!.animateCamera(update)
        } else map!!.animateCamera(CameraUpdateFactory.newLatLng(latLngList.last()))
    }

    override fun onMapReady(googleMap: GoogleMap) {
            map = googleMap
            googleMap.clear()
            googleMap.uiSettings.isCompassEnabled = true
            googleMap.uiSettings.isScrollGesturesEnabled = true
            googleMap.mapType = GoogleMap.MAP_TYPE_NORMAL
            googleMap.setOnMarkerClickListener(this)
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        contactsMapPresenter.onMarkerClicked(marker)
        return true
    }

    override fun openContact(contactId: UUID) {
        val args = Bundle()
        args.putString(ContactsFragment.ARG_CONTACT_ID, contactId.toString())
        NavHostFragment.findNavController(this).navigate(R.id.contactsFragment, args)
    }
}
