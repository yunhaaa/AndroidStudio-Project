package com.lx.oneteamproject.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lx.oneteamproject.databinding.ActivityMyPageBinding
import com.lx.oneteamproject.main.MainActivity

class MyPageActivity : AppCompatActivity() {

    lateinit var binding: ActivityMyPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 왼쪽 위 화살표 눌렀을 때 다시 메인으로
        binding.myPageBackButton.setOnClickListener {
            val mainIntent = Intent(this, MainActivity::class.java)
            startActivity(mainIntent)
            finish()
        }
    }

}