package com.example.gridu_unittestscourse_capstoneproject.ui.users.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gridu_unittestscourse_capstoneproject.R
import com.example.gridu_unittestscourse_capstoneproject.data.model.UserDetails
import com.example.gridu_unittestscourse_capstoneproject.databinding.ItemUserBinding

class UsersAdapter : RecyclerView.Adapter<UsersAdapter.ViewHolder>() {

    private val userDetailsList = arrayListOf<UserDetails>()

    var onItemClick: (Int) -> Unit = { id: Int -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(userDetailsList[position])
    }

    override fun getItemCount() = userDetailsList.size

    fun setUserList(userDetailsList: List<UserDetails>) {
        this.userDetailsList.apply {
            clear()
            addAll(userDetailsList)
        }
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemUserBinding.bind(itemView)

        fun onBind(userDetails: UserDetails) {
            binding.apply {
                with(userDetails) {
                    nameTextView.text = name
                    loginTextView.text = login
                    bioTextView.text = bio
                    repositoriesCounterTextView.text = public_repos.toString()
                    Glide.with(itemView.context)
                        .load(avatar_url)
                        .into(avatarImageView)
                }
                root.setOnClickListener {
                    onItemClick.invoke(userDetails.id)
                }
            }
        }
    }
}