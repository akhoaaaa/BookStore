package com.khoa.bookstore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.khoa.bookstore.R;
import com.khoa.bookstore.model.Item;

import java.text.DecimalFormat;
import java.util.List;

public class ChiTietAdapter extends RecyclerView.Adapter<ChiTietAdapter.MyViewHolder> {
    List <Item> itemList;
    Context context;

    public ChiTietAdapter(List<Item> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chitiet,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Item item = itemList.get(position);
        holder.txtTen.setText(item.getTensp() + "");
        holder.txtSoLuong.setText("Số Lượng "+item.getSoluong());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtGia.setText("Tổng Giá: "+ decimalFormat.format(item.getGia() * item.getSoluong()) +"đ");
        Glide.with(context).load(item.getHinhanh()).into(holder.imageChiTiet);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageChiTiet;
        TextView txtTen,txtSoLuong,txtGia;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageChiTiet = itemView.findViewById(R.id.item_ImageChiTiet);
            txtTen = itemView.findViewById(R.id.item_TenSpChiTiet);
            txtSoLuong = itemView.findViewById(R.id.item_SoLuongChiTiet);
            txtGia = itemView.findViewById(R.id.item_GiaChiTiet);

        }
    }
}
