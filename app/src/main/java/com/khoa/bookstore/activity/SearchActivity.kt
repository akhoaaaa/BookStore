package com.khoa.bookstore.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.khoa.bookstore.R
import com.khoa.bookstore.Retrofit.ApiBookStore
import com.khoa.bookstore.Retrofit.RetrofitClient
import com.khoa.bookstore.Utils.Utils
import com.khoa.bookstore.adapter.SachAdapter
import com.khoa.bookstore.databinding.ActivitySearchBinding
import com.khoa.bookstore.model.SanPhamMoi
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class SearchActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySearchBinding
    private lateinit var apiBookStore:ApiBookStore
    private var compositeDisposable = CompositeDisposable()
    private lateinit var sanPhamMoiList:MutableList<SanPhamMoi>
    private lateinit var sachAdapter:SachAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val linearLayoutManager:LinearLayoutManager = LinearLayoutManager(this)
        binding.reTimKiem.setHasFixedSize(true)
        binding.reTimKiem.layoutManager = linearLayoutManager
        sanPhamMoiList = mutableListOf()

        apiBookStore = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBookStore::class.java)

        binding.edtTimKiem.addTextChangedListener{
            if(binding.edtTimKiem.length() == 0){
                sanPhamMoiList.clear()
                sachAdapter = SachAdapter(this,sanPhamMoiList)
                binding.reTimKiem.adapter = sachAdapter
            }else{
                getSearch()
            }
        }


        binding.tbTimKiem.apply {
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            setNavigationOnClickListener {
                finish()
            }
        }
    }

    private fun getSearch() {
        sanPhamMoiList.clear()
        val search = binding.edtTimKiem.text.toString().trim()
        compositeDisposable.add(apiBookStore.search(search)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                sanPhamMoiModel ->
                if (sanPhamMoiModel.isSuccess){
                    sanPhamMoiList = sanPhamMoiModel.result
                    sachAdapter = SachAdapter(this,sanPhamMoiList)
                    binding.reTimKiem.adapter = sachAdapter
                }else{
                    sanPhamMoiList.clear()
                    sachAdapter.notifyDataSetChanged()
                }

            },{e->
                Toast.makeText(this,e.message,Toast.LENGTH_SHORT).show()
            }

            ))
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }
}