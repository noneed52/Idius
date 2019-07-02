package com.example.busstation.ui.activity

import android.Manifest
import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import com.example.busstation.base.BaseActivity
import com.example.busstation.ui.dialog.BusStationArrivalInfoDialog
import com.example.busstation.ui.fragment.BusInfoFragment
import com.example.myapplication.R
import com.example.busstation.viewmodel.BusViewModel
import com.example.myapplication.databinding.ActivityMapsBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.rxkotlin.addTo


class MapsActivity : BaseActivity<ActivityMapsBinding>(), GoogleMap.OnMarkerClickListener {

    override val layoutId: Int = R.layout.activity_maps

    private lateinit var googleMap: GoogleMap
    private lateinit var busViewModel: BusViewModel
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val addedMarkerList by lazy { ArrayList<Marker>() }
    private val defaultLocation = LatLng(37.400275, 127.112150)

    companion object {
        const val ZOOM_LEVEL_WORLD = 1.toFloat()
        const val ZOOM_LEVEL_CONTINENT = 5.toFloat()
        const val ZOOM_LEVEL_CITY = 10.toFloat()
        const val ZOOM_LEVEL_STREETS = 15.toFloat()
        const val ZOOM_LEVEL_BUILDINGS = 20.toFloat()
        const val BUS_STATION_AROUND_COMPLETE = 100000
        const val BUS_ARRIVAL_INFO_COMPLETE   = 100001
        const val BUS_ROUTE_INFO              = 100002
    }

    private val activityCallbackObserver: Observer<Int> = Observer {
        when(it) {
            BUS_STATION_AROUND_COMPLETE -> addBusStationMarker()
            BUS_ARRIVAL_INFO_COMPLETE -> showBusInfoList()
            BUS_ROUTE_INFO -> showBusRouteInfo()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        busViewModel = getViewModel { BusViewModel(this) }

        (supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment).getMapAsync {
            googleMap = it
            googleMap.setOnMarkerClickListener(this)
            checkLocationPermission()
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this@MapsActivity)
        busViewModel.activityCallback.observe(this, activityCallbackObserver)
    }

    override fun onMarkerClick(marker: Marker?): Boolean {
        marker?.let {
            busViewModel.getBusStationInfo(it.title)
        }
        return false
    }

    /**
     * Asks user to allow permission for location of device
     */
    private fun checkLocationPermission() {
        RxPermissions(this)
            .request(Manifest.permission.ACCESS_FINE_LOCATION)
            .subscribe(::receiveLocationPermissionResult, ::errorHandler)
            .addTo(busViewModel.compositeDisposable)
    }

    /**
     * Receives the location permission granted results
     * @param granted: permission granted result
     */
    private fun receiveLocationPermissionResult(granted: Boolean) {
        when(granted) {
            true -> startLocationUpdates()
            else -> checkLocationPermission()
        }
    }

    /**
     * Start updating map location according to device's last location
     */
    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        initMap()
        fusedLocationClient.lastLocation.run {
            addOnSuccessListener(::relocateMap)
            addOnFailureListener { toast(it.localizedMessage) }
        }
    }

    /**
     * Init Google Map settings
     */
    @SuppressLint("MissingPermission")
    private fun initMap() {
        googleMap.run {
            isMyLocationEnabled = true
            isTrafficEnabled = true
            isBuildingsEnabled = true
            setDefaultLocation()
            setOnMyLocationClickListener(::relocateMap)
            busViewModel.requestBusStationList(defaultLocation.latitude, defaultLocation.longitude)
        }
    }

    /**
     * Add bus station location on google map
     */
    private fun addBusStationMarker() {
        busViewModel.busStationAroundInfoList?.forEach {
            addedMarkerList.add(googleMap.addMarker(MarkerOptions()
                .position(LatLng(it.y, it.x))
                .title(it.stationName)))
        }
    }

    /**
     * Show bus station
     */
    private fun showBusInfoList() {
        BusStationArrivalInfoDialog.newInstance(busViewModel)
            .show(supportFragmentManager, "dialog")
    }

    /**
     * Set default location when app is open
     */
    private fun setDefaultLocation() {
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation,
            ZOOM_LEVEL_STREETS
        ))
    }

    /**
     * Relocate the map according to location
     */
    private fun relocateMap(location: Location?) {
        location?.let {
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(it.latitude, it.longitude),
                ZOOM_LEVEL_STREETS
            ))
            busViewModel.requestBusStationList(it.latitude, it.longitude)
            addedMarkerList.forEach { marker -> marker.remove() }
        }
    }

    /**
     * Show bus route list
     */
    private fun showBusRouteInfo() {
        (supportFragmentManager.findFragmentByTag("dialog") as DialogFragment)
            .dismiss()
        viewDataBinding?.let {
            replaceFragment(R.id.map, BusInfoFragment(), "busRouteInfo")
        }
    }
}