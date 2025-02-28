package com.lx.oneteamproject.main

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Point
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.lx.oneteamproject.databinding.FragmentMainDetailsBinding
import com.lx.oneteamproject.fragment.FragmentType
import com.lx.oneteamproject.fragment.OnFragmentListener
import com.lx.oneteamproject.sub.SubActivity
import com.lx.oneteamproject.weather.Common
import com.lx.oneteamproject.weather.WeatherObject
import com.lx.oneteamproject.weather.getRainImage
import com.lx.popupproject.weather.ITEM
import com.lx.popupproject.weather.ModelWeather
import com.lx.popupproject.weather.WEATHER
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainDetailsFragment : Fragment() {

    var _binding: FragmentMainDetailsBinding? = null
    val binding get() = _binding!!

    var listener: OnFragmentListener? = null

    var locationclient: FusedLocationProviderClient? = null

    private val scope = CoroutineScope(Dispatchers.Main)

    private var baseDate = "20210510"
    private var baseTime = "1400"
    private var curPoint : Point? = null
    var currentLatitude: Double = 0.0
    var currentLongitude: Double = 0.0

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onAttach(activity: Activity) {
        super.onAttach(activity)

        val permissionList = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

        // 권한이 부여되어 있는지 확인
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // 권한이 부여되어 있지 않으면 권한 요청
            ActivityCompat.requestPermissions(requireActivity(), permissionList, 1)
        } else {
            // 권한이 이미 부여되어 있으면 위치 정보 요청
            requestLocation1()
        }


        if (activity is OnFragmentListener) {
            listener = activity as OnFragmentListener
        }

        if (activity is OnFragmentListener) {
            listener = activity as OnFragmentListener
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainDetailsBinding.inflate(inflater, container, false)

        // 국민행동요령 pager adapter 연결하는 부분
        binding.mainActionArticlePager.adapter =
            MainActionArticlePagerAdapter(requireActivity().supportFragmentManager, lifecycle)

        // 공익광고포스터 pager adapter 연결하는 부분
        binding.mainPublicAdPager.adapter =
            MainPublicAdPagerAdapter(requireActivity().supportFragmentManager, lifecycle)

        // 신고하기 눌렀을 때
        binding.mainReportButton.setOnClickListener {
            listener?.onFragmentChanged(FragmentType.REPORT)
        }

        binding.myLetterBox.setOnClickListener {
            val intent = Intent(requireContext(), SubActivity::class.java).apply {
                putExtra("fragmentType", FragmentType.MYREPORTLIST.name)
            }
            startActivity(intent)
            activity?.overridePendingTransition(0, 0) // 애니메이션 제거
        }

        binding.situationButton.setOnClickListener {
            val intent = Intent(requireContext(), SubActivity::class.java).apply {
                putExtra("fragmentType", FragmentType.NEARSITUATIONMAP.name)
            }
            startActivity(intent)
            activity?.overridePendingTransition(0, 0) // 애니메이션 제거
        }



        binding.socialButton.setOnClickListener {
            val intent = Intent(requireContext(), SubActivity::class.java).apply {
                putExtra("fragmentType", FragmentType.NEARSTATISTICSMAP_SOCIAL.name)
            }
            startActivity(intent)
            activity?.overridePendingTransition(0, 0) // 애니메이션 제거
        }

        binding.riskButton.setOnClickListener {
            val intent = Intent(requireContext(), SubActivity::class.java).apply {
                putExtra("fragmentType", FragmentType.NEARSTATISTICSMAP_RISK.name)
            }
            startActivity(intent)
            activity?.overridePendingTransition(0, 0) // 애니메이션 제거
        }

        binding.naturalButton.setOnClickListener {
            val intent = Intent(requireContext(), SubActivity::class.java).apply {
                putExtra("fragmentType", FragmentType.NEARSTATISTICSMAP_NATURAL.name)
            }
            startActivity(intent)
            activity?.overridePendingTransition(0, 0) // 애니메이션 제거
        }

        binding.safetyButton.setOnClickListener {
            val intent = Intent(requireContext(), SubActivity::class.java).apply {
                putExtra("fragmentType", FragmentType.NEARSTATISTICSMAP_SAFETY.name)
            }
            startActivity(intent)
            activity?.overridePendingTransition(0, 0) // 애니메이션 제거
        }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        scope.launch {
            requestLocation2()
        }
    }


    fun requestLocation2() {
        // 위치 클라이언트 만들기
        locationclient = LocationServices.getFusedLocationProviderClient(requireContext())

        try {
            // 내 현재 위치 요청하기
            val locationRequest = LocationRequest.create()
            locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            locationRequest.interval = 30 * 1000

            val locationCallback = object : LocationCallback() {
                // 제너레이트 오버라이드 메소드
                override fun onLocationResult(result: LocationResult) {
                    super.onLocationResult(result)

                    GlobalScope.launch(Dispatchers.Main) {
                        for ((index, location) in result.locations.withIndex()) {
                            if (isAdded) { // Fragment가 Context에 연결되어 있는지 확인합니다.
                                val address = getAddress(location.latitude, location.longitude)
                                binding.mainLocation?.text = address ?: "주소를 찾을 수 없습니다."
                            }
                        }
                    }



                }
            }
            // 위치 업데이트를 요청 하는 것
            locationclient?.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.myLooper()
            )

        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }


    fun getAddress(latitude: Double, longitude: Double): String? {
        val geocoder = Geocoder(requireContext(), Locale.KOREAN)
        val addresses = geocoder.getFromLocation(latitude, longitude, 1)
        val address = addresses?.getOrNull(0)?.getAddressLine(0)
        return address?.replace("대한민국 ", "")?.replace("KR", "")?.replace("서울특별시", "")?.trim()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        scope.cancel() // CoroutineScope를 취소합니다.
        _binding = null
    }

    override fun onDetach() {
        super.onDetach()

        listener = null
    }

    private fun setWeather(nx: Int, ny: Int) {
        val cal = Calendar.getInstance()

        baseDate = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(cal.time) // 현재 날짜


        var timeH = SimpleDateFormat("HH", Locale.getDefault()).format(cal.time) // 현재 시각// 현재 시각
        val timeM = SimpleDateFormat("mm", Locale.getDefault()).format(cal.time) // 현재 분 (수정)
        // API 가져오기 적당하게 변환
        baseTime = Common().getBaseTime(timeH, timeM) // 수정된 timeM 사용
        // 현재 시각이 00시이고 45분 이하일 때 baseTime이 2330이면 어제 정보 받아오기
        var resultH = timeH.toInt() -6
        if (resultH >= -1) {
            timeH= "1"
        } else if (resultH >= -2) {
            timeH= "2"
        } else if (resultH >= -3) {
            timeH= "3"
        } else if (resultH >= -4) {
            timeH= "4"
        } else if (resultH >= -5) {
            timeH= "5"
        } else if (resultH >= -6) {
            timeH= "6"
        }

        val resultHStr = if (resultH < 10) "0$resultH" else resultH.toString()

        baseTime = "$resultHStr$timeM"
        Log.d("MainActivity", "baseTime: $baseTime")
        if (timeH == "00") {
            cal.add(Calendar.DATE, -1)
            baseDate = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(cal.time)
        }

        // HTTPS를 사용한 예시 URL
        val call = WeatherObject.getRetrofitService().getWeather(60, 1, "JSON", baseDate, baseTime, nx, ny)

        // 비동기적으로 실행하기
        call.enqueue(object : Callback<WEATHER> {
            // 응답 성공 시
            override fun onResponse(call: Call<WEATHER>, response: Response<WEATHER>) {
                // 프래그먼트가 현재 활성화되어 있지 않은 경우 함수 종료
                if (!isAdded) return
                if (response.isSuccessful && response.body() != null) {
                    val responseBody = response.body()!!

                    if (_binding != null) { // binding이 null인지 확인
                        if (responseBody != null && responseBody.response != null && responseBody.response.body != null) {
                            val it: List<ITEM> = responseBody.response.body.items.item

                            val weatherArr = arrayOf(ModelWeather())

                            for (i in it.indices) {
                                when (it[i].category) {
                                    "PTY" -> weatherArr[0].rainType = it[i].fcstValue // 강수 형태
                                    "REH" -> weatherArr[0].humidity = it[i].fcstValue // 습도
                                    "SKY" -> weatherArr[0].sky = it[i].fcstValue // 하늘 상태
                                    "T1H" -> weatherArr[0].temp = it[i].fcstValue // 기온
                                }
                            }

                            weatherArr[0].fcstTime = "지금"
                            binding.tvTemp.text = "${weatherArr[0].temp}°C"

                            val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
                            binding.imgWeather.setImageResource(
                                getRainImage(
                                    weatherArr[0].rainType,
                                    weatherArr[0].sky,
                                    currentHour
                                )
                            )
                        } else {
                            Log.e("Weather", "서버 응답에 원하는 데이터가 없음")
                        }
                    }
                } else {
                    Log.e("Weather", "서버 응답 실패")
                }
            }


            override fun onFailure(call: Call<WEATHER>, t: Throwable) {
                // 실패 시 처리 로직을 여기에 추가
                Log.e("Weather", "API 요청 실패: ${t.message}")
            }
        })
    }


    @SuppressLint("MissingPermission")
    private fun requestLocation1() {
        Log.d("setWeather", "baseDate: $baseDate")
        val locationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        try {
            // 나의 현재 위치 요청
            val locationRequest = LocationRequest.create()
            locationRequest.run {
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                interval = 60 * 1000    // 요청 간격(1초)
            }
            val locationCallback = object : LocationCallback() {
                // 요청 결과
                override fun onLocationResult(p0: LocationResult) {
                    p0.let {
                        for (location in it.locations) {
                            // 현재 위치의 위경도를 격자 좌표로 변환
                            curPoint = Common().dfsXyConv(location.latitude, location.longitude)
                            currentLatitude = location.latitude
                            currentLongitude = location.longitude

                            // nx, ny 지점의 날씨 가져와서 설정하기
                            setWeather(curPoint!!.x, curPoint!!.y)
                            Log.d("Location", "Latitude: ${location.latitude}, Longitude: ${location.longitude}")
                        }
                    }
                }
            }

            // 내 위치 실시간으로 감지
            Looper.myLooper()?.let {
                locationClient.requestLocationUpdates(locationRequest, locationCallback, it)
            }
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }
}