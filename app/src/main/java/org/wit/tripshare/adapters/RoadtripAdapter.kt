package org.wit.tripshare.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import org.wit.tripshare.R
import org.wit.tripshare.databinding.CardRoadtripBinding
import org.wit.tripshare.models.RoadtripModel
import org.wit.tripshare.utils.customTransformation

interface RoadtripClickListener {
    fun onRoadtripClick(roadtrip: RoadtripModel)
}

class RoadtripAdapter constructor(private var roadtrips: ArrayList<RoadtripModel>,
                                  private val listener: RoadtripClickListener,
                                  private val readOnly: Boolean)
    : RecyclerView.Adapter<RoadtripAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardRoadtripBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding,readOnly)
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

    inner class MainHolder(val binding : CardRoadtripBinding, private val readOnly : Boolean) : RecyclerView.ViewHolder(binding.root) {

        val readOnlyRow = readOnly

        fun bind(roadtrip: RoadtripModel, listener: RoadtripClickListener) {
            binding.root.tag = roadtrip
            binding.roadtrip = roadtrip
            Picasso.get().load(roadtrip.profilepic.toUri())
                .resize(200, 200)
                .transform(customTransformation())
                .centerCrop()
                .into(binding.imageIcon)
            binding.root.setOnClickListener { listener.onRoadtripClick(roadtrip) }
            binding.executePendingBindings()
        }
    }
}