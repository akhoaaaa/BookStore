    package com.khoa.bookstore.adapter;

    import android.app.Activity;
    import android.app.AlertDialog;
    import android.content.Context;
    import android.content.DialogInterface;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.CheckBox;
    import android.widget.CompoundButton;
    import android.widget.ImageView;
    import android.widget.TextView;

    import androidx.annotation.NonNull;
    import androidx.annotation.Nullable;
    import androidx.recyclerview.widget.RecyclerView;

    import com.bumptech.glide.Glide;
    import com.khoa.bookstore.Interface.IImageClickListenner;
    import com.khoa.bookstore.R;
    import com.khoa.bookstore.Utils.Utils;
    import com.khoa.bookstore.activity.GioHangActivity;
    import com.khoa.bookstore.model.EventBus.TinhTongEvent;
    import com.khoa.bookstore.model.GioHang;
    import com.khoa.bookstore.model.SanPhamMoi;

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
            holder.soluongtonkho.setText("Còn lại: "+gioHang.getSoluongtonkho());
            holder.item_giohang_giasp2.setText(decimalFormat.format(gioHang.getSoluong() * gioHang.getGiasp()));
            holder.item_giohang_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b){
                        Utils.listmuahang.add(gioHang);
                        EventBus.getDefault().postSticky(new TinhTongEvent());
                    }else {
                        for (int i=0;i<Utils.listmuahang.size();i++){
                            if (Utils.listmuahang.get(i).getId() == gioHang.getId()){
                                Utils.listmuahang.remove(i);
                                EventBus.getDefault().postSticky(new TinhTongEvent());
                            }
                        }
                    }
                }
            });



            holder.setListenner(new IImageClickListenner() {
                @Override
                public void onImageClick(@Nullable View view, int pos, int giatri) {
                    if (giatri == 1 ){
                        if (gioHangList.get(pos).getSoluong()>1){
                            if (gioHangList.get(pos).getSoluongtonkho() <gioHangList.get(pos).getSoluong()){
                                int soluongmoi = gioHang.getSoluongtonkho();
                                gioHangList.get(pos).setSoluong(soluongmoi);
                            }
                            int soluongmoi = gioHangList.get(pos).getSoluong()-1;
                            gioHangList.get(pos).setSoluong(soluongmoi);
                            holder.item_giohang_soluong.setText(gioHangList.get(pos).getSoluong() + " ");
                            double gia = gioHangList.get(pos).getSoluong() * gioHangList.get(pos).getGiasp();
                            holder.item_giohang_giasp2.setText(decimalFormat.format(gia));
                            EventBus.getDefault().postSticky(new TinhTongEvent());
                        }else if (gioHangList.get(pos).getSoluong() == 1){
                            AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                            builder.setTitle("Thông Báo");
                            builder.setMessage("Bạn muốn xóa sản phẩm khỏi giỏ hàng ?");
                            builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Utils.listgiohang.remove(pos);
                                    notifyDataSetChanged();
                                    EventBus.getDefault().postSticky(new TinhTongEvent());
                                }
                            });
                            builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            builder.show();

                        }
                    }else if (giatri == 2){
                        if (gioHangList.get(pos).getSoluong() < gioHangList.get(pos).getSoluongtonkho()){
                            int soluongmoi = gioHangList.get(pos).getSoluong()+1;
                            gioHangList.get(pos).setSoluong(soluongmoi);
                        }else if (gioHangList.get(pos).getSoluongtonkho() <gioHangList.get(pos).getSoluong()){
                            int soluongmoi = gioHang.getSoluongtonkho();
                            gioHangList.get(pos).setSoluong(soluongmoi);
                        }
                        holder.item_giohang_soluong.setText(gioHangList.get(pos).getSoluong() + " ");
                        double gia = gioHangList.get(pos).getSoluong() * gioHangList.get(pos).getGiasp();
                        holder.item_giohang_giasp2.setText(decimalFormat.format(gia));
                        EventBus.getDefault().postSticky(new TinhTongEvent());
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return gioHangList.size();
        }


        public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            ImageView item_giohang_img,item_giohang_cong,item_giohang_tru;
            TextView item_giohang_tensp,item_giohang_giasp,item_giohang_giasp2,item_giohang_soluong,soluongtonkho;
            IImageClickListenner listenner;
            CheckBox item_giohang_check;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                soluongtonkho = itemView.findViewById(R.id.item_giohang_soluongtonkho);
                item_giohang_img = itemView.findViewById(R.id.item_giohang_img);
                item_giohang_tensp = itemView.findViewById(R.id.item_giohang_tensp);
                item_giohang_giasp = itemView.findViewById(R.id.item_giohang_giasp);
                item_giohang_giasp2 = itemView.findViewById(R.id.item_giohang_giasp2);
                item_giohang_soluong = itemView.findViewById(R.id.item_giohang_soluong);
                item_giohang_cong = itemView.findViewById(R.id.item_giohang_cong);
                item_giohang_tru = itemView.findViewById(R.id.item_giohang_tru);
                item_giohang_cong.setOnClickListener(this);
                item_giohang_tru.setOnClickListener(this);
                item_giohang_check = itemView.findViewById(R.id.item_giohang_check);
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
