package com.khoa.bookstore.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.khoa.bookstore.R
import com.khoa.bookstore.Utils.Utils
import com.khoa.bookstore.databinding.ActivityThongTinBinding
import io.reactivex.rxjava3.disposables.CompositeDisposable

class ThongTinActivity : AppCompatActivity() {
    private lateinit var binding: ActivityThongTinBinding
    private var compositeDisposable = CompositeDisposable()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThongTinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tbThongtin.apply {
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            setNavigationOnClickListener {
                finish()
            }
        }
        binding.btnDangXuat.setOnClickListener {
            Utils.isUserLoggedIn = false
            val i = Intent(this,SplashActivity::class.java)
            startActivity(i)
        }

        binding.txtUsername.text = "Xin chào: " + Utils.User_current.username
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }
}