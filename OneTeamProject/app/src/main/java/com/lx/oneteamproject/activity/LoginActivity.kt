package com.lx.oneteamproject.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.lx.oneteamproject.databinding.ActivityLoginBinding
import com.lx.oneteamproject.main.MainActivity

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding

    //당연준 추가 0000
    lateinit var mAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //당연준 추가 1111 시작
        //인증 초기화
        mAuth = Firebase.auth

        // 이전에 저장한 로그인 정보 불러오기
        val (email, password) = getUserInfoFromSharedPreferences()

        if (email != null && password != null) {
            // 이전에 저장한 로그인 정보가 있을 경우 자동 로그인 시도
            login(email, password)
        }


        //로그인 버튼 눌렀을 때
        binding.loginButton.setOnClickListener {

            val email = binding.idText.text.toString()
            val password = binding.pwText.text.toString()


            login(email, password)
            if (binding.rememLoginCheck.isChecked) {
                // 자동 로그인 체크박스가 체크되어 있을 때만 자동 로그인 시도
                saveUserInfoToSharedPreferences(email, password)
            }
        }
        //당연준 추가 1111 끝

        // "회원가입" 버튼 눌렀을 때 signup 화면으로 전환
        binding.signUpPageButton.setOnClickListener {
            val signUpPageIntent = Intent(this@LoginActivity, SignUpActivity::class.java)
            startActivity(signUpPageIntent)
            finish()
        }

    }


    //당연준 추가 2222 시작
    //로그인 버튼 눌렀을 때 함수
    private fun login(email:String , password: String){
        if (email.isEmpty()) {
            showToast("이메일이 비어 있습니다.")
            return
        }

        if (password.isEmpty()) {
            showToast("비밀번호가 비어 있습니다.")
            return
        }
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){ task->
                if (task.isSuccessful){
                    //성공시 실행
                    val intent: Intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this,"로그인 성공", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    //실패시 실행
                    Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show()
                    Log.d("Login", "Error: ${task.exception}")
                }
            }
    }


    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun saveUserInfoToSharedPreferences(email: String, password: String) {
        val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("email", email)
        editor.putString("password", password)
        editor.apply()
    }

    private fun getUserInfoFromSharedPreferences(): Pair<String?, String?> {
        val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val email = sharedPreferences.getString("email", null)
        val password = sharedPreferences.getString("password", null)
        return Pair(email, password)
    }

    //당연준 추가 2222 끝

}