package com.example.fastchatmesss.ui.newmessage

import com.example.fastchatmesss.R
import com.example.fastchatmesss.base.BaseActivity
import kotlinx.android.synthetic.main.activity_new_message.*

class NewMessageActivity : BaseActivity(R.layout.activity_new_message) {
    private val newMessageAdapter by lazy { NewMessageAdapter() }


    override fun initViews() {
        recyViewNewMesss.apply {
            adapter = newMessageAdapter
        }

        val dataFake = mutableListOf<String>(
            "Linhtesst",
            "Linhtesst",
            "Linhtesst",
            "Linhtesst",
            "Linhtesst",
            "Linhtesst",
            "Linhtesst",
            "Linhtesst",
            "Linhtesst",
            "Linhtesst",
        )
        newMessageAdapter.data = dataFake
    }

    override fun initActions() {
    }

}




