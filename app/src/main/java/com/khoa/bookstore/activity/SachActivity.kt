package com.khoa.bookstore.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.khoa.bookstore.Retrofit.ApiBookStore
import com.khoa.bookstore.Retrofit.RetrofitClient
import com.khoa.bookstore.Utils.Utils
import com.khoa.bookstore.adapter.SachAdapter
import com.khoa.bookstore.databinding.ActivitySachBinding
import com.khoa.bookstore.model.SanPhamMoi
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers


class SachActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySachBinding
    private lateinit var apiBook:ApiBookStore
    private val compositeDisposable = CompositeDisposable()
    private lateinit var sachAdapter : SachAdapter
    private var listSanPham= mutableListOf<SanPhamMoi>()
    private val page = 1
    var loai: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySachBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loai = intent.getIntExtra("loai",1)

        apiBook = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBookStore::class.java)
        binding.reSach.layoutManager = LinearLayoutManager(this)
        binding.reSach.hasFixedSize()


        binding.tbSach.apply {
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            setNavigationOnClickListener {
                finish()
            }
        }
        getData()

    }

    private fun getData() {
        compositeDisposable.add(apiBook.getSach(page,loai)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ sanPhamMoiModel ->
                if (sanPhamMoiModel.isSuccess()){
                    listSanPham = sanPhamMoiModel.result
                    sachAdapter = SachAdapter(this,listSanPham)
                    binding.reSach.adapter = sachAdapter
                }
            },{
                Toast.makeText(this,"không kết nối tới server",Toast.LENGTH_SHORT).show()
             }
            ))
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }
}