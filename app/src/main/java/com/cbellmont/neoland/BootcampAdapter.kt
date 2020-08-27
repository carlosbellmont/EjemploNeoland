package com.cbellmont.neoland

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cbellmont.neoland.datamodel.BootcampWithCampus

class BootcampAdapter : RecyclerView.Adapter<BootcampAdapter.BootcampViewHolder>() {

    private var bootcamps = listOf<BootcampWithCampus>()

    class BootcampViewHolder(var root: View, var tvName: TextView, var tvAbout: TextView) : RecyclerView.ViewHolder(root)

    fun updateBootcamps(bootcamps : List<BootcampWithCampus>){
        this.bootcamps = bootcamps
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BootcampViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_item_bootcamp, parent, false)
        val tvAbout = view.findViewById<TextView>(R.id.tvAbout)
        val tvName = view.findViewById<TextView>(R.id.tvName)

        return BootcampViewHolder(view, tvName, tvAbout)
    }

    override fun getItemCount(): Int {
        return bootcamps.size
    }

    override fun onBindViewHolder(holder: BootcampViewHolder, position: Int) {
        holder.tvName.text = String.format("%s (%s)", bootcamps[position].bootcamp.bootcampName, bootcamps[position].campus.campusName)
        holder.tvAbout.text = bootcamps[position].bootcamp.about
        holder.root.setOnClickListener {
            it.context.startActivity(UsersActivity.getIntent(it.context, bootcamps[position].bootcamp.bootcampId, UsersActivity.UsersActivityType.BOOTCAMP))
        }
    }

}
