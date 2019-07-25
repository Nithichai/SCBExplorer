package com.nopyjf.projectscbexplorer.fragment


import android.app.Activity
import android.app.ListActivity
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.nopyjf.projectscbexplorer.R


class MapFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private lateinit var map: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var lastLocation: Location

    private lateinit var locationCallback: LocationCallback
    private lateinit var locationRequest: LocationRequest
    private var locationUpdateState = false

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
        private const val REQUEST_CHECK_SETTINGS = 2
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Auth.checking()
        val view = inflater.inflate(R.layout.fragment_map, container, false)
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this.activity!!.applicationContext)

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                super.onLocationResult(p0)

                lastLocation = Location(LocationManager.GPS_PROVIDER)
                lastLocation.latitude = 12.786981
                lastLocation.longitude = 100.906386
                drawCircle(LatLng(lastLocation.latitude, lastLocation.longitude))
                placeMarkerOnMap(LatLng(lastLocation.latitude, lastLocation.longitude))

                getNearShop()
            }
        }

        createLocationRequest()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CHECK_SETTINGS) {
            if (resultCode == Activity.RESULT_OK) {
                locationUpdateState = true
                startLocationUpdates()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    override fun onResume() {
        super.onResume()
        if (!locationUpdateState) {
            startLocationUpdates()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        map.setOnMarkerClickListener(this)

        setUpMap()
    }

    override fun onMarkerClick(p0: Marker?): Boolean {
        val intent = Intent(activity, ListActivity::class.java)
        startActivity(intent)
        return true
    }

    private fun setUpMap() {
        if (ActivityCompat.checkSelfPermission(
                this.activity!!.applicationContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this.activity!!,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE
            )
            return
        }

//        map.isMyLocationEnabled = true
        map.mapType = GoogleMap.MAP_TYPE_NORMAL

        map.setMinZoomPreference(14.0f)
        map.setMaxZoomPreference(14.5f)

        map.uiSettings.isZoomControlsEnabled = true
        map.uiSettings.setAllGesturesEnabled(false)
        map.uiSettings.isZoomGesturesEnabled = true
        map.uiSettings.isMapToolbarEnabled = false
        map.uiSettings.isIndoorLevelPickerEnabled = true

        fusedLocationClient.lastLocation.addOnSuccessListener(this.activity!!) { location ->
            // Got last known location. In some rare situations this can be null.
            if (location != null) {
                lastLocation = location
//                val currentLatLng = LatLng(location.latitude, location.longitude)
                val currentLatLng = LatLng(12.786981, 100.90638)
                placeMarkerOnMap(currentLatLng)
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))
            }
        }
    }

    private fun placeMarkerOnMap(location: LatLng) {
        val foodIcon: BitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.ic_food)


        val markerOptions = MarkerOptions()
            .position(location)
//            .icon(foodIcon)

//        val titleStr = getAddress(location)  // add these two lines
//        markerOptions.title(titleStr)

        map.addMarker(markerOptions)
    }

//    private fun getAddress(latLng: LatLng): String {
//        // 1
//        val geocoder = Geocoder(this)
//        val addresses: List<Address>?
//        val address: Address?
//        var addressText = ""
//
//        try {
//            // 2
//            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
//            // 3
//            if (null != addresses && !addresses.isEmpty()) {
//                address = addresses[0]
//                for (i in 0 until address.maxAddressLineIndex) {
//                    addressText += if (i == 0) address.getAddressLine(i) else "\n" + address.getAddressLine(i)
//                }
//            }
//        } catch (e: IOException) {
//            Log.e("MapsActivity", e.localizedMessage!!)
//        }
//
//        return addressText
//    }

    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(
                this.activity!!.applicationContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this.activity!!,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
            return
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null /* Looper */)
    }

    private fun createLocationRequest() {
        locationRequest = LocationRequest()
        locationRequest.interval = 10000
        locationRequest.fastestInterval = 5000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
        val client = LocationServices.getSettingsClient(this.activity!!)
        val task = client.checkLocationSettings(builder.build())

        task.addOnSuccessListener {
            locationUpdateState = true
            startLocationUpdates()
        }
        task.addOnFailureListener { e ->
            if (e is ResolvableApiException) {
                // Location settings are not satisfied, but this can be fixed
                // by showing the user a dialog.
                try {
                    // Show the dialog by calling startResolutionForResult(),
                    // and check the result in onActivityResult().
                    e.startResolutionForResult(
                        this.activity,
                        REQUEST_CHECK_SETTINGS
                    )
                } catch (sendEx: IntentSender.SendIntentException) {
                    // Ignore the error.
                }
            }
        }
    }

    private fun drawCircle(location: LatLng) {
        // Instantiating CircleOptions to draw a circle around the marker
        val circleOptions = CircleOptions()
        circleOptions.center(location)
        circleOptions.radius(1000.0)
        circleOptions.strokeColor(R.color.colorPrimary)
        circleOptions.fillColor(Color.alpha(0))
        circleOptions.strokeWidth(10f)
        map.addCircle(circleOptions)
    }

    private fun getNearShop() {
        val shops = ArrayList<LatLng>()
        shops.add(LatLng(12.784751, 100.908223))
        shops.add(LatLng(12.784765, 100.90785))

        for (latlng in shops) {
            placeShopMarkerOnMap(latlng)
        }
    }

    private fun placeShopMarkerOnMap(location: LatLng) {
        val markerOptions = MarkerOptions().position(location)
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
        map.addMarker(markerOptions)
        map.setOnMarkerClickListener(this)
    }


}
