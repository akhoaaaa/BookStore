package com.khoa.bookstore.adapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.khoa.bookstore.Interface.ItemClickListener;
import com.khoa.bookstore.R;
import com.khoa.bookstore.activity.ChiTietActivity;
import com.khoa.bookstore.model.SanPhamMoi;

import java.text.DecimalFormat;
import java.util.List;

public class SanPhamMoiAdapter extends RecyclerView.Adapter<SanPhamMoiAdapter.MyViewHolder> {
    Context context;
    List<SanPhamMoi> array;

    public SanPhamMoiAdapter(Context context, List<SanPhamMoi> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sanphammoi,parent,false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int index) {
        SanPhamMoi sanPhamMoi = array.get(index);
        holder.txtSanPham.setText(sanPhamMoi.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtGia.setText(("Giá: "+decimalFormat.format(sanPhamMoi.getGiasp())+"Đ"));
        Glide.with(context).load(sanPhamMoi.getHinhanh()).into(holder.imgSanPham);
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(@Nullable View view, int pos, boolean isLongClick) {
                if (!isLongClick){
                    //click
                    Intent intent = new Intent(context, ChiTietActivity.class);
                    intent.putExtra("chitiet",sanPhamMoi);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtSanPham,txtGia;
        ImageView imgSanPham,imgNew;
        private ItemClickListener itemClickListener;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtSanPham = itemView.findViewById(R.id.item_txtTenSanPham);
            txtGia = itemView.findViewById(R.id.item_txtGiaSanPham);
            imgSanPham = itemView.findViewById(R.id.item_imgSanPhamMoi);
            imgNew = itemView.findViewById(R.id.imgNew);
            itemView.setOnClickListener(this);


            ObjectAnimator animator1 = new ObjectAnimator();
            animator1 = ObjectAnimator.ofFloat(imgNew,"alpha",1f,0.5f);
            animator1.setDuration(500);
            animator1.setRepeatCount(ObjectAnimator.INFINITE);
            animator1.setRepeatMode(ObjectAnimator.REVERSE);
            animator1.start();
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view,getAdapterPosition(),false);
        }
    }
}
