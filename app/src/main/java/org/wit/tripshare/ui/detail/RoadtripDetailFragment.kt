package org.wit.tripshare.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import org.wit.tripshare.R

class RoadtripDetailFragment : Fragment() {

    companion object {
        fun newInstance() = RoadtripDetailFragment()
    }

    private lateinit var viewModel: RoadtripDetailViewModel
    private val args by navArgs<RoadtripDetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_roadtrip_detail, container, false)

        Toast.makeText(context,"Roadtrip ID Selected : ${args.roadtripid}",Toast.LENGTH_LONG).show()

        return view
    }
}