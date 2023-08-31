package com.khoa.bookstore.Retrofit;

import com.khoa.bookstore.model.DanhMucModel;
import com.khoa.bookstore.model.SanPhamMoiModel;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;

public interface ApiBookStore {

    //get data
    @GET("getdanhmuc.php")
    Observable<DanhMucModel> getDanhMuc();

    @GET("getsanpham.php")
    Observable<SanPhamMoiModel> getSanPham();
}
