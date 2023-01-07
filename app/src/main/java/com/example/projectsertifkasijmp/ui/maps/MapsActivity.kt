package com.example.projectsertifkasijmp.ui.maps

import android.location.Geocoder
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.projectsertifkasijmp.R
import com.example.projectsertifkasijmp.databinding.ActivityMapsBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import java.io.IOException
import java.util.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, View.OnClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    var alamat: String? = null
    var lat = -7.764410365423249
    var lon = 110.43330832348435

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        binding.btnPickLocation.setOnClickListener(this)

        val geocoder = Geocoder(this, Locale.getDefault())
        try {
            val list = geocoder.getFromLocation(lat, lon, 1)
            alamat = list[0].getAddressLine(0) + list[0].countryName
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val dicodingSpace = LatLng(-7.764410365423249, 110.43330832348435)
        mMap.addMarker(
            MarkerOptions()
                .position(dicodingSpace)
                .title("Dicoding Space")
        )
    }

    companion object {
        const val EXTRA_SELECTED_VALUE = "extra_selected_value"
        const val RESULT_CODE = 110
    }

    override fun onClick(v: View?) {
        val resultPick = intent.putExtra(EXTRA_SELECTED_VALUE, alamat)
        setResult(RESULT_CODE, resultPick)
        finish()
    }
}