package com.example.fastchatmesss.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.fastchatmesss.data.User
import com.example.fastchatmesss.utils.LoadingProgress

abstract class BaseActivity(layoutId: Int) : AppCompatActivity(layoutId) {

    private var loadingProgress: LoadingProgress? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        initActions()
        initToolbar()
    }

    open fun initToolbar(){

    }

    //khởi tạo views
    abstract fun initViews()
    //xử lý sự kiện nút bấm
    abstract fun initActions()

    fun showLoading() {
        if (loadingProgress == null) {
            loadingProgress = LoadingProgress(this)
        }
        loadingProgress?.let {
            if (!it.isShowing) {
                it.show()
            }
        }
    }

    fun hideLoading() {
        try {
            loadingProgress?.let {
                it.dismiss()
                loadingProgress = null
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}