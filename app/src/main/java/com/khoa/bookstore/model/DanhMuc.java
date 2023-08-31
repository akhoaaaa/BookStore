package com.khoa.bookstore.model;

public class DanhMuc {
    int id;
    String tendanhmuc;
    String hinhanh;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTendanhmuc() {
        return tendanhmuc;
    }

    public void setTensanpham(String tensanpham) {
        this.tendanhmuc = tensanpham;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }
}
