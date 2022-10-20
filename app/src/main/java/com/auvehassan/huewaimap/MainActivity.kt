package com.auvehassan.huewaimap

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    // HUAWEI map
    private var hMap: HuaweiMap? = null

    private var mMapView: MapView? = null

    companion object {
        private const val TAG = "MapViewDemoActivity"
        private const val MAPVIEW_BUNDLE_KEY = "MapViewBundleKey"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate:")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mMapView = findViewById<com.huawei.hms.maps.MapView>(R.id.mapView)
        var mapViewBundle: Bundle? = null
        if (savedInstanceState != null) {
            mapViewBundle =
                savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY)
        }
        mMapView?.apply {
            onCreate(mapViewBundle)
            getMapAsync(this)
        }
    }

    override fun onMapReady(map: HuaweiMap) {
        Log.d(TAG, "onMapReady: ")
        hMap = map
    }

    override fun onStart() {
        super.onStart()
        mMapView?.onStart()
    }

    override fun onStop() {
        super.onStop()
        mMapView?.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mMapView?.onDestroy()
    }

    override fun onPause() {
        mMapView?.onPause()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        mMapView?.onResume()
    }

    private fun hasPermissions(context: Context, vararg permissions: String): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (permission in permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false
                }
            }
        }
        return true
    }


    /**
     * Initialize dummy markers
     */
    private fun initMarkers() {
        val markerDataList = createMarkerList()
        markerDataList.forEachIndexed { index, markerOptions ->
            val marker = hMap?.addMarker(markerOptions)
            marker?.setMarkerAnchor(0.5f, 1f) // Set marker anchor point
            marker?.tag = "$index Extra Info" // Set extra data with tag. This data can be a custom class
        }
        hMap?.setMarkersClustering(true) // Enable clustering
        hMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(markerDataList[0].position, 10f)) // Move camera to first item
        hMap?.setOnMarkerClickListener { marker ->
            Log.d(TAG, "Clicked marker ${marker.title}")
            true
        }

        hMap?.setInfoWindowAdapter(CustomInfoViewAdapter(this))

    }

    fun createMarkerList() : List<MarkerOptions> {
        val markerOptions = arrayListOf<MarkerOptions>()
        val latLangList = dummyLatLangList()
        latLangList.forEachIndexed { index, latLng ->
            val options = MarkerOptions()
                .position(latLng)
                .title("$index Market Title")
                .snippet("$index snippet!")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker))
                .clusterable(true) // Make it clusterable
            markerOptions.add(options)
        }
        return markerOptions
    }

    private fun dummyLatLangList() : ArrayList<LatLng> {
        val list = arrayListOf<LatLng>()
        list.add(LatLng(41.032284, 29.032708))
        list.add(LatLng(41.031442, 29.032322))
        list.add(LatLng(41.031507, 29.030369))
        list.add(LatLng(41.032527, 29.030358))
        list.add(LatLng(41.034081, 29.030787))
        list.add(LatLng(41.026767, 29.033660))
        list.add(LatLng(41.027885, 29.029466))
        list.add(LatLng(41.029577, 29.030861))
        list.add(LatLng(41.030451, 29.028737))
        list.add(LatLng(41.029666, 29.027503))
        list.add(LatLng(41.028865, 29.027321))
        list.add(LatLng(41.027934, 29.027375))
        return list
    }
}

