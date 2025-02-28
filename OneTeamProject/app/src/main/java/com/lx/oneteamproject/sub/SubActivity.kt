package com.lx.oneteamproject.sub

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.lx.oneteamproject.databinding.ActivitySubBinding
import com.lx.oneteamproject.fragment.FragmentType
import com.lx.oneteamproject.fragment.OnFragmentListener
import com.lx.oneteamproject.R
import com.lx.oneteamproject.main.MainActivity
import com.lx.oneteamproject.main.NearSituationMapFragment
import com.lx.oneteamproject.main.NearStatisticsMapFragment
import com.lx.oneteamproject.main.ReportDetailsFragment


class SubActivity : AppCompatActivity(), OnFragmentListener {
    lateinit var binding: ActivitySubBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val fragmentType = intent.getStringExtra("fragmentType")?.let { FragmentType.valueOf(it) }
        if (fragmentType != null) {
            onFragmentChanged(fragmentType)
        } else {
        }

        binding.backButton.setOnClickListener {
            if (supportFragmentManager.backStackEntryCount > 1) {
                supportFragmentManager.popBackStack()
            } else {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                overridePendingTransition(0, 0) // 애니메이션 제거
            }
        }

        // 설정 아이콘 눌렀을 때 햄버거
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)

        binding.subSettingsButton.setOnClickListener {
            if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
                drawerLayout.closeDrawer(GravityCompat.END)
            } else {
                drawerLayout.openDrawer(GravityCompat.END)
            }
            true
        }

        // 로고 눌렀을 때 메인으로 전환
        binding.mainbackbutton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
    override fun onFragmentChanged(type: FragmentType) {
        when (type) {
            FragmentType.MYREPORTLIST -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.myreportlistfragmentcontainer, MyReportListFragment()).addToBackStack(null).commit()
            }

            FragmentType.NEARSITUATIONMAP -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.myreportlistfragmentcontainer, NearSituationMapFragment()).addToBackStack(null).commit()
            }

            FragmentType.NEARSTATISTICSMAP -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.myreportlistfragmentcontainer, NearStatisticsMapFragment()).addToBackStack(null).commit()
            }

            FragmentType.REPORTDETAILS -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.myreportlistfragmentcontainer, ReportDetailsFragment()).addToBackStack(null).commit()
            }

            FragmentType.NEARSTATISTICSMAP_SOCIAL -> {
                val fragment = NearStatisticsMapFragment().apply {
                    arguments = Bundle().apply {
                        putString("selectedButton", "social")
                    }
                }
                supportFragmentManager.beginTransaction()
                    .replace(R.id.myreportlistfragmentcontainer, fragment).addToBackStack(null).commit()
            }

            FragmentType.NEARSTATISTICSMAP_RISK -> {
                val fragment = NearStatisticsMapFragment().apply {
                    arguments = Bundle().apply {
                        putString("selectedButton", "risk")
                    }
                }
                supportFragmentManager.beginTransaction()
                    .replace(R.id.myreportlistfragmentcontainer, fragment).addToBackStack(null).commit()
            }

            FragmentType.NEARSTATISTICSMAP_NATURAL -> {
                val fragment = NearStatisticsMapFragment().apply {
                    arguments = Bundle().apply {
                        putString("selectedButton", "natural")
                    }
                }
                supportFragmentManager.beginTransaction()
                    .replace(R.id.myreportlistfragmentcontainer, fragment).addToBackStack(null).commit()
            }

            FragmentType.NEARSTATISTICSMAP_SAFETY -> {
                val fragment = NearStatisticsMapFragment().apply {
                    arguments = Bundle().apply {
                        putString("selectedButton", "safety")
                    }
                }
                supportFragmentManager.beginTransaction()
                    .replace(R.id.myreportlistfragmentcontainer, fragment).addToBackStack(null).commit()
            }



            else -> {}
        }
    }
}