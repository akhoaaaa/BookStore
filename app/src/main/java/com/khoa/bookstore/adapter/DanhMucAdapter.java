package com.khoa.bookstore.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.khoa.bookstore.R;
import com.khoa.bookstore.Utils.Utils;
import com.khoa.bookstore.activity.DangNhapActivity;
import com.khoa.bookstore.model.DanhMuc;

import java.util.List;

public class DanhMucAdapter extends BaseAdapter {
    List<DanhMuc> array;
    Context context;

    public DanhMucAdapter(List<DanhMuc> array, Context context) {
        this.array = array;
        this.context = context;
    }

    @Override
    public int getCount() {
        return array.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    public class ViewHolder{
        TextView txtDanhMuc;
        ImageView imgDanhMuc;
        LinearLayout layoutLog;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(view == null ) {
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.item_danhmuc, null);
            viewHolder.txtDanhMuc = view.findViewById(R.id.txtDanhMuc);
            viewHolder.imgDanhMuc = view.findViewById(R.id.imgDanhMuc);


            view.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.txtDanhMuc.setText(array.get(i).getTendanhmuc());
        Glide.with(context).load(array.get(i).getHinhanh()).into((viewHolder.imgDanhMuc));
        return view;
    }

}
