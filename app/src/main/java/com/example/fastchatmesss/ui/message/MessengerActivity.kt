package com.example.fastchatmesss.ui.message

import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import com.example.fastchatmesss.R
import com.example.fastchatmesss.base.BaseActivity
import com.example.fastchatmesss.data.User
import com.example.fastchatmesss.ui.login.LoginActivity
import com.example.fastchatmesss.ui.newmessage.NewMessageActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_messenger.*


class MessengerActivity : BaseActivity(R.layout.activity_messenger) {
    private val messengerAdapter by lazy {
        MessengerAdapter { user ->
            // cick vào 1 item chuyển sang nd messs
            val intent = Intent(this, NewMessageActivity::class.java)
            intent.putExtra(KEYGUARD_SERVICE, user)
            startActivity(intent)
        }
    }

    override fun initViews() {
        // kiểm tra nếu uild == null, tức là người dùng chưa đăng nhập, thì back lại màn hình Login
        val uid = FirebaseAuth.getInstance().uid
        if (uid == null) {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or((Intent.FLAG_ACTIVITY_NEW_TASK))
            startActivity(intent)
        }
        showLoading()
        FirebaseDatabase.getInstance().getReference("/users")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val usersList = mutableListOf<User>()
                    snapshot.children.forEach {

                        val user = it.getValue(User::class.java)
                        user?.let { it1 ->
                            usersList.add(it1)
                        }
                    }
                    messengerAdapter.usersList = usersList
                    hideLoading()
                }

                override fun onCancelled(error: DatabaseError) {
                    hideLoading()
                }
            })

        listUser.apply {
            adapter = messengerAdapter
        }

    }

    override fun initActions() {

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_new_message -> {
                val intent = Intent(this, NewMessageActivity::class.java)
                startActivity(intent)
            }
            R.id.menu_sign_out -> {
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or((Intent.FLAG_ACTIVITY_NEW_TASK))
                startActivity(intent)
            }
        }

        return super.onOptionsItemSelected(item)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

}
