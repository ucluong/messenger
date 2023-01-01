package com.example.fastchatmesss.ui.login


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.fastchatmesss.R
import com.example.fastchatmesss.base.BaseActivity
import com.example.fastchatmesss.data.User
import com.example.fastchatmesss.ui.message.MessengerActivity
import com.example.fastchatmesss.ui.signup.RegisterActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : BaseActivity(R.layout.activity_login) {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth

    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val intent = Intent(this, MessengerActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }

    override fun initActions() {
        btnLogin.setOnClickListener {
            showLoading()
            val email = edtLoginEmail.text.toString()
            val password = edtLoginPasss.text.toString()
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    hideLoading()
                    if (task.isSuccessful) {
                        val intent = Intent(this, MessengerActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                    } else {
                        Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
        }
        tvBackTo.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    override fun putExtra(keyData: String, it: User) {
        TODO("Not yet implemented")
    }

    override fun initToolbar() {

    }

    override fun initViews() {

    }

}
