package com.example.reservehotel

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker

class InfoWindowAdapter(context: MapFragment): GoogleMap.InfoWindowAdapter {
    @SuppressLint("InflateParams")
    var mWindow: View = (context as Fragment).layoutInflater.inflate(R.layout.custom_info_window, null)

    private fun rendowWindowText(marker: Marker, view: View){

        val tvTitle = view.findViewById<TextView>(R.id.title)
        val tvSnippet = view.findViewById<TextView>(R.id.snippet)

        tvTitle.text = marker.title
        tvSnippet.text = marker.snippet

    }

    override fun getInfoContents(marker: Marker): View {
        rendowWindowText(marker, mWindow)
        return mWindow
    }

    override fun getInfoWindow(marker: Marker): View? {
        rendowWindowText(marker, mWindow)
        return mWindow
    }
}
