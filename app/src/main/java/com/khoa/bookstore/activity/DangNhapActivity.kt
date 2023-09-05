package com.khoa.bookstore.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.khoa.bookstore.R
import com.khoa.bookstore.databinding.ActivityDangNhapBinding

class DangNhapActivity : AppCompatActivity() {
    private lateinit var binding:ActivityDangNhapBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDangNhapBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.txtDangKy.setOnClickListener {
            val i = Intent(this,DangKiActivity::class.java)
            startActivity(i)
        }
    }
}