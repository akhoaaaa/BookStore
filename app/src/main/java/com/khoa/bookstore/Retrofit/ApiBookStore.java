package com.khoa.bookstore.Retrofit;

import com.khoa.bookstore.model.DanhMucModel;
import com.khoa.bookstore.model.DonHang;
import com.khoa.bookstore.model.DonHangModel;
import com.khoa.bookstore.model.SanPhamMoi;
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

    @POST("resetpass.php")
    @FormUrlEncoded
    Observable<UserModel> repass(
            @Field("email") String email
    );

    @POST("donhang.php")
    @FormUrlEncoded
    Observable<UserModel> createOrder(
            @Field("email") String email,
            @Field("sdt") String sdt,
            @Field("tongtien") double tongtien,
            @Field("iduser") int iduser,
            @Field("diachi") String diachi,
            @Field("soluong") int soluong,
            @Field("chitiet") String chitiet

    );
    @POST("xemdonhang.php")
    @FormUrlEncoded
    Observable<DonHangModel> xemDonHang(
            @Field("iduser") int id
    );
    @POST("timkiem.php")
    @FormUrlEncoded
    Observable<SanPhamMoiModel> search(
            @Field("search") String search
    );
}
