package com.example.obstaclerace.ui

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.obstaclerace.R
import com.example.obstaclerace.utilities.Constants

class MapFragment : Fragment() {

    private var mapClient: GoogleMap? = null

    private val callback = OnMapReadyCallback { googleMap: GoogleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        mapClient = googleMap
        zoom()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    fun zoom(
        lat: Double = Constants.LocationDefault.LATITUDE,
        lon: Double = Constants.LocationDefault.LONGITUDE,
        title: String = "Hello World"
    ) {
        val location = LatLng(lat, lon)
        mapClient?.addMarker(MarkerOptions().position(location).title(title))
        mapClient?.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 12f))
    }
}