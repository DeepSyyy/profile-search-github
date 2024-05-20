package com.example.profilegithubsearcher.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.profilegithubsearcher.data.local.entity.UserEntity
import com.example.profilegithubsearcher.databinding.ItemProfileBinding
import com.example.profilegithubsearcher.util.Utils

class FavoriteUserAdapter(private val listUser: List<UserEntity>, private var onItemClickCallback: OnItemClickCallback) :
    RecyclerView.Adapter<FavoriteUserAdapter.ListViewHolder>() {

    interface OnItemClickCallback {
        fun onItemClicked(data: UserEntity)
    }

    class ListViewHolder(var binding: ItemProfileBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemProfileBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = listUser.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val users = listUser[position]

        holder.binding.apply {
            tvUsername.text = users.login
            val avatar = users.avatarUrl
            Utils.glideBinding(holder.itemView.context, avatar.orEmpty(), ivAvatar)
            root.setOnClickListener {
                onItemClickCallback.onItemClicked(listUser[holder.adapterPosition])
            }
        }
    }

}