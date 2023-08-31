package com.khoa.bookstore.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.khoa.bookstore.R
import com.khoa.bookstore.databinding.ActivityTruyenBinding

class TruyenActivity : AppCompatActivity() {
    private lateinit var binding:ActivityTruyenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTruyenBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}