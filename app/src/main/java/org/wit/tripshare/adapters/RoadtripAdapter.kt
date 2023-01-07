package org.wit.tripshare.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.wit.tripshare.R
import org.wit.tripshare.databinding.CardRoadtripBinding
import org.wit.tripshare.models.RoadtripModel

interface RoadtripClickListener {
    fun onRoadtripClick(roadtrip: RoadtripModel)
}

class RoadtripAdapter constructor(private var roadtrips: ArrayList<RoadtripModel>,
                                  private val listener: RoadtripClickListener)
    : RecyclerView.Adapter<RoadtripAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardRoadtripBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val roadtrip = roadtrips[holder.adapterPosition]
        holder.bind(roadtrip,listener)
    }

    fun removeAt(position: Int) {
        roadtrips.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun getItemCount(): Int = roadtrips.size

    inner class MainHolder(val binding : CardRoadtripBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(roadtrip: RoadtripModel, listener: RoadtripClickListener) {
            binding.root.tag = roadtrip
            binding.roadtrip = roadtrip
            binding.imageIcon.setImageResource(R.mipmap.ic_launcher_round)
            binding.root.setOnClickListener { listener.onRoadtripClick(roadtrip) }
            binding.executePendingBindings()
        }
    }
}