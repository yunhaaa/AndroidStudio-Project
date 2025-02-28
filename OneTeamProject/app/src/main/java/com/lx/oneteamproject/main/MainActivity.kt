package com.lx.oneteamproject.main

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.core.view.get
import androidx.fragment.app.Fragment
import com.lx.oneteamproject.R
import com.lx.oneteamproject.databinding.ActivityMainBinding
import com.lx.oneteamproject.fragment.FragmentType
import com.lx.oneteamproject.fragment.OnFragmentListener
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.lx.oneteamproject.activity.EmergencyPlaceNumberActivity
import com.lx.oneteamproject.activity.LoginActivity
import com.lx.oneteamproject.activity.MyPageActivity
import java.text.SimpleDateFormat
import java.util.Locale


class MainActivity : AppCompatActivity(), OnFragmentListener {

    lateinit var binding: ActivityMainBinding
    lateinit var sharedPreferences: SharedPreferences
    private lateinit var mAuth: FirebaseAuth

    companion object {
        private const val MAIN_POPUP_STATE_KEY = "main_popup_state"
        private const val LAST_POPUP_TIME_KEY = "last_popup_time"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = FirebaseAuth.getInstance()

        // SharedPreferences 초기화
        sharedPreferences = getPreferences(Context.MODE_PRIVATE)

        // 저장된 시간 가져오기
        val lastPopupTime = sharedPreferences.getLong(LAST_POPUP_TIME_KEY, 0)

        // mainPopupCheckBox의 상태에 따라 팝업 표시 여부 결정
        val shouldShowPopup = !sharedPreferences.getBoolean(MAIN_POPUP_STATE_KEY, false) ||
                (sharedPreferences.getBoolean(MAIN_POPUP_STATE_KEY, false) &&
                        System.currentTimeMillis() - lastPopupTime >= 24 * 60 * 60 * 1000)

        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val formattedDate = dateFormat.format(lastPopupTime)

        Log.d("팝업 24시간 안뜨게 한 시간", formattedDate)


        if (!isFinishing && shouldShowPopup) {
            showPopup()
        }

        // 화면 첫 시작 되는 fragment 호출
        onFragmentChanged(FragmentType.MAIN)

        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)

        // 설정 아이콘 눌렀을 때 햄버거
        binding.mainSettingsButton.setOnClickListener {
            if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
                drawerLayout.closeDrawer(GravityCompat.END)
            } else {
                drawerLayout.openDrawer(GravityCompat.END)
            }

        }

        // 네비게이션 뷰의 로그아웃 버튼 클릭 리스너 설정
        val navigationView = findViewById<NavigationView>(R.id.navigationView)
        val logoutButton = navigationView.getHeaderView(0).findViewById<Button>(R.id.logoutButton)

        logoutButton.setOnClickListener {
            // 사용자 로그아웃
            mAuth.signOut()
            saveUserInfoToSharedPreferences(null, null) // 로그아웃 시 저장된 정보를 지움
            val loginIntent = Intent(this, LoginActivity::class.java)
            startActivity(loginIntent)
            finish()
        }

        // 네비게이션 뷰의 마이페이지 버튼 클릭 리스너 설정
        val mypageButton = navigationView.getHeaderView(0).findViewById<TextView>(R.id.myPageButton)
        mypageButton.setOnClickListener {
            val myPageIntent = Intent(this, MyPageActivity::class.java)
            startActivity(myPageIntent)
            finish()
        }

        // 네비게이션 뷰의 안전기관 연락처 알아보기 버튼 클릭 리스너 설정
        val emergencyplacenumber = navigationView.getHeaderView(0).findViewById<TextView>(R.id.emergencyPlaceButton)
        emergencyplacenumber.setOnClickListener {
            val emergencyPlaceNumberIntent = Intent(this, EmergencyPlaceNumberActivity::class.java)
            startActivity(emergencyPlaceNumberIntent)
            finish()
        }

        // 로고 눌렀을 때 메인으로
        binding.mainBackPageButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }

    private fun showPopup() {
        val popUpView = LayoutInflater.from(this).inflate(R.layout.manual_main, null)
        val popUpBuilder = AlertDialog.Builder(this, R.style.FullScreenDialogStyle)
            .setView(popUpView)

        val popUpDialog = popUpBuilder.show()

        val closeButton = popUpView.findViewById<Button>(R.id.closeButton)
        val checkBox = popUpView.findViewById<CheckBox>(R.id.mainPopupCheckBox)

        closeButton.setOnClickListener {
            // 팝업이 닫힐 때 상태 저장
            savePopupState(checkBox.isChecked)
            savePopupTime()

            popUpDialog.dismiss()
        }
    }


    private fun savePopupState(isChecked: Boolean) {
        Log.d("PopupTest", "savePopupState - isChecked: $isChecked")
        val editor = sharedPreferences.edit()
        editor.putBoolean(MAIN_POPUP_STATE_KEY, isChecked)
        editor.apply()
    }

    private fun savePopupTime() {
        val editor = sharedPreferences.edit()
        editor.putLong(LAST_POPUP_TIME_KEY, System.currentTimeMillis())
        editor.apply()
    }


    // 각 fragment 설정
    @SuppressLint("SuspiciousIndentation")
    override fun onFragmentChanged(type: FragmentType) {val fragment: Fragment
        val bundle = Bundle()

        when (type) {
            FragmentType.MAIN -> {
                supportFragmentManager.beginTransaction().replace(R.id.mainFragmentContainer, MainFragment()).commit()
            }

            FragmentType.REPORT -> {
                supportFragmentManager.beginTransaction().replace(R.id.mainFragmentContainer, MainReportFragment()).commit()
            }

            FragmentType.REPORTCHECK -> {
                supportFragmentManager.beginTransaction().replace(R.id.mainFragmentContainer, ReportCheckPopUpFragment()).commit()
            }

            FragmentType.REPORTFREE -> {
                supportFragmentManager.beginTransaction().replace(R.id.mainReportContainer, MainReportFreeFragment()).commit()
            }

            FragmentType.REPORTFORM -> {
                supportFragmentManager.beginTransaction().replace(R.id.mainReportContainer, MainReportFormFragment()).commit()
            }

            else -> {
                return
            }
        }

    }

    private fun saveUserInfoToSharedPreferences(email: String?, password: String?) {
        val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        if (email != null) {
            editor.putString("email", email)
        } else {
            editor.remove("email") // email 값을 제거하려면 null 대신 remove 메서드를 호출합니다.
        }
        if (password != null) {
            editor.putString("password", password)
        } else {
            editor.remove("password") // password 값을 제거하려면 null 대신 remove 메서드를 호출합니다.
        }
        editor.apply()
    }

}
