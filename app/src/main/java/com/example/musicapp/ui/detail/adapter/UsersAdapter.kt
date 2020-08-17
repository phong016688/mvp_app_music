package com.example.musicapp.ui.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.R
import com.example.musicapp.databinding.ItemLoadingBinding
import com.example.musicapp.databinding.ItemUserBinding
import com.example.musicapp.domain.model.User

class DiffUtilUser : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem == newItem
    }
}

class UsersAdapter : ListAdapter<User, RecyclerView.ViewHolder>(DiffUtilUser()) {
    var isLoading = false

    //1: user 2: loading
    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount - 1 && isLoading) 2 else 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 1) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_user, parent, false)
            ItemUserHolder(ItemUserBinding.bind(view))
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_loading, parent, false)
            ItemLoadingHolder(ItemLoadingBinding.bind(view))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val user = currentList[position]
        (holder as? ItemUserHolder)?.bind(user)
    }

    class ItemUserHolder(private val viewBinding: ItemUserBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(user: User) {
            viewBinding.userName.text = user.userName
        }
    }

    class ItemLoadingHolder(viewBinding: ItemLoadingBinding) :
        RecyclerView.ViewHolder(viewBinding.root)
}