package org.wit.tripshare.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import org.wit.tripshare.databinding.CardRoadtripBinding
import org.wit.tripshare.models.RoadtripModel

interface RoadtripListener {
    fun onRoadtripClick(roadtrip: RoadtripModel)
    fun onRoadtripButtonClick(roadtrip: RoadtripModel)
}

class RoadtripAdapter constructor(
    private var roadtrips: List<RoadtripModel>,
    private val listener: RoadtripListener
) :
    RecyclerView.Adapter<RoadtripAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardRoadtripBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val roadtrip = roadtrips[holder.adapterPosition]
        holder.bind(roadtrip, listener)
    }

    override fun getItemCount(): Int = roadtrips.size

    class MainHolder(private val binding: CardRoadtripBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(roadtrip: RoadtripModel, listener: RoadtripListener) {
            binding.roadtripTitle.text = roadtrip.roadtripTitle
            binding.roadtripDescription.text = roadtrip.roadtripDescription
            binding.roadtripHighlights.text = roadtrip.roadtripHighlights
            binding.roadtripLowlights.text = roadtrip.roadtripLowlights
            binding.roadtripDates.text = roadtrip.roadtripDates
            binding.roadtripRatingBar.rating = roadtrip.roadtripRating
            Picasso.get().load(roadtrip.roadtripImage).resize(200,200).into(binding.roadtripImageIcon)
//            Glide.with(binding.root)
//                .load(destination.image)
//                .override(200,200)
//                .into(binding.imageIcon)
            binding.root.setOnClickListener { listener.onRoadtripClick(roadtrip) }
            binding.roadtripOpenButton.setOnClickListener {
                listener.onRoadtripButtonClick(roadtrip)
            }
        }
    }
}