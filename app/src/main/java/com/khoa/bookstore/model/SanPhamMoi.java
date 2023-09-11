package com.khoa.bookstore.model;

import java.io.Serializable;

public class SanPhamMoi implements Serializable {
    int id;
    double giasp;
    String tensp;
    String hinhanh;
    String mota;
    int loai;
    int soluongtonkho;

    public int getSoluong() {
        return soluongtonkho;
    }

    public void setSoluong(int soluongtonkho) {
        this.soluongtonkho = soluongtonkho;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getGiasp() {
        return giasp;
    }

    public void setGiasp(double giasp) {
        this.giasp = giasp;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public int getLoai() {
        return loai;
    }

    public void setLoai(int loai) {
        this.loai = loai;
    }
}
