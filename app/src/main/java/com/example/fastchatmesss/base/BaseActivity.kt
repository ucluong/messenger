package com.example.fastchatmesss.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity(layoutId: Int) : AppCompatActivity(layoutId) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        initActions()
        initToolbar()

    }



    open fun  initToolbar(){

    }

    //khởi tạo views
    abstract fun initViews()
    //xử lý sự kiện nút bấm
    abstract fun initActions()

}