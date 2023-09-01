package com.khoa.bookstore.activity

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers

import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide

import com.khoa.bookstore.R
import com.khoa.bookstore.Retrofit.ApiBookStore
import com.khoa.bookstore.Retrofit.RetrofitClient
import com.khoa.bookstore.Utils.Utils
import com.khoa.bookstore.adapter.DanhMucAdapter
import com.khoa.bookstore.adapter.SanPhamMoiAdapter
import com.khoa.bookstore.databinding.ActivityMainBinding
import com.khoa.bookstore.model.DanhMuc
import com.khoa.bookstore.model.SanPhamMoi
import com.khoa.bookstore.model.SanPhamMoiModel
import io.reactivex.rxjava3.disposables.CompositeDisposable


class MainActivity : AppCompatActivity() {
    private val compositeDisposable = CompositeDisposable()
    private lateinit var binding: ActivityMainBinding
    private lateinit var apiBook: ApiBookStore
    lateinit var danhMucAdapter: DanhMucAdapter
    lateinit var sanPhamAdapter:SanPhamMoiAdapter
    private var listdanhmuc = mutableListOf<DanhMuc>()
    private var listsanpham = mutableListOf<SanPhamMoi>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        apiBook = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBookStore::class.java)






        binding.tbTrangChu.apply {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            setNavigationIcon(android.R.drawable.ic_menu_sort_by_size)
            setNavigationOnClickListener {
            binding.drTrangChu.openDrawer(GravityCompat.START)
            }
        }
        ViewFlipper()

        if (isConnected(this)){
            ViewFlipper()
            getLoaiDanhMuc()
            getSanPhamMoi()
            getDanhMuc()
        }else{
            Toast.makeText(this,"Không có internet, vui lòng kết nối",Toast.LENGTH_SHORT).show()
        }
    }

    private fun getDanhMuc() {
        binding.lvTrangChu.setOnItemClickListener { adapterView, view, i, l ->
            when (i) {
                0 -> {
                    val i0 = Intent (this,MainActivity::class.java)
                    startActivity(i0)
                }
                1 -> {
                    val i1 = Intent(this,SachActivity::class.java)
                    i1.putExtra("loai",1)
                    startActivity(i1)
                }
                2 -> {
                    val i2 = Intent(this,SachActivity::class.java)
                    i2.putExtra("loai",2)
                    startActivity(i2)
                }
            }
        }
    }

    private fun getSanPhamMoi() {
        compositeDisposable.add(apiBook.sanPham
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({sanPhamMoiModel ->
                if (sanPhamMoiModel.isSuccess()){
                    listsanpham = sanPhamMoiModel.result
                    sanPhamAdapter = SanPhamMoiAdapter(this, listsanpham)
                    binding.reView.adapter = sanPhamAdapter
                    binding.reView.layoutManager = GridLayoutManager(this,2)
                    binding.reView.hasFixedSize()
                }
            }, { error ->
                Toast.makeText(this, "Không thể kết nối với server: ${error.message}", Toast.LENGTH_SHORT).show()
            })
        )
    }

    private fun getLoaiDanhMuc() {
        compositeDisposable.add(apiBook.danhMuc
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({ danhMucModel ->
                if (danhMucModel.isSuccess) {
                    listdanhmuc = danhMucModel.result
                    //khởi tạo listview
                    danhMucAdapter = DanhMucAdapter(listdanhmuc,this)
                    binding.lvTrangChu.adapter = danhMucAdapter
                }
            },{
                    error ->
                Log.e("API Error", "Error fetching data: ${error.message}")
            }
        )
        )
    }


    private fun isConnected( context:Context):Boolean{
        val connectivityManager = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        val mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
        return wifi != null && wifi.isConnected || mobile!= null &&mobile.isConnected
    }

    private fun ViewFlipper() {
        val mangQuangCao:List<String> = listOf("http://mauweb.monamedia.net/thegioididong/wp-content/uploads/2017/12/banner-Le-hoi-phu-kien-800-300.png",
            "http://mauweb.monamedia.net/thegioididong/wp-content/uploads/2017/12/banner-HC-Tra-Gop-800-300.png",
        "http://mauweb.monamedia.net/thegioididong/wp-content/uploads/2017/12/banner-big-ky-nguyen-800-300.jpg")
        for (i in mangQuangCao) {
            val imageView = ImageView(this)
            Glide.with(this).load(i).into(imageView)
            imageView.scaleType = ImageView.ScaleType.FIT_XY
            binding.vfTrangChu.addView(imageView)
        }
        binding.vfTrangChu.flipInterval = 3000
        binding.vfTrangChu.isAutoStart = true
        val slideIn:Animation = AnimationUtils.loadAnimation(this, R.anim.slide_in_right)
        val slideOut:Animation = AnimationUtils.loadAnimation(this,R.anim.slide_out_right)
        binding.vfTrangChu.setInAnimation(slideIn)
        binding.vfTrangChu.setOutAnimation(slideOut)
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }
}