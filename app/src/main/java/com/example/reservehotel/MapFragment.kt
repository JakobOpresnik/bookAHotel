package com.example.reservehotel

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.reservehotel.databinding.FragmentMapBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions


data class HotelMarker(
    var location: LatLng,
    var name: String,
    var address: String
)

class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var binding: FragmentMapBinding
    private lateinit var mapView: MapView
    private lateinit var googleMap: GoogleMap
    private val markers : HashMap<Double, HotelMarker> = HashMap()

    @Deprecated("Deprecated in Java", ReplaceWith(
        "super.onActivityCreated(savedInstanceState)",
        "androidx.fragment.app.Fragment"
    ))
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mapView = binding.mapView
        // to display the map immediately
        mapView.onCreate(savedInstanceState)
        mapView.onResume()




        // add all hotel markers to the map
        mapView.getMapAsync{ googleMap ->
            // remove all markers
            googleMap.clear()

            googleMap.uiSettings.isZoomControlsEnabled = true
            googleMap.uiSettings.isZoomGesturesEnabled = true

            // animation to zoom in where the marker is
            val mariborLocation = LatLng(46.5546385762219, 15.645714707066432)
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mariborLocation, 14f))


            /*googleMap.setOnMarkerClickListener { marker -> // on marker click we are getting the title of our marker
                // which is clicked and displaying it in a toast message.
                val markerName = marker.title
                Toast.makeText(
                    context,
                    "Clicked location is $markerName",
                    Toast.LENGTH_SHORT
                ).show()
                false
            }*/

            // clicking on the marker info window takes us to the input screen
            // we also send the hotel name
            googleMap.setOnInfoWindowClickListener { marker ->
                Log.i("marker title", marker.title.toString())
                val hotelName = marker.title
                val bundle = Bundle()
                bundle.putString("hotel_name", hotelName)
                findNavController().navigate(R.id.inputFragment, bundle)
            }

            // check box filter
            val filterInput = binding.inputFilter
            val checkbox = binding.checkboxRating
            checkbox.setOnClickListener {
                val checked = checkbox.isChecked
                if (checked && filterInput.text.toString() != "") {
                    googleMap.clear()
                    for (marker in markers) {
                        if (marker.key >= filterInput.text.toString().toDouble()) {
                            addMarkerToMap(googleMap, marker.value)
                        }
                    }
                }
            }

            // add markers to the map initially
            if (filterInput.text.toString() == "") {
                val coordinates = "46.559977354863214, 15.639146206230603".split(",")
                val latitude = coordinates[0].toDouble()
                val longitude = coordinates[1].toDouble()
                val hotelLocation1 = LatLng(latitude, longitude)
                markers[3.8] = HotelMarker(hotelLocation1, "S Hotel", "Smetanova ulica 20, 2000 Maribor")
                markers[3.8]?.let { addMarkerToMap(googleMap, it) }

                val hotelLocation2 = LatLng(46.56014039108593, 15.647429991062523)
                markers[4.3] = HotelMarker(hotelLocation2, "Hotel Orel", "Volkmerjev prehod, 2000 Maribor")
                markers[4.3]?.let { addMarkerToMap(googleMap, it) }

                val hotelLocation3 = LatLng(46.556938563726, 15.650450035099487)
                markers[4.6] = HotelMarker(hotelLocation3, "Hotel City Maribor", "Ulica kneza Koclja 22, 2000 Maribor")
                markers[4.6]?.let { addMarkerToMap(googleMap, it) }

                val hotelLocation4 = LatLng(46.557414223569154, 15.642747814885615)
                markers[4.8] = HotelMarker(hotelLocation4, "Garden Room", "Vojašniški trg 8, 2000 Maribor")
                markers[4.8]?.let { addMarkerToMap(googleMap, it) }

                val hotelLocation5 = LatLng(46.55680257712338, 15.645669518105661)
                markers[3.9] = HotelMarker(hotelLocation5, "Hotel Lent", "Dravska ulica 9, 2000 Maribor")
                markers[3.9]?.let { addMarkerToMap(googleMap, it) }

                val hotelLocation6 = LatLng(46.53668222185121, 15.602381158585237)
                markers[4.4] = HotelMarker(hotelLocation6, "Garni Hotel", "Macunova ulica 1, 2000 Maribor")
                markers[4.4]?.let { addMarkerToMap(googleMap, it) }

                val hotelLocation7 = LatLng(46.55951747653705, 15.653298636907937)
                markers[4.1] = HotelMarker(hotelLocation7, "Hotel Piramida", "Ulica jeroja Šlandra 10, 2000 Maribor")
                markers[4.1]?.let { addMarkerToMap(googleMap, it) }

                val hotelLocation8 = LatLng(46.56026372426372, 15.644488677518158)
                markers[4.9] = HotelMarker(hotelLocation8, "Roomers", "Gledališka ulica 4, 2000 Maribor")
                markers[4.9]?.let { addMarkerToMap(googleMap, it) }

                val hotelLocation9 = LatLng(46.55951708689654, 15.650499750037405)
                markers[4.5] = HotelMarker(hotelLocation9, "B&B Hotel Maribor", "Ulica Vita Kraigherja 3, 2000 Maribor")
                markers[4.5]?.let { addMarkerToMap(googleMap, it) }

                val hotelLocation10 = LatLng(46.55027508573705, 15.637058809975521)
                markers[4.2] = HotelMarker(hotelLocation10, "Hotel Tabor", "Ulica heroja Zidanška 18, 2000 Maribor")
                markers[4.2]?.let { addMarkerToMap(googleMap, it) }
            }

            Log.i("markers", markers.size.toString())

            // add custom pop-up window for markers
            googleMap.setInfoWindowAdapter(InfoWindowAdapter(this))

        }
    }

    override fun onMapReady(map: GoogleMap) {
        map.let {
            googleMap = it
        }

        /*map.setOnMarkerClickListener { marker ->
            if (marker.isInfoWindowShown) {
                marker.hideInfoWindow()
            }
            else {
                marker.showInfoWindow()
            }
            true
        }*/
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMapBinding.inflate(inflater, container, false)

        val backButton = binding.backButton
        backButton.setOnClickListener {
            findNavController().navigate(R.id.homeFragment)     // navigate to home screen/fragment
        }

        return binding.root
    }

    private fun addMarkerToMap(googleMap: GoogleMap, marker: HotelMarker) {
        googleMap.addMarker(
            MarkerOptions()
                .position(marker.location)
                .title(marker.name)
                .snippet(marker.name)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.hotel_marker_2))
        )
    }

}
