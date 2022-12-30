package com.example.fastchatmesss.ui.message

import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import com.example.fastchatmesss.R
import com.example.fastchatmesss.base.BaseActivity
import com.example.fastchatmesss.ui.signup.RegisterActivity
import com.google.firebase.auth.FirebaseAuth

class MessengerActivity : BaseActivity(R.layout.activity_messenger) {
    override fun initViews() {
        val uid = FirebaseAuth.getInstance().uid
        if (uid== null){
            val intent = Intent(this, RegisterActivity::class.java)
            intent.flags= Intent.FLAG_ACTIVITY_CLEAR_TASK.or((Intent.FLAG_ACTIVITY_NEW_TASK))
            startActivity(intent)
        }
    }

    override fun initActions() {

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            R.id.menu_new_message->{
//                val intent= Intent(this,NewMessageActivity::class.java )
//                startActivity(intent)

            }
            R.id.menu_sign_out->{
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this, RegisterActivity::class.java)
                intent.flags= Intent.FLAG_ACTIVITY_CLEAR_TASK.or((Intent.FLAG_ACTIVITY_NEW_TASK))
                startActivity(intent)
            }
        }

        return super.onOptionsItemSelected(item)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

}
