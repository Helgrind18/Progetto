package com.example.progetto.mappa


import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.os.Build
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.progetto.R
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

class Mappa : AppCompatActivity(), LocationListener {
    private lateinit var locationManager: LocationManager
    private val locationPermissionCode = 1
    private lateinit var mapView: MapView

    //Creo una lista di punti di interesse possibili
    private val puntiInteresse: List<PuntoInteresse> = getPuntiInteresse()

    private fun getPuntiInteresse(): List<PuntoInteresse> {
        return listOf(
            PuntoInteresse("Ponte Bucci", 39.363627, 16.226413, 50f),
            PuntoInteresse("Biblioteca Centrale", 39.359054, 16.230531, 100f),
            PuntoInteresse("Aula Magna", 39.363402, 16.225327, 80f),
            PuntoInteresse("Centro Residenziale", 39.361512, 16.231271, 120f),
            PuntoInteresse("Cubo 22 (Matematica e Informatica)", 39.363900, 16.225800, 50f),
            PuntoInteresse("Cubo 27 (Filosofia)", 39.364200, 16.226500, 50f),
            PuntoInteresse("Cubo 32 (Ingegneria Civile)", 39.366000, 16.226700, 50f),
            PuntoInteresse("Cubo 43 (Scienze Economiche)", 39.366500, 16.224800, 50f),
            PuntoInteresse("Mensa Universitaria", 39.362789, 16.227416, 70f),
            PuntoInteresse("Piazza Vermicelli", 39.361635, 16.227779, 50f),
            PuntoInteresse("Piazza Chiodo", 39.361915, 16.226352, 50f),
            PuntoInteresse("Dipartimento di Fisica", 39.365000, 16.227000, 80f),
            PuntoInteresse("Dipartimento di Chimica", 39.364500, 16.228000, 80f),
            PuntoInteresse("Palestra UniCal", 39.360419, 16.229779, 100f),
            PuntoInteresse("Stadio Campo A", 39.358958, 16.229094, 150f),
            PuntoInteresse("Teatro Auditorium", 39.364569, 16.224965, 80f),
            PuntoInteresse("Museo di Storia Naturale", 39.367500, 16.228900, 100f),
            PuntoInteresse("Area di Ingegneria", 39.365401, 16.226300, 70f),
            PuntoInteresse("Laboratori di Informatica", 39.362900, 16.226700, 60f),
            PuntoInteresse("Dipartimento di Economia", 39.364800, 16.224800, 100f),
            PuntoInteresse("Area Ristorazione UniCal", 39.365000, 16.232500, 70f),
            PuntoInteresse("Casa dello Studente", 39.362300, 16.231000, 120f)
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_mappa)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Implementazione di OSMDroid per poter usare OperStreetMap
        Configuration.getInstance().userAgentValue = "Mappa/1.0" //statico

        // Inizializzazione della mappa
        mapView = findViewById(R.id.map)
        mapView.setMultiTouchControls(true)

        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                locationPermissionCode
            )
        } else {
            startLocationUpdates()
            //Per rendere l'app Context Aware si ragiona così: quando si è nelle vicinanze di un punto di interesse viene mandata una notifica

        }
    }

    private fun puntoDiInteresse(mapView: MapView, currentLocation: GeoPoint) {
        // Se l'utente si trova vicino a un punto di interesse, viene notificato.
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            var distanza: FloatArray = floatArrayOf(0f, 0f, 0f)
            for (punto in puntiInteresse) {
                Location.distanceBetween(
                    //calcolo la distanza in linea d'aria
                    currentLocation.latitude,
                    currentLocation.longitude,
                    punto.latitudine,
                    punto.longitudine,
                    distanza
                )

                if (distanza[0] <= 100) { // Soglia di prossimità
                    // Aggiungi il marker sulla mappa
                    val possibileMarker = Marker(mapView)
                    possibileMarker.position = GeoPoint(punto.latitudine, punto.longitudine)
                    possibileMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                    possibileMarker.title = punto.nome
                    mapView.overlays.add(possibileMarker)

                }
            }
        }
    }





    private fun startLocationUpdates() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            // Ottenere ultima posizione nota
            val lastKnownLocation =
                locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            val currentLocation = if (lastKnownLocation != null) {
                GeoPoint(lastKnownLocation.latitude, lastKnownLocation.longitude)
            } else {
                GeoPoint(39.3636, 16.2263) // UniCal
            }
            // Centrare la mappa
            mapView.controller.setCenter(currentLocation)
            mapView.controller.setZoom(18.0)
            // Aggiungere un marker
            val defaultMarker = Marker(mapView)
            defaultMarker.position = currentLocation
            defaultMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            defaultMarker.title = "Posizione Corrente"
            mapView.overlays.add(defaultMarker)

            puntoDiInteresse(mapView, currentLocation)

            // Richiedere aggiornamenti di posizione
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                2500,
                10f,
                this
            )
        } else {
            Toast.makeText(
                this, "Location permission not granted. Unable to update location.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }


    override fun onLocationChanged(location: Location) {
        val geoPoint = GeoPoint(location.latitude, location.longitude)

        // Pulire i marker precedenti
        mapView.overlays.clear()

        // Aggiungere un marker
        val marker = Marker(mapView)
        marker.position = geoPoint
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        marker.title = "Sei qui!"
        mapView.overlays.add(marker)


        // Centrare la mappa sulla nuova posizione
        mapView.controller.setCenter(geoPoint)
        mapView.controller.setZoom(18.0)

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == locationPermissionCode && grantResults.isNotEmpty()
            && grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            startLocationUpdates()
        }
    }
}


