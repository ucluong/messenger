package com.example.fastchatmesss.ui.message

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fastchatmesss.R
import com.example.fastchatmesss.data.User
import kotlinx.android.synthetic.main.item_user_new_message.view.*

class MessgerAdapte :RecyclerView.Adapter<MessgerAdapte.ViewHolder>() {

    var user = mutableListOf<User>()

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(user:User){
            itemView.tvUsername.text = user.username

        }

      
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user_new_message,parent,false)
        return ViewHolder(view)
       
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder .bind(user[position])
        
    }

    override fun getItemCount(): Int {
        return user.size
    }


}


