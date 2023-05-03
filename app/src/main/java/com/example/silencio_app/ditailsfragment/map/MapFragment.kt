package com.example.silencio_app.ditailsfragment.map

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

import com.example.silencio_app.databinding.FragmentMapBinding
import com.google.type.LatLng


class MapFragment : Fragment() {
    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

//    private val pERMISSION_ID = 42
//    lateinit var mFusedLocationClient: FusedLocationProviderClient
//    lateinit var mMap: GoogleMap
//
//    // Current location is set to India, this will be of no use
//    var currentLocation: LatLng = LatLng(20.5, 78.9)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMapBinding.inflate(inflater, container, false)

      binding.webView

        val webSettings = binding.webView.settings
        webSettings.javaScriptEnabled = true

        binding.webView.loadUrl("https://www.google.com/maps")
        Onclick()
        return binding.root

    }


    private fun Onclick(){
        binding.cardViewProgess.setOnClickListener {

            findNavController().navigate(

                MapFragmentDirections.actionMapFragmentToExampleReferencesFragment()

            )
        }

    }

}