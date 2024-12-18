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
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
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
            PuntoInteresse("Ponte Bucci", 39.363627, 16.226413, 100f),
            PuntoInteresse("Biblioteca Centrale", 39.359054, 16.230531, 100f),
            PuntoInteresse("Aula Magna", 39.363402, 16.225327, 100f),
            PuntoInteresse("Centro Residenziale", 39.361512, 16.231271, 100f),
            PuntoInteresse("Cubo 22 (Matematica e Informatica)", 39.363900, 16.225800, 100f),
            PuntoInteresse("Cubo 27 (Filosofia)", 39.364200, 16.226500, 100f),
            PuntoInteresse("Cubo 32 (Ingegneria Civile)", 39.366000, 16.226700, 100f),
            PuntoInteresse("Cubo 43 (Scienze Economiche)", 39.366500, 16.224800, 100f),
            PuntoInteresse("Mensa Universitaria", 39.362789, 16.227416, 100f),
            PuntoInteresse("Piazza Vermicelli", 39.361635, 16.227779, 100f),
            PuntoInteresse("Piazza Chiodo", 39.361915, 16.226352, 100f),
            PuntoInteresse("Dipartimento di Fisica", 39.365000, 16.227000, 100f),
            PuntoInteresse("Dipartimento di Chimica", 39.364500, 16.228000, 100f),
            PuntoInteresse("Palestra UniCal", 39.360419, 16.229779, 100f),
            PuntoInteresse("Stadio Campo A", 39.358958, 16.229094, 100f),
            PuntoInteresse("Teatro Auditorium", 39.364569, 16.224965, 100f),
            PuntoInteresse("Museo di Storia Naturale", 39.367500, 16.228900, 100f),
            PuntoInteresse("Area di Ingegneria", 39.365401, 16.226300, 100f),
            PuntoInteresse("Laboratori di Informatica", 39.362900, 16.226700, 100f),
            PuntoInteresse("Dipartimento di Economia", 39.364800, 16.224800, 100f),
            PuntoInteresse("Area Ristorazione UniCal", 39.365000, 16.232500, 100f),
            PuntoInteresse("Casa dello Studente", 39.362300, 16.231000, 100f),
            PuntoInteresse("Cubo 45 (Scienze Naturali)", 39.366700, 16.228200, 100f),
            PuntoInteresse("Punto Relax Studenti", 39.362000, 16.230000, 100f),
            PuntoInteresse("Ingresso Nord UniCal", 39.367000, 16.225900, 100f),
            PuntoInteresse("Cubo 13 (Ingegneria Meccanica)", 39.363300, 16.229300, 100f),
            PuntoInteresse("Stazione Bus Campus", 39.361000, 16.227800, 100f),
            PuntoInteresse("Parco Scientifico", 39.365300, 16.229500, 100f),
            PuntoInteresse("Laboratorio di Fisica Applicata", 39.365700, 16.226100, 100f),
            PuntoInteresse("Campo Sportivo B", 39.358700, 16.229500, 100f),
            PuntoInteresse("Ristorante Centrale UniCal", 39.363100, 16.230800, 100f),
            PuntoInteresse("Dipartimento di Scienze Sociali", 39.366200, 16.224500, 100f),
            PuntoInteresse("Student Hub", 39.362400, 16.232200, 100f),
            PuntoInteresse("Area Verde Ovest", 39.364100, 16.228700, 100f),
            PuntoInteresse("Centro Tecnologico", 39.366500, 16.226900, 100f),
            PuntoInteresse("Casa della Cultura UniCal", 39.364300, 16.225500, 100f),
            PuntoInteresse("Pista di Atletica", 39.359500, 16.229700, 100f),
            PuntoInteresse("Cubo 55 (Geologia)", 39.367800, 16.227200, 100f),
            PuntoInteresse("Parcheggio Est", 39.365900, 16.224300, 100f),
            PuntoInteresse("Bar Centrale UniCal", 39.362600, 16.230300, 100f),
            PuntoInteresse("Edificio 19 (Storia dell'Arte)", 39.363500, 16.224600, 100f),
            PuntoInteresse("Aula Studio Giardino", 39.361300, 16.228500, 100f)
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
                    //Segnalo all'utente che si trovi in prossimità di un punto di interesse
                    Toast.makeText(this, "Sei vicino a ${punto.nome}", Toast.LENGTH_SHORT).show()
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
                GeoPoint(39.3641, 16.2259) // UniCal
            }

            markerUtente(currentLocation)

            puntoDiInteresse(mapView, currentLocation)

            // Richiedere aggiornamenti di posizione
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                5000,
                20f,
                this
            )
        } else {
            Toast.makeText(
                this, "Location permission not granted. Unable to update location.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun markerUtente(currentLocation: GeoPoint) {
        // Centrare la mappa
        mapView.controller.setCenter(currentLocation)
        mapView.controller.setZoom(18.0)

        // Aggiungere un marker
        val defaultMarker = Marker(mapView)
        defaultMarker.position = currentLocation
        defaultMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        defaultMarker.title = "Posizione Corrente"
        mapView.overlays.add(defaultMarker)
        //Il marker lo coloro in modo differente, per far capire all'utente dove si trovi
        addCustomMarker(currentLocation)
    }

    private fun addCustomMarker(currentLocation: GeoPoint) {
        val marker = Marker(mapView)
        marker.position = currentLocation
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        marker.title = "Posizione Corrente"

        // Creare un Bitmap personalizzato per il marker
        val markerBitmap = Bitmap.createBitmap(50, 50, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(markerBitmap)
        val paint = Paint()
        paint.color = Color.RED // Imposta il colore del cerchio
        paint.style = Paint.Style.FILL
        canvas.drawCircle(25f, 25f, 25f, paint) // Disegna il cerchio al centro

        // Imposta il marker con il Bitmap creato
        val markerIcon = BitmapDrawable(resources, markerBitmap)
        marker.setIcon(markerIcon)

        mapView.overlays.add(marker)
    }


    override fun onLocationChanged(location: Location) {
        val geoPoint = GeoPoint(location.latitude, location.longitude)

        //Pulisco tutti i marker
        mapView.overlays.clear()
        //Così ho aggiornamenti in tempo reale
        puntoDiInteresse(mapView, geoPoint)
        //Imposto il marker dell'utente
        markerUtente(geoPoint)
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