package com.cbellmont.neoland

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cbellmont.neoland.datamodel.user.UserMostrable
import com.squareup.picasso.Picasso

interface Updatable {
    fun updateData(users : List<UserMostrable>)
}

class UserAdapterCompacted : RecyclerView.Adapter<UserAdapterCompacted.UserViewHolder>(), Updatable {

    private var users = listOf<UserMostrable>()

    class UserViewHolder(root: View, var tvName: TextView, var ivFoto: ImageView) : RecyclerView.ViewHolder(root)

    override fun updateData(users : List<UserMostrable>){
        this.users = users
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_item_user_compacted, parent, false)
        val tvName = view.findViewById<TextView>(R.id.tvName)
        val ivFoto = view.findViewById<ImageView>(R.id.ivFoto)
        return UserViewHolder(view, tvName, ivFoto)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.tvName.text = users[position].getNombreEnString()
        Picasso.get().load(users[position].getFotoUrl()).into(holder.ivFoto)
    }

}