package com.khoa.bookstore.Retrofit;

import com.khoa.bookstore.model.DanhMucModel;
import com.khoa.bookstore.model.SanPhamMoiModel;
import com.khoa.bookstore.model.UserModel;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiBookStore {

    //get data
    @GET("getdanhmuc.php")
    Observable<DanhMucModel> getDanhMuc();

    @GET("getsanpham.php")
    Observable<SanPhamMoiModel> getSanPham();


    @POST("chitietdanhmuc.php")
    @FormUrlEncoded
    Observable<SanPhamMoiModel> getSach(
            @Field("loai") int loai
    );

    @POST("dangki.php")
    @FormUrlEncoded
    Observable<UserModel> dangki(
            @Field("email") String email,
            @Field("pass") String pass,
            @Field("sdt") String sdt,
            @Field("username") String username

    );

    @POST("dangnhap.php")
    @FormUrlEncoded
    Observable<UserModel> dangnhap(
            @Field("email") String email,
            @Field("pass") String pass
    );

}
