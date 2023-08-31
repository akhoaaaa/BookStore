package com.khoa.bookstore.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.khoa.bookstore.R
import com.khoa.bookstore.databinding.ActivitySachBinding


class SachActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySachBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySachBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}