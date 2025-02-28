package com.lx.oneteamproject.main

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.lx.oneteamproject.R
import com.lx.oneteamproject.databinding.NearRiskInformationFragmentBinding

class NearRiskInformationFragment : Fragment() {

    private var _binding: NearRiskInformationFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var map: GoogleMap
    private var locationClient: FusedLocationProviderClient? = null

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1001
        private const val DEFAULT_ZOOM = 15f
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = NearRiskInformationFragmentBinding.inflate(inflater, container, false)
        val rootView = binding.root

        initMap()

        return rootView
    }

    private fun initMap() {
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.riskInformationFragment) as SupportMapFragment
        mapFragment.getMapAsync { googleMap ->
            map = googleMap
            requestLocation()
        }
    }

    private fun requestLocation() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            // 이미 권한이 부여되었을 경우 작업 수행
            getLocation()
        } else {
            // 권한이 부여되지 않았을 경우 사용자에게 요청
            requestLocationPermission()
        }
    }

    private fun requestLocationPermission() {
        requestPermissions(
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            LOCATION_PERMISSION_REQUEST_CODE
        )
    }

    // 권한 요청 결과를 처리
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            LOCATION_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 권한이 부여되었을 경우 작업 수행
                    getLocation()
                } else {
                    // 권한이 거부되었을 경우 적절히 처리 (예: 사용자에게 메시지 표시)
                    Toast.makeText(
                        requireContext(),
                        "위치 권한이 거부되었습니다. 일부 기능이 작동하지 않을 수 있습니다.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun getLocation() {
        locationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            locationClient?.lastLocation?.addOnSuccessListener { location ->
                if (location != null) {
                    // 위치를 가져와서 지도를 해당 위치로 이동
                    moveCameraToLocation(location)
                }
            }
        } else {
            // 위치 권한이 없을 경우 권한 요청
            requestLocationPermission()
        }
    }

    private fun moveCameraToLocation(location: Location) {
        val currentLatLng = LatLng(location.latitude, location.longitude)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, DEFAULT_ZOOM))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
