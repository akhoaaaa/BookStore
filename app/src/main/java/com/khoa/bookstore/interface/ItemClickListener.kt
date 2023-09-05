package com.khoa.bookstore.Interface

import android.view.View

interface ItemClickListener {
    fun onClick(view: View?, pos: Int, isLongClick: Boolean)
}