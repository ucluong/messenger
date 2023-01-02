package com.example.fastchatmesss.ui.newmessage

import com.example.fastchatmesss.R
import com.example.fastchatmesss.base.BaseActivity
import com.example.fastchatmesss.data.ChatMessage
import com.example.fastchatmesss.data.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_new_message.*


class NewMessageActivity : BaseActivity(R.layout.activity_new_message) {
    val messagesList = mutableListOf<ChatMessage>()
    private val user by lazy { intent.getSerializableExtra(KEYGUARD_SERVICE) as? User }

    private val newMessageAdapter by lazy {
        NewMessageAdapter {

        }
    }

    override fun initViews() {
        newMessageAdapter.user = user
        recyViewNewMesss.apply {
            adapter = newMessageAdapter
        }
        supportActionBar?.title = user?.username
        listenForMessages()
    }

    override fun initActions() {
        btnSend.setOnClickListener {
            performSendMesage()
        }

    }

    private fun listenForMessages() {
        val ref = FirebaseDatabase.getInstance().getReference("/user-messages/${FirebaseAuth.getInstance().uid}/${user?.uid}")
        ref.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                hideLoading()
                val chatMessage = snapshot.getValue(ChatMessage::class.java)
                if (chatMessage != null) {
                    messagesList.add(chatMessage)
                }
                newMessageAdapter.data = messagesList
            }

            override fun onCancelled(error: DatabaseError) {

            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onChildRemoved(snapshot: DataSnapshot) {

            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

            }
        })
    }

    private fun performSendMesage() {
        // how do we actually send a message to firebase...
        // khai báo gửi messages lên firebase
        if (edtMessage.text.toString().isEmpty()) return
        showLoading()
        val text = edtMessage.text.toString()  //
        //from id là người gửi
        val fromId = FirebaseAuth.getInstance().uid
        //toId là người nhận
        val toId = user?.uid.toString()
        if (fromId == null) return
//        val refaerence = FirebaseDatabase.getInstance().getReference("messages").push()    //
        val refaerence = FirebaseDatabase.getInstance().getReference("/user-messages/$fromId/$toId").push()    //
        val toRefaerence = FirebaseDatabase.getInstance().getReference("/user-messages/$toId/$fromId").push()    //
        val chatMessage = refaerence.key?.let { ChatMessage(it, text, fromId, toId, System.currentTimeMillis()) }
        refaerence.setValue(chatMessage)  //
        toRefaerence.setValue(chatMessage)  //
        edtMessage.setText("")
    }

}




