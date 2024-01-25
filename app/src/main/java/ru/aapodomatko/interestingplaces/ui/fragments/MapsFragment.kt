package ru.aapodomatko.interestingplaces.ui.fragments

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import ru.aapodomatko.interestingplaces.R
import ru.aapodomatko.interestingplaces.databinding.FragmentMapsBinding

class MapsFragment : Fragment(), OnMapReadyCallback {
    private var _binding: FragmentMapsBinding? = null
    private val mBinding get() = _binding!!

    private val bundleArgs: MapsFragmentArgs by navArgs()

    private lateinit var googleM: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapsBinding.inflate(layoutInflater, container, false)

        return mBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        mBinding.iconBack.setOnClickListener {
            findNavController().popBackStack()
        }

    }

    override fun onMapReady(googleMap: GoogleMap) {
        googleM = googleMap
        val placeArgs = bundleArgs.coords
        val objectLocation = placeArgs.let {
            LatLng(it.coords?.lat ?: 0.0, it.coords?.lon ?: 0.0)
        }
        googleM.addMarker(MarkerOptions().position(objectLocation).title("Местоположение"))

        val cameraPosition = CameraPosition.builder()
            .target(objectLocation)
            .zoom(15.0f)
            .build()

        googleM.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }
}