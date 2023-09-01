    package com.khoa.bookstore.adapter;

    import android.content.Context;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.ImageView;
    import android.widget.ProgressBar;
    import android.widget.TextView;

    import androidx.annotation.NonNull;
    import androidx.recyclerview.widget.RecyclerView;

    import com.bumptech.glide.Glide;
    import com.khoa.bookstore.R;
    import com.khoa.bookstore.model.SanPhamMoi;

    import java.text.DecimalFormat;
    import java.util.List;



    public class SachAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        Context context;
        List<SanPhamMoi> array;
        private static final int VIEW_TYPE_DATA = 0;
        private static final int VIEW_TYPE_LOADING = 1;

        public SachAdapter(Context context, List<SanPhamMoi> array) {
            this.context = context;
            this.array = array;
        }


        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            if (viewType == VIEW_TYPE_DATA){
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sach,parent,false);
                return new MyViewHolder(view);
            }else {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading,parent,false);
                return new LoadingViewHolder(view);
            }
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int index) {
            if (holder instanceof MyViewHolder){
                MyViewHolder myViewHolder = (MyViewHolder) holder;
                SanPhamMoi sanPham = array.get(index);
                myViewHolder.tensp.setText(sanPham.getTensp());
                myViewHolder.mota.setText(sanPham.getMota());
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                myViewHolder.giasp.setText("Giá: "+decimalFormat.format(sanPham.getGiasp())+"Đ");
                Glide.with(context).load(sanPham.getHinhanh()).into(((MyViewHolder) holder).imgHinhAnh);
            }else {
                LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
                loadingViewHolder.progressBar.setIndeterminate(true);
            }

        }

        @Override
        public int getItemViewType(int position) {
            return array.get(position) == null ? VIEW_TYPE_LOADING:VIEW_TYPE_DATA;
        }

        @Override
        public int getItemCount() {
            return array.size();
        }

        public class LoadingViewHolder extends RecyclerView.ViewHolder{
            ProgressBar progressBar;

            public LoadingViewHolder(@NonNull View itemView) {
                super(itemView);
                progressBar = itemView.findViewById(R.id.progressbar);
            }
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
