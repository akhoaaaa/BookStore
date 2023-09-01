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
    import com.khoa.bookstore.model.SanPhamMoi;

    import java.text.DecimalFormat;
    import java.util.List;



    public class SachAdapter extends RecyclerView.Adapter<SachAdapter.MyViewHolder> {
        Context context;
        List<SanPhamMoi> array;

        public SachAdapter(Context context, List<SanPhamMoi> array) {
            this.context = context;
            this.array = array;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sach,parent,false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int index) {
            SanPhamMoi sanPham = array.get(index);
            holder.tensp.setText(sanPham.getTensp());
            holder.mota.setText(sanPham.getMota());
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            holder.giasp.setText("Giá: "+decimalFormat.format(sanPham.getGiasp())+"Đ");
            Glide.with(context).load(sanPham.getHinhanh()).into(holder.imgHinhAnh);
        }

        @Override
        public int getItemCount() {
            return array.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tensp,giasp,mota;
            ImageView imgHinhAnh;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                tensp = itemView.findViewById(R.id.itemS_Ten);
                giasp = itemView.findViewById(R.id.itemS_Gia);
                mota = itemView.findViewById(R.id.itemS_MoTa);
                imgHinhAnh = itemView.findViewById(R.id.itemS_img);
            }
        }
    }
