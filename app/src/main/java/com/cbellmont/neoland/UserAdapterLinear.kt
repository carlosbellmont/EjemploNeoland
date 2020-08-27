package com.cbellmont.neoland

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cbellmont.neoland.datamodel.user.UserMostrable
import com.squareup.picasso.Picasso

class UserAdapterLinear : RecyclerView.Adapter<UserAdapterLinear.UserViewHolder>(), Updatable {

    private var users = listOf<UserMostrable>()

    class UserViewHolder(root: View, var tvName: TextView, var tvEmail: TextView, var imFoto: ImageView) : RecyclerView.ViewHolder(root)

    override fun updateData(users : List<UserMostrable>){
        this.users = users
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_item_user_linear, parent, false)
        val tvName = view.findViewById<TextView>(R.id.tvName)
        val tvEmail = view.findViewById<TextView>(R.id.tvEmail)
        val ivFoto = view.findViewById<ImageView>(R.id.ivFoto)
        return UserViewHolder(view, tvName, tvEmail, ivFoto)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.tvName.text = users[position].getNombreEnString()
        holder.tvEmail.text = users[position].getEmailEnString()
        Picasso.get().load(users[position].getFotoUrl()).into(holder.imFoto)
    }

}