package com.khoa.bookstore.Utils;

import com.khoa.bookstore.model.GioHang;
import com.khoa.bookstore.model.User;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static final String BASE_URL="http://192.168.5.18:8080/bookstore/";
    public static List<GioHang> listgiohang;
    public static User User_current = new User();
    public static Boolean isUserLoggedIn = false;
    public static List<GioHang> listmuahang = new ArrayList<>();


}