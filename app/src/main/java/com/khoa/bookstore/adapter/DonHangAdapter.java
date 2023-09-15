package com.khoa.bookstore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.khoa.bookstore.R;
import com.khoa.bookstore.Utils.Utils;
import com.khoa.bookstore.model.DonHang;

import java.text.DecimalFormat;
import java.util.List;

public class DonHangAdapter extends RecyclerView.Adapter<DonHangAdapter.MyViewHolder> {
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    Context context;
    List<DonHang> listDonHang;

    public DonHangAdapter(Context context, List<DonHang> listDonHang) {
        this.context = context;
        this.listDonHang = listDonHang;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_donhang,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int index) {
        DonHang donHang = listDonHang.get(index);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtDonHang.setText("Đơn hàng: "+donHang.getId());
        holder.txtTongTien.setText("Tổng tiền: " + decimalFormat.format(donHang.getTongtien()) + "đ");
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                holder.reChiTiet.getContext(),
                LinearLayoutManager.VERTICAL,
                false
        );
        layoutManager.setInitialPrefetchItemCount(donHang.getItem().size());
        //adapter chitiet
        ChiTietAdapter chiTietAdapter = new ChiTietAdapter(donHang.getItem(),context);
        holder.reChiTiet.setLayoutManager(layoutManager);
        holder.reChiTiet.setAdapter(chiTietAdapter);
        holder.reChiTiet.setRecycledViewPool(viewPool);
    }

    @Override
    public int getItemCount() {
        return listDonHang.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtDonHang,txtTongTien;
        RecyclerView reChiTiet;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTongTien = itemView.findViewById(R.id.txtTongTienDonHang);
            txtDonHang = itemView.findViewById(R.id.txtDonHang);
            reChiTiet = itemView.findViewById(R.id.reChiTiet);
        }
    }
}
