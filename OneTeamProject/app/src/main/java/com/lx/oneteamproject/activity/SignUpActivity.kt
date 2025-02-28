package com.lx.oneteamproject.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.auth.User
import com.google.firebase.ktx.Firebase
import com.lx.oneteamproject.databinding.ActivitySignupBinding
import com.lx.oneteamproject.main.MainActivity

class SignUpActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignupBinding
    lateinit var checkBox1: CheckBox
    lateinit var checkBox2: CheckBox
    lateinit var signUpButton: Button

    var checkBox1Checked = false
    var checkBox2Checked = false

    //당연준 추가 1111 시작
    lateinit var mAuth: FirebaseAuth

    private lateinit var  mDbRef: DatabaseReference

    //당연준 추가 1111 끝

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkBox1 = binding.checkbok1
        checkBox2 = binding.checkbok2
        signUpButton = binding.signUpButton

        checkBox1.setOnCheckedChangeListener { _, isChecked ->
            checkBox1Checked = isChecked
        }

        checkBox2.setOnCheckedChangeListener { _, isChecked ->
            checkBox2Checked = isChecked
        }

        binding.signUpBackButton.setOnClickListener {
            val signupPageIntent = Intent(this@SignUpActivity, LoginActivity::class.java)
            startActivity(signupPageIntent)
            finish()
        }

        //당연준 추가 2222 시작
        //인증 초기화
        mAuth = Firebase.auth

        //db 초기화
        mDbRef = Firebase.database.reference


        // "회원가입" 버튼 눌렀을 때 다시 login 화면으로 전환
        binding.signUpButton.setOnClickListener {
            if (checkBox1Checked && checkBox2Checked) {
                val name = binding.inputName.text.toString().trim()
                val mobile = binding.inputMobile.text.toString().trim()
                val email = binding.inputEmail.text.toString().trim()
                val password = binding.inputPw.text.toString().trim()
                val passwordCh = binding.inputPwCh.text.toString().trim()
                // 사용자 등록 함수 호출

                if(password != passwordCh){
                    showToast("비밀번호 확인이 일치하지 않습니다.")
                }else{
                    signUp(name,email, password, passwordCh , mobile)
                }
            } else {
                showToast("약관에 동의해주세요")
            }

        }
        //당연준 추가 2222 끝


    }


    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    //당연준 추가 3333 시작
    private fun signUp(name: String, email: String, password: String, passwordCh: String, mobile: String) {

        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || passwordCh.isEmpty() || mobile.isEmpty()) {
            showToast("필수 입력 필드를 작성해주세요")
            return
        }
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    showToast("회원가입 성공")
                    // 사용자 정보를 Firebase 데이터베이스에 추가
                    addUserToDatabase(name, email, mAuth.currentUser?.uid!!, mobile)

                    // 회원가입 성공 시 Firebase 인증을 통해 로그인 수행
                    mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this) { loginTask ->
                            if (loginTask.isSuccessful) {
                                // 로그인 성공
                                val intent: Intent = Intent(this@SignUpActivity, MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                // 로그인 실패
                                showToast("자동 로그인 실패")
                                Log.d("AutoLogin", "Error: ${loginTask.exception}")
                            }
                        }
                } else {
                    showToast("회원가입 실패")
                    Log.d("SignUp", "Error: ${task.exception}")
                }
            }
    }

    // Firebase 데이터베이스에 사용자 정보 추가
    private fun addUserToDatabase(name: String,email: String,uid:String,mobile: String){
        mDbRef.child("user").child(uid).setValue(com.lx.oneteamproject.other.User(name,email,uid,mobile))
    }


}
