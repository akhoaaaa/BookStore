package com.khoa.bookstore.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.khoa.bookstore.R
import com.khoa.bookstore.Retrofit.ApiBookStore
import com.khoa.bookstore.Retrofit.RetrofitClient
import com.khoa.bookstore.Utils.Utils
import com.khoa.bookstore.databinding.ActivityDangKiBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class DangKiActivity : AppCompatActivity() {
    private lateinit var binding:ActivityDangKiBinding
    private lateinit var apiBookStore: ApiBookStore
    private val compositeDisposable = CompositeDisposable()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDangKiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        apiBookStore = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBookStore::class.java)

        Event()
    }

    private fun Event() {
        binding.btnDangNhap.setOnClickListener {
            DangKi()
        }

    }

    private fun DangKi() {
        val email = binding.txtEmail.text.toString().trim()
        val pass = binding.txtPass.text.toString().trim()
        val repass = binding.txtRePass.text.toString().trim()
        val sdt = binding.txtEmail.text.toString().trim()
        val username = binding.txtUserName.text.toString().trim()
        if (TextUtils.isEmpty(email)){
            Toast.makeText(this,"Bạn chưa nhập email ",Toast.LENGTH_SHORT).show()
        }else if (TextUtils.isEmpty(pass)){
            Toast.makeText(this,"Bạn chưa nhập Pass ",Toast.LENGTH_SHORT).show()
        }else if (TextUtils.isEmpty(repass)){
            Toast.makeText(this,"Bạn chưa nhập lại mật khẩu ",Toast.LENGTH_SHORT).show()
        }else if (TextUtils.isEmpty(sdt)){
            Toast.makeText(this,"Bạn chưa nhập số điện thoại ",Toast.LENGTH_SHORT).show()
        }else if (TextUtils.isEmpty(username)){
            Toast.makeText(this,"Bạn chưa nhập username ",Toast.LENGTH_SHORT).show()
        }else{
            if (pass.equals(repass)) {
                //post data
                compositeDisposable.add(apiBookStore.dangki(sdt, pass, email, username)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ userModel ->
                        if (userModel.isSuccess) {
                            Utils.User_current.username = username
                            Utils.User_current.pass = pass
                            val i = Intent(this, DangNhapActivity::class.java)
                            startActivity(i)
                            Toast.makeText(this,"Thành Công",Toast.LENGTH_SHORT).show()
                            finish()
                        } else {
                            Toast.makeText(this, userModel.message, Toast.LENGTH_SHORT).show()
                        }

                    }, { e ->
                        Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()


                    })
                )

            }else{
                Toast.makeText(this,"Hãy nhập lại trùng với mật khẩu đã đặt",Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()

    }
}