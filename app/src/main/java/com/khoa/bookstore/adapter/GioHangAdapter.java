package com.khoa.bookstore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.khoa.bookstore.Interface.IImageClickListenner;
import com.khoa.bookstore.R;
import com.khoa.bookstore.model.EventBus.TinhTongEvent;
import com.khoa.bookstore.model.GioHang;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.List;

public class GioHangAdapter extends RecyclerView.Adapter<GioHangAdapter.MyViewHolder> {
    Context context;
    List<GioHang> gioHangList;

    public GioHangAdapter(Context context, List<GioHang> gioHangList) {
        this.context = context;
        this.gioHangList = gioHangList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_giohang,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int index) {
        GioHang gioHang = gioHangList.get(index);
        holder.item_giohang_tensp.setText(gioHang.getTensp());
        holder.item_giohang_soluong.setText(gioHang.getSoluong() + " ");
        Glide.with(context).load(gioHang.getHinhanh()).into(holder.item_giohang_img);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.item_giohang_giasp.setText(decimalFormat.format(gioHang.getGiasp()));
        holder.item_giohang_giasp2.setText(decimalFormat.format(gioHang.getSoluong() * gioHang.getGiasp()));
        holder.setListenner(new IImageClickListenner() {
            @Override
            public void onImageClick(@Nullable View view, int pos, int giatri) {
                if (giatri == 1 ){
                    if (gioHangList.get(pos).getSoluong()>1){
                        int soluongmoi = gioHangList.get(pos).getSoluong()-1;
                        gioHangList.get(pos).setSoluong(soluongmoi);
                    }
                }else if (giatri == 2){
                    if (gioHangList.get(pos).getSoluong() < 11){
                        int soluongmoi = gioHangList.get(pos).getSoluong()+1;
                        gioHangList.get(pos).setSoluong(soluongmoi);
                    }
                }holder.item_giohang_soluong.setText(gioHangList.get(pos).getSoluong() + " ");
                double gia = gioHangList.get(pos).getSoluong() * gioHangList.get(pos).getGiasp();
                holder.item_giohang_giasp2.setText(decimalFormat.format(gia));
                EventBus.getDefault().postSticky(new TinhTongEvent());
            }
        });
    }

    @Override
    public int getItemCount() {
        return gioHangList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView item_giohang_img,item_giohang_cong,item_giohang_tru;
        TextView item_giohang_tensp,item_giohang_giasp,item_giohang_giasp2,item_giohang_soluong;
        IImageClickListenner listenner;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item_giohang_img = itemView.findViewById(R.id.item_giohang_img);
            item_giohang_tensp = itemView.findViewById(R.id.item_giohang_tensp);
            item_giohang_giasp = itemView.findViewById(R.id.item_giohang_giasp);
            item_giohang_giasp2 = itemView.findViewById(R.id.item_giohang_giasp2);
            item_giohang_soluong = itemView.findViewById(R.id.item_giohang_soluong);
            item_giohang_cong = itemView.findViewById(R.id.item_giohang_cong);
            item_giohang_tru = itemView.findViewById(R.id.item_giohang_tru);
            item_giohang_cong.setOnClickListener(this);
            item_giohang_tru.setOnClickListener(this);
        }

        public void setListenner(IImageClickListenner listenner) {
            this.listenner = listenner;
        }

        @Override
        public void onClick(View view) {
            if (view == item_giohang_tru){
                //tru
                listenner.onImageClick(view,getAdapterPosition(),1);
            }else if (view == item_giohang_cong){
                //cong
                listenner.onImageClick(view,getAdapterPosition(),2);
            }
        }
    }
}
