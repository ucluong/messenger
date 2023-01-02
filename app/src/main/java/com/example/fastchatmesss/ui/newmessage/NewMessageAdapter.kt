package com.example.fastchatmesss.ui.newmessage

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fastchatmesss.R
import com.example.fastchatmesss.data.ChatMessage

import com.example.fastchatmesss.data.User
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.item_me.view.*
import kotlinx.android.synthetic.main.item_user_new_message.view.*
import kotlinx.android.synthetic.main.item_you.view.*

const val VIEW_TYPE_ME = 1
const val VIEW_TYPE_YOU = 2

class NewMessageAdapter(val onClick: (User) -> Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var data = mutableListOf<ChatMessage>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_ME -> {
                val view =
                    LayoutInflater.from(parent.context).inflate(R.layout.item_me, parent, false)
                ViewHolderMe(view)
            }
            else -> {
                val view =
                    LayoutInflater.from(parent.context).inflate(R.layout.item_you, parent, false)
                ViewHolderYou(view)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (data[position].fromId == FirebaseAuth.getInstance().uid) {
            VIEW_TYPE_ME
        } else {
            VIEW_TYPE_YOU
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder.itemViewType){
            VIEW_TYPE_ME -> {(holder as? ViewHolderMe)?.bind(data[position])}
            VIEW_TYPE_YOU -> {(holder as? ViewHolderYou)?.bind(data[position])}
        }
    }

    override fun getItemCount(): Int = data.size



    inner class ViewHolderMe(view: View  ) : RecyclerView.ViewHolder(view) {
        fun bind(item: ChatMessage) {
            itemView.tvMessageMe.text = item.text
        }
    }

    inner class ViewHolderYou(view: View, ) : RecyclerView.ViewHolder(view) {
        fun bind(item: ChatMessage) {
            itemView.tvMessageYou.text = item.text
            // load our user
            Glide.with(itemView.context)
                .load(item)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(itemView.circleImageView)
        }
    }
}