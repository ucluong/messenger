package com.example.fastchatmesss.ui.newmessage

import android.util.Log
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

    private val newMessageAdapter by lazy {
        NewMessageAdapter(){}
    }



    override fun initViews() {
        recyViewNewMesss.apply {
            adapter = newMessageAdapter
        }
//
        val username = intent.getStringExtra(KEYGUARD_SERVICE)


        supportActionBar?.title = username
   //     setupDummyData()
        listenForMessages()
        btnSeand.setOnClickListener {
            performSendMesage()
        }

    }
    private fun listenForMessages(){
        val ref = FirebaseDatabase.getInstance().getReference("messages")
        ref.addChildEventListener(object :ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val chatMessage=  snapshot.getValue(ChatMessage::class.java)

                if (chatMessage!= null){
                }

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

    private fun performSendMesage(){
        // how do we actually send a message to firebase...
        // khai báo messages lên firebase
        val text= Edt_messageActivity.text.toString()  //
        val fromId = FirebaseAuth.getInstance().uid
        val username = intent.getStringExtra(KEYGUARD_SERVICE)
        val toId = Unit.toString()
        if ( fromId== null) return

        val refaerence = FirebaseDatabase.getInstance().getReference("messages").push()    //


        val chatMessage= refaerence.key?.let { ChatMessage(it,text,fromId,toId,System.currentTimeMillis()) }
        refaerence.setValue(chatMessage)  //



    }

    private fun setupDummyData() {
        val dataFake = mutableListOf<String>(

            "Linhtesst1",
            "Linhtesst",
            "tesst2",
            "tesst",
            "tesst3",
            "Tiện ích mở rộng Android dành cho người dùng có thể chuyển đổi",


            )
        newMessageAdapter.data = dataFake



    }

    override fun initActions() {


    }

    override fun putExtra(keyData: String, it: User) {
        TODO("Not yet implemented")
    }

}




