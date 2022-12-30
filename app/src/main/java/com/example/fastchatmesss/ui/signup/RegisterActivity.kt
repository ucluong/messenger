package com.example.fastchatmesss.ui.signup


import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.fastchatmesss.ui.message.MessengerActivity
import com.example.fastchatmesss.R
import com.example.fastchatmesss.base.BaseActivity
import com.example.fastchatmesss.data.User
import com.example.fastchatmesss.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_regisrer.*
import java.util.UUID

class RegisterActivity : BaseActivity(R.layout.activity_regisrer) {
    var selectPhotoUri : Uri? = null

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
            // Xác thực Firebase để tạo người dùng bằng email mật khẩu
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (!it.isSuccessful) return@addOnCompleteListener

                }

            uploadImageToFirebase()
        }


    }


    private fun uploadImageToFirebase(){
        if (selectPhotoUri== null)
            return

        val filename = UUID.randomUUID().toString()
      val  ref  = FirebaseStorage.getInstance().getReference("/images/$filename")
        ref.putFile(selectPhotoUri!!)
            .addOnSuccessListener {
                Log.d("Register", " Successfully uploaded image : ${it.metadata?.path}")
                ref.downloadUrl.addOnSuccessListener {
                    Log.d("RegisterActivity", " File location:$it ")

                    saveUserToFirabaseDatabase(it .toString())
                }
            }
            . addOnFailureListener{
                // do some loggin here
            }


    }



    override fun initViews() {
        // code phần tvAlready
        tvAlready.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

    }


    override fun initToolbar() {
        // code btn chọn ảnh avata
        btnPhoto.setOnClickListener {
            getContentImage.launch("image/*")
        }
    }

    private val getContentImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
       uri?.let {
           selectPhotoUri = uri
           val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
          btnPhoto.setImageBitmap(bitmap)
//           val bitmapDrawable = BitmapDrawable(bitmap)
//           btnPhoto.setBackgroundDrawable(bitmapDrawable)
       }
    }

    // code saveUserToFirabaseDatabase
    private fun saveUserToFirabaseDatabase(profileImageUri: String){
        val uid= FirebaseAuth.getInstance().uid ?:""
        val ref=  FirebaseDatabase.getInstance().getReference("/users/$uid")

        val user = User(uid, edtName.text.toString(),profileImageUri )
        ref.setValue(user)
            .addOnSuccessListener {
                Log.d("RegisterActivity", " Finallu we saved the user to Firebase Databes")

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

