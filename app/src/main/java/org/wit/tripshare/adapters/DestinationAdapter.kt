package org.wit.tripshare.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import org.wit.tripshare.databinding.CardDestinationBinding
import org.wit.tripshare.models.DestinationModel

interface DestinationListener {
    fun onDestinationClick(destination: DestinationModel)
}

class DestinationAdapter constructor(
    private var destinations: List<DestinationModel>,
    private val listener: DestinationListener
) :
    RecyclerView.Adapter<DestinationAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardDestinationBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val destination = destinations[holder.adapterPosition]
        holder.bind(destination, listener)
    }

    override fun getItemCount(): Int = destinations.size

    class MainHolder(private val binding: CardDestinationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(destination: DestinationModel, listener: DestinationListener) {
            binding.destinationTitle.text = destination.title
            binding.description.text = destination.description
            binding.pros.text = destination.pros
            binding.cons.text = destination.cons
            binding.dateArrived.text = destination.dateArrived
            binding.ratingBar.rating = destination.rating
            Picasso.get().load(destination.image).resize(200,200).into(binding.imageIcon)
//            Glide.with(binding.root)
//                .load(destination.image)
//                .override(200,200)
//                .into(binding.imageIcon)
            binding.root.setOnClickListener { listener.onDestinationClick(destination) }
        }
    }
}
