package com.example.fastchatmesss.ui.message

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fastchatmesss.R
import com.example.fastchatmesss.data.User
import kotlinx.android.synthetic.main.activity_regisrer.*
import kotlinx.android.synthetic.main.item_user_new_message.view.*

class MessengerAdapter (val onClick : (User) -> Unit):RecyclerView.Adapter<MessengerAdapter.ViewHolder>() {
    var usersList = mutableListOf<User>()
    @SuppressLint("NotifyDataSetChanged")
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user_new_message,parent,false)
        return ViewHolder(view)
       
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(usersList[position])
        
    }

    override fun getItemCount(): Int {
        return usersList.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(user:User){
            itemView.setOnClickListener {
                onClick.invoke(user)
            }

            itemView.tvUsername.text = user.username
            Glide.with(itemView.context)
                .load(user.imageUri)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(itemView.imageUser)
        }
    }

}


