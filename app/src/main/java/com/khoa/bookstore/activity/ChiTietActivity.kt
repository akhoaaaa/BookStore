package com.khoa.bookstore.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.bumptech.glide.Glide
import com.khoa.bookstore.Retrofit.ApiBookStore
import com.khoa.bookstore.Retrofit.RetrofitClient
import com.khoa.bookstore.Utils.Utils
import com.khoa.bookstore.databinding.ActivityChiTietBinding
import com.khoa.bookstore.model.GioHang
import com.khoa.bookstore.model.SanPhamMoi
import com.khoa.bookstore.model.User
import com.khoa.bookstore.model.UserModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.text.DecimalFormat

class ChiTietActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChiTietBinding
    private lateinit var sanPhamMoi:SanPhamMoi
    private lateinit var apiBookStore: ApiBookStore
    private val compositeDisposable = CompositeDisposable()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChiTietBinding.inflate(layoutInflater)
        setContentView(binding.root)

        apiBookStore = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBookStore::class.java)
        binding.tbChiTiet.apply {
            title = "Chi tiết sản phẩm"
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            setNavigationOnClickListener {
                finish()
            }
        }

        inData()
        initControl()
    }

    private fun initControl() {
        binding.btnThemVaoGioHang.setOnClickListener {
            if (Utils.isUserLoggedIn == false){
                val i = Intent(this,DangNhapActivity::class.java)
                startActivity(i)
            }else{
                themGioHang()
            }
        }
    }

    private fun themGioHang() {
        if (Utils.listgiohang.size > 0){
            var flag = false
            val soluong:Int = binding.spinner.selectedItem.toString().toInt()
            for (i in 0 until Utils.listgiohang.size){
                if (Utils.listgiohang[i].id == sanPhamMoi.id){
                    Utils.listgiohang[i].soluong = soluong + Utils.listgiohang.get(i).soluong
                    val giasp = sanPhamMoi.giasp * Utils.listgiohang.get(i).soluong
                    Utils.listgiohang[i].giasp = giasp
                    flag = true
                }
            }
            if (!flag){
                val giasp = sanPhamMoi.giasp * soluong
                val gioHang:GioHang = GioHang()
                gioHang.giasp = giasp
                gioHang.soluong = soluong
                gioHang.id = sanPhamMoi.id
                gioHang.tensp = sanPhamMoi.tensp
                gioHang.hinhanh = sanPhamMoi.hinhanh
                Utils.listgiohang.add(gioHang)
            }

        }else{
            val soluong:Int = binding.spinner.selectedItem.toString().toInt()
            val giasp = sanPhamMoi.giasp * soluong
            val gioHang:GioHang = GioHang()
            gioHang.giasp = giasp
            gioHang.soluong = soluong
            gioHang.id = sanPhamMoi.id
            gioHang.tensp = sanPhamMoi.tensp
            gioHang.hinhanh = sanPhamMoi.hinhanh
            Utils.listgiohang.add(gioHang)
        }
        var totalItem = 0
        for (i in 0 until Utils.listgiohang.size){
            totalItem = totalItem + Utils.listgiohang.get(i).soluong
        }
        if (Utils.isUserLoggedIn == false){
            binding.badge.setText("0")
        }else{
            binding.badge.setText(totalItem.toString())
        }
    }

    private fun inData() {
        sanPhamMoi = intent.getSerializableExtra("chitiet") as SanPhamMoi
        binding.txtTenSp.setText(sanPhamMoi.tensp)
        binding.txtMoTaChiTiet.setText(sanPhamMoi.mota)
        Glide.with(applicationContext).load(sanPhamMoi.hinhanh).into(binding.imgGioHang)
        val decimalFormat:DecimalFormat = DecimalFormat("###,###,###")
        binding.txtGiaSp.setText("Giá: "+decimalFormat.format(sanPhamMoi.giasp)+"Đ")
        val so = listOf<Int>(1,2,3,4,5,6,7,8,9,10)
        val adapterspin = ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,so)
        binding.spinner.adapter = adapterspin
        binding.frameGioHang.setOnClickListener {
            if (Utils.isUserLoggedIn == false){
                binding.badge.setText("0")
                val i1 = Intent(this,DangNhapActivity::class.java)
                startActivity(i1)
            }else{
                val i = Intent(this,GioHangActivity::class.java)
                startActivity(i)
            }
        }

        if (Utils.listgiohang != null){
            var totalItem = 0
            for (i in 0 until Utils.listgiohang.size){
                totalItem = totalItem + Utils.listgiohang.get(i).soluong
            }
            if (Utils.isUserLoggedIn == false){
                binding.badge.setText("0")
            }else{
                binding.badge.setText(totalItem.toString())
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (Utils.listgiohang != null){
            var totalItem = 0
            for (i in 0 until Utils.listgiohang.size){
                totalItem = totalItem + Utils.listgiohang.get(i).soluong
            }
            if (Utils.isUserLoggedIn == false){
                binding.badge.setText("0")
            }else{
                binding.badge.setText(totalItem.toString())
            }
        }
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }
}