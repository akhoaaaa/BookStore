package com.khoa.bookstore.activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.khoa.bookstore.Retrofit.ApiBookStore
import com.khoa.bookstore.Retrofit.RetrofitClient
import com.khoa.bookstore.Utils.Utils
import com.khoa.bookstore.databinding.ActivityThanhToanBinding
import com.khoa.bookstore.model.GioHang
import com.khoa.bookstore.model.SanPhamMoi
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.text.DecimalFormat

class ThanhToanActivity : AppCompatActivity() {
    private lateinit var binding:ActivityThanhToanBinding
    private var compositeDisposable = CompositeDisposable()
    private var sanPhamMoi = SanPhamMoi()
    private var gioHang = GioHang()
    private lateinit var apiBookStore: ApiBookStore
    private var tongtien = 0.0
    private var totalItem:Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThanhToanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        apiBookStore = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBookStore::class.java)

        val decimalFormat = DecimalFormat("###,###,###")
        tongtien = intent.getDoubleExtra("tongtien",0.0)
        binding.txtTienThanhToan.text = decimalFormat.format(tongtien)
        binding.txtEmailThanhToan.text = Utils.User_current.email
        binding.txtSdtThanhToan.text = Utils.User_current.sdt

        binding.tbThanhToan.apply {
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            binding.tbThanhToan.setNavigationOnClickListener {
                finish()
            }
        }

        Item()
        addEvent()

    }

    private fun Item() {
        totalItem = 0
        for (i in 0 until Utils.listgiohang.size){
            totalItem += Utils.listgiohang[i].soluong

        }
    }

    private fun addEvent() {
        binding.btnDatHang.setOnClickListener {
            val soLuongTrongGioHang = Utils.listgiohang.sumBy { it.soluong }
            val soluongtonkho = Utils.listgiohang.sumBy { it.soluongtonkho }
            if (soLuongTrongGioHang <= soluongtonkho){
                var diachi= binding.edtDiaChi.text.toString().trim()
                if (TextUtils.isEmpty(diachi)){
                    Toast.makeText(this,"bạn vui lòng nhập địa chỉ nhận hàng",Toast.LENGTH_SHORT).show()
                }else{
                    //post data
                    val email = Utils.User_current.email
                    val sdt = Utils.User_current.sdt
                    val id = Utils.User_current.id
                    Log.d("Test", Gson().toJson(Utils.listgiohang))
                    compositeDisposable.add(apiBookStore.createOrder(email,sdt,tongtien,id,diachi,totalItem,Gson().toJson(Utils.listgiohang))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe( { userModel ->
                            if(userModel.isSuccess){
                                Toast.makeText(applicationContext, "Thành công", Toast.LENGTH_SHORT).show()
                                val i = Intent(this, MainActivity::class.java)
                                startActivity(i)
                                Utils.listgiohang.clear()
                                finish()

                            }
                        },{throwable ->
                            Toast.makeText(this,throwable.message,Toast.LENGTH_SHORT).show()
                        }
                        ))

                }
            }else{
                Toast.makeText(this, "Sản phẩm bạn mua đã hết hoặc không còn  số lượng", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }
}