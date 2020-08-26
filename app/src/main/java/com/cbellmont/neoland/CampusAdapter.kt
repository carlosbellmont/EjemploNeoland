package com.cbellmont.neoland

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cbellmont.neoland.datamodel.campus.Campus

class CampusAdapter : RecyclerView.Adapter<CampusAdapter.CampusViewHolder>() {

    private var campus = listOf<Campus>()

    class CampusViewHolder(root: View, var tvCampus: TextView, var imCampus: ImageView) : RecyclerView.ViewHolder(root)

    fun updateCampus(campus : List<Campus>){
        this.campus = campus
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CampusViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_item_campus, parent, false)
        val tvCampus = view.findViewById<TextView>(R.id.tvCampus)
        val imCampus = view.findViewById<ImageView>(R.id.ivCampus)

        return CampusViewHolder(view, tvCampus, imCampus)
    }

    override fun getItemCount(): Int {
        return campus.size
    }

    override fun onBindViewHolder(holder: CampusViewHolder, position: Int) {
        holder.tvCampus.text = campus[position].name
        holder.imCampus.setImageResource(campus[position].image)
    }

}
