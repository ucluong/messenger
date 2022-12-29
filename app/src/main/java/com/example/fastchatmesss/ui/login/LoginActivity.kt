package com.example.fastchatmesss.ui.login


import android.util.Log
import com.example.fastchatmesss.R
import com.example.fastchatmesss.base.BaseActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity(R.layout.activity_login) {

    override fun initActions() {
        btnLogin.setOnClickListener {
            val email= edtLoginEmail.text.toString()
            val pass= edtLoginPasss.text.toString()
            Log.d("Login", "Attempt login with email/pw: $email/****" )
        }
        tvBackTo.setOnClickListener {
            finish()
        }

    }


    override fun initToolbar() {

    }

    override fun initViews() {

    }




}
