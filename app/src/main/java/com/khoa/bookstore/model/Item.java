package com.khoa.bookstore.model;

public class Item {
    int idsp;
    String tensp;
    int soluong;
    double giasp;
    String hinhanh;

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public double getGia() {
        return giasp;
    }

    public void setGia(double gia) {
        this.giasp = gia;
    }

    public int getIdsp() {
        return idsp;
    }

    public void setIdsp(int idsp) {
        this.idsp = idsp;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }
}
