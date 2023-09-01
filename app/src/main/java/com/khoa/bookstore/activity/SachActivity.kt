package com.khoa.bookstore.activity

import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
    private lateinit var apiBook: ApiBookStore
    private val compositeDisposable = CompositeDisposable()

    private lateinit var sachAdapter: SachAdapter
    private var listSanPham = mutableListOf<SanPhamMoi?>()
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var handler: Handler
    private var page = 1
    var loai: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySachBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loai = intent.getIntExtra("loai", 1)

        apiBook = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBookStore::class.java)
        linearLayoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        binding.reSach.layoutManager = linearLayoutManager
        binding.reSach.setHasFixedSize(true)
        sachAdapter = SachAdapter(this, listSanPham)
        binding.reSach.adapter = sachAdapter

        handler = Handler()

        binding.tbSach.apply {
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            setNavigationOnClickListener {
                finish()
            }
        }
        getData(page)
    }



    private fun getData(page: Int) {
        compositeDisposable.add(apiBook.getSach(page, loai)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ sanPhamMoiModel ->
                if (sanPhamMoiModel.isSuccess()) {
                        listSanPham.clear()
                        listSanPham.addAll(sanPhamMoiModel.result)
                        sachAdapter.notifyDataSetChanged()
                }
            }, {
                Toast.makeText(this, "Không kết nối tới server", Toast.LENGTH_SHORT).show()
            })
        )
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }
}