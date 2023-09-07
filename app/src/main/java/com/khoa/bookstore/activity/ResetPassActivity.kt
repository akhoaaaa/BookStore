package com.khoa.bookstore.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.khoa.bookstore.R
import com.khoa.bookstore.Retrofit.ApiBookStore
import com.khoa.bookstore.Retrofit.RetrofitClient
import com.khoa.bookstore.Utils.Utils
import com.khoa.bookstore.databinding.ActivityResetPassBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class ResetPassActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResetPassBinding
    private var compositeDisposable = CompositeDisposable()
    private lateinit var apiBookStore: ApiBookStore
    private var isResetButtonClicked = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResetPassBinding.inflate(layoutInflater)
        setContentView(binding.root)

        apiBookStore = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBookStore::class.java)


        binding.btnResetPass.setOnClickListener {
            val email = binding.txtResetPass.text.toString().trim()
            if (!isResetButtonClicked) {
                isResetButtonClicked = true
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(this, "Bạn chưa nhập mail", Toast.LENGTH_SHORT).show()
                } else {
                    binding.progressReset.visibility = View.VISIBLE
                    compositeDisposable.add(apiBookStore.repass(email)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ userModel ->
                            if (userModel.isSuccess) {
                                Toast.makeText(this, userModel.message, Toast.LENGTH_SHORT).show()
                                val i = Intent(this, DangNhapActivity::class.java)
                                startActivity(i)
                                finish()
                            } else {
                                Toast.makeText(this, userModel.message, Toast.LENGTH_SHORT).show()
                            }
                            binding.progressReset.visibility = View.INVISIBLE
                        }, { e ->
                            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                            binding.progressReset.visibility = View.INVISIBLE
                        }
                        ))

                }
            }else if (TextUtils.isEmpty(email)){
                Toast.makeText(this, "Bạn chưa nhập mail", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this, "Vui lòng đợi hoàn thành tác vụ trước khi thử lại.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}