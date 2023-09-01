package com.khoa.bookstore.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.bumptech.glide.Glide
import com.khoa.bookstore.databinding.ActivityChiTietBinding
import com.khoa.bookstore.model.SanPhamMoi
import java.text.DecimalFormat

class ChiTietActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChiTietBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChiTietBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tbChiTiet.apply {
            title = "Chi tiết sản phẩm"
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            setNavigationOnClickListener {
                finish()
            }
        }
        inData()
    }

    private fun inData() {
        val sanPhamMoi:SanPhamMoi = intent.getSerializableExtra("chitiet") as SanPhamMoi
        binding.txtTenSp.setText(sanPhamMoi.tensp)
        binding.txtMoTaChiTiet.setText(sanPhamMoi.mota)
        Glide.with(applicationContext).load(sanPhamMoi.hinhanh).into(binding.imgGioHang)
        val decimalFormat:DecimalFormat = DecimalFormat("###,###,###")
        binding.txtGiaSp.setText("Giá: "+decimalFormat.format(sanPhamMoi.giasp)+"Đ")
        val so = listOf<Int>(1,2,3,4,5,6,7,8,9,10)
        val adapterspin = ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,so)
        binding.spinner.adapter = adapterspin
    }
}