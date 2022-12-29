package com.example.fastchatmesss.ui.signup

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import com.example.fastchatmesss.ui.message.MessengerActivity
import com.example.fastchatmesss.R
import com.example.fastchatmesss.base.BaseActivity
import com.example.fastchatmesss.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_regisrer.*

class RegisterActivity : BaseActivity(R.layout.activity_regisrer) {



    override fun initActions() {
        // code btnDangki
        btnDangki.setOnClickListener {
            val email = edtEmail.text.toString()
            val password = edtPass.text.toString()


            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "vui lòng nhập nội dung trong email/pass ", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
            Log.d("RegisterActivity", " Emmailis:" + email)
            Log.d("RegisterActivity", "Password:$password")

            // Xác thực Firebase để tạo người dùng bằng email mật khẩu
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (!it.isSuccessful) return@addOnCompleteListener
                    // eles if succerrful
                    Log.d(
                        "RegisterActivity",
                        "đã tạo thành công ng dùng với uid:${it.result.user?.uid}")

                    val intent= Intent(this, MessengerActivity::class.java)
                    intent.flags= Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)



                }
                .addOnFailureListener {
                    Toast.makeText(this, "ko thanhf coong :${it.message}", Toast.LENGTH_SHORT)
                        .show()
                }
        }

    }

    override fun initViews() {
        // code phần tvAlready
        tvAlready.setOnClickListener {
            Log.d("MainActivity", "Bạn đã có tài khoản")

            //
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)

        }

    }
    override fun initToolbar() {
        // code btn chọn ảnh avata
        btnPhoto.setOnClickListener {
            Log.d("RegisterActivity", " Try to whow photo selector")
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)

        }
    }

    var photoUri: Uri? = null

    //  // code btn chọn ảnh avata
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
            // tiếp tục và kiểm tra xem hình ảnh đã chọn là gì
            Log.d(" RegisterActivity", " photo was selected")
            photoUri = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, photoUri)
            val bitmapDrawable = BitmapDrawable(bitmap)
            btnPhoto.setBackgroundDrawable(bitmapDrawable)
        }
    }

    // code cho Username

    class  User(val uid:String, val username:String, val  imageUri:String)


}
