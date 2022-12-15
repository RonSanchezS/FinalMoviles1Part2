package com.moviles.proyectofinaltrabajador2.ActivitiesAvanzadas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.moviles.proyectofinaltrabajador2.R
import com.moviles.proyectofinaltrabajador2.databinding.ActivitySeleccionarUbicacionBinding
import com.moviles.proyectofinaltrabajador2.models.Charla
import com.moviles.proyectofinaltrabajador2.models.Location
import com.moviles.proyectofinaltrabajador2.repository.ConversacionRepository

class ActivitySeleccionarUbicacion : AppCompatActivity(), OnMapReadyCallback,
    ConversacionRepository.onUbicacionListener {

    private lateinit var mMap: GoogleMap

    private lateinit var btnSendLocation: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivitySeleccionarUbicacionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        btnSendLocation = binding.btnSendLocation
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        var latitud = 0.0;
        var longitud = 0.0;
        //add onClick
        mMap.setOnMapClickListener { latLng ->
            mMap.clear()
            mMap.addMarker(MarkerOptions().position(latLng).title("Marker in Sydney"))
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
            latitud = latLng.latitude
            longitud = latLng.longitude
        }
        btnSendLocation.setOnClickListener {
            val id = intent.extras?.get("idCotizacion").toString()
            val location = Location(latitud.toString(), longitud.toString())
            val ubi = com.moviles.proyectofinaltrabajador2.models.Ubicacion(location)
            val token = getSharedPreferences("MyPref", MODE_PRIVATE).getString("token", "")
            Toast.makeText(this, "Enviando token $token", Toast.LENGTH_SHORT).show()
            Toast.makeText(this, "Enviando id $id", Toast.LENGTH_SHORT).show()
            if (token != null) {
                ConversacionRepository.uploadLocation(ubi, token, id, this)
            }
        }
    }

    override fun onUbicacionError(throwable: Throwable) {
        finish()
    }

    override fun onUbicacionSuccess(body: Charla) {
        finish()
    }
}