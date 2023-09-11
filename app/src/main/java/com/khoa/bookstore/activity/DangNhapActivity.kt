package com.khoa.bookstore.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.khoa.bookstore.R
import com.khoa.bookstore.Retrofit.ApiBookStore
import com.khoa.bookstore.Retrofit.RetrofitClient
import com.khoa.bookstore.Utils.Utils
import com.khoa.bookstore.databinding.ActivityDangNhapBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class DangNhapActivity : AppCompatActivity() {
    private lateinit var binding:ActivityDangNhapBinding
    private val compositeDisposable = CompositeDisposable()
    private lateinit var apiBookStore: ApiBookStore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDangNhapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        apiBookStore = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBookStore::class.java)


        Event()
    }

    private fun Event() {
        binding.txtDangKy.setOnClickListener {
            val i = Intent(this, DangKiActivity::class.java)
            startActivity(i)
        }
        binding.btnDangNhap.setOnClickListener {
            val email = binding.txtEmail.text.toString().trim()
            val pass = binding.txtPass.text.toString().trim()

            if (email.isEmpty()){
                Toast.makeText(this,"Bạn chưa nhập email",Toast.LENGTH_SHORT).show()
            }else if (pass.isEmpty()){
                Toast.makeText(this,"Bạn chưa nhập mật khẩu",Toast.LENGTH_SHORT).show()
            }else{
                compositeDisposable.add(apiBookStore.dangnhap(email,pass)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({userModel ->
                        if (userModel.isSuccess){
                            Utils.isUserLoggedIn = true
                            Utils.User_current = userModel.result.get(0)
                            val i = Intent(this,MainActivity::class.java)
                            startActivity(i)
                            finish()
                        }else if (!isValidEmail(email)){
                            Toast.makeText(this,"Vui lòng nhập đúng định dạng mail ",Toast.LENGTH_SHORT).show()
                        }
                        else{
                            Toast.makeText(this,"Tài khoản hoặc mật khẩu không chính xác vui lòng kiểm tra",Toast.LENGTH_SHORT).show()
                        }
                    },{e ->
                        Toast.makeText(this, e.message,Toast.LENGTH_SHORT).show()
                    }

                    ))
            }
        }
        binding.txtQuenMk.setOnClickListener {
            val i = Intent(this,ResetPassActivity::class.java)
            startActivity(i)
        }
    }

    override fun onResume() {
        super.onResume()
        if (Utils.User_current.email != null && Utils.User_current.pass != null){
            binding.txtEmail.setText(Utils.User_current.username)
            binding.txtPass.setText(Utils.User_current.pass)
        }
    }
    fun isValidEmail(email: String):Boolean{
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }
}