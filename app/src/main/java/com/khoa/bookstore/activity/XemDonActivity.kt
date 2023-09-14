package com.khoa.bookstore.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.khoa.bookstore.R
import com.khoa.bookstore.Retrofit.ApiBookStore
import com.khoa.bookstore.Retrofit.RetrofitClient
import com.khoa.bookstore.Utils.Utils
import com.khoa.bookstore.databinding.ActivityXemDonBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class XemDonActivity : AppCompatActivity() {
    private lateinit var binding: ActivityXemDonBinding
    private var compositeDisposable = CompositeDisposable()
    private lateinit var apiBookStore: ApiBookStore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityXemDonBinding.inflate(layoutInflater)
        setContentView(binding.root)
        apiBookStore = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBookStore::class.java)
        compositeDisposable.add(apiBookStore.xemDonHang(Utils.User_current.id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {donHangModel->
                    Toast.makeText(this, donHangModel.result[0].item[0].tensp,Toast.LENGTH_SHORT).show()

                },{e ->

                }
            ))
    }
}