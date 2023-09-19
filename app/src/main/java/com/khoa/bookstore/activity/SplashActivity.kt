package com.khoa.bookstore.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.khoa.bookstore.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val thread = Thread(){
                try {
                    Thread.sleep(1500)
                }catch (e:java.lang.Exception){
                    e.printStackTrace()
                }finally {
                    runOnUiThread {
                        val i = Intent(this, MainActivity::class.java)
                        startActivity(i)
                        finish()
                    }
                }
            }
        thread.start()
        }
    }