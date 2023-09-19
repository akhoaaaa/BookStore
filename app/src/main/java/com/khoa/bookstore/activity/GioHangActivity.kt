    package com.khoa.bookstore.activity

    import android.content.Intent
    import androidx.appcompat.app.AppCompatActivity
    import android.os.Bundle
    import android.view.View
    import android.widget.Toast
    import androidx.appcompat.app.AlertDialog
    import androidx.recyclerview.widget.LinearLayoutManager
    import androidx.recyclerview.widget.RecyclerView
    import androidx.recyclerview.widget.RecyclerView.LayoutManager
    import com.khoa.bookstore.R
    import com.khoa.bookstore.Retrofit.ApiBookStore
    import com.khoa.bookstore.Retrofit.RetrofitClient
    import com.khoa.bookstore.Utils.Utils
    import com.khoa.bookstore.adapter.GioHangAdapter
    import com.khoa.bookstore.databinding.ActivityGioHangBinding
    import com.khoa.bookstore.model.EventBus.TinhTongEvent
    import com.khoa.bookstore.model.GioHang
    import com.khoa.bookstore.model.SanPhamMoi
    import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
    import io.reactivex.rxjava3.disposables.CompositeDisposable
    import io.reactivex.rxjava3.schedulers.Schedulers
    import org.greenrobot.eventbus.EventBus
    import org.greenrobot.eventbus.Subscribe
    import org.greenrobot.eventbus.ThreadMode
    import java.text.DecimalFormat

    class GioHangActivity : AppCompatActivity() {
        private lateinit var binding: ActivityGioHangBinding
        private lateinit var gioHangAdapter: GioHangAdapter
        private lateinit var gioHangList: List<GioHang>
        private var sanPhamMoi = SanPhamMoi()
        private lateinit var apiBookStore: ApiBookStore
        private var compositeDisposable = CompositeDisposable()
        private var tongtiensp:Double = 0.0

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityGioHangBinding.inflate(layoutInflater)
            setContentView(binding.root)

            binding.tbGioHang.title = "Giỏ Hàng"
            binding.tbGioHang.apply {
                setSupportActionBar(binding.tbGioHang)
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                setNavigationOnClickListener {
                    finish()
                }
            }
            binding.reGioHang.setHasFixedSize(true)
            binding.reGioHang.layoutManager = LinearLayoutManager(this)
            if (Utils.listgiohang.size == 0) {
                binding.txtGioHangTrong.visibility = View.VISIBLE
            } else {
                gioHangAdapter = GioHangAdapter(applicationContext, Utils.listgiohang)
                binding.reGioHang.adapter = gioHangAdapter
                binding.txtGioHangTrong.visibility = View.GONE
            }
            Event()
            if (Utils.listmuahang != null){
                Utils.listmuahang.clear()
            }
            tinhTongTien()
        }

        private fun Event() {
            if (sanPhamMoi.soluong>sanPhamMoi.soluongtonkho){
                binding.btnMuaHang.visibility = View.GONE
            }
            binding.btnMuaHang.setOnClickListener {

                val i = Intent(this, ThanhToanActivity::class.java)
                i.putExtra("tongtien", tongtiensp)
                Utils.listgiohang.clear()
                startActivity(i)
            }
        }


        private fun tinhTongTien() {
            tongtiensp = 0.0
            for (i in 0 until Utils.listmuahang.size){
                tongtiensp += (Utils.listmuahang[i].giasp * Utils.listmuahang[i].soluong)
            }
            val decimalFormat = DecimalFormat("###,###,###")
            binding.txtTongTien.text = decimalFormat.format(tongtiensp)
        }

        override fun onStart() {
            super.onStart()
            //register evenbus
            EventBus.getDefault().register(this)
        }

        override fun onStop() {
            EventBus.getDefault().unregister(this)
            super.onStop()
        }
        @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
        fun eventTinhTong(event:TinhTongEvent){
            if (event != null){
                tinhTongTien()
            }
        }
    }