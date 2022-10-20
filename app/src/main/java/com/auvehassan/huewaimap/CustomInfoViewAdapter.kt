package com.auvehassan.huewaimap

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView

class CustomInfoViewAdapter constructor(context : Context) : HuaweiMap.InfoWindowAdapter {

    private var mWindow: View? = null

    init {
        mWindow = LayoutInflater.from(context).inflate(R.layout.custom_info_window_layout, null)
    }

    override fun getInfoWindow(marker: Marker?): View? {

        if (marker == null || marker.tag == null)
            return null

        val tvTitle = mWindow?.findViewById<TextView>(R.id.tv_title)
        val tvSnippet = mWindow?.findViewById<TextView>(R.id.tv_snippet)
        val tvExtraInfo = mWindow?.findViewById<TextView>(R.id.tv_extra_info)

        // Set data to UI
        tvTitle?.text = marker.title
        tvSnippet?.text = marker.snippet

        // Get extra marker info
        val markerInfo = marker.tag as String
        tvExtraInfo?.text = markerInfo


        return mWindow
    }

    override fun getInfoContents(marker: Marker?): View? {
        return null
    }
}