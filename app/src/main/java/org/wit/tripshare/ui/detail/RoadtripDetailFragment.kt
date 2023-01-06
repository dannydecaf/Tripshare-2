package org.wit.tripshare.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import org.wit.tripshare.databinding.FragmentRoadtripDetailBinding
import timber.log.Timber

class RoadtripDetailFragment : Fragment() {

    private lateinit var detailViewModel: RoadtripDetailViewModel
    private val args by navArgs<RoadtripDetailFragmentArgs>()
    private var _fragBinding: FragmentRoadtripDetailBinding? = null
    private val fragBinding get() = _fragBinding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        _fragBinding = FragmentRoadtripDetailBinding.inflate(inflater, container, false)
        val root = fragBinding.root

        detailViewModel = ViewModelProvider(this).get(RoadtripDetailViewModel::class.java)
        detailViewModel.observableRoadtrip.observe(viewLifecycleOwner, Observer { render() })
        return root
    }

    private fun render() {
        fragBinding.roadtripvm = detailViewModel
        Timber.i("Retrofit fragBinding.roadtripvm == $fragBinding.roadtripvm")
    }

    override fun onResume() {
        super.onResume()
        detailViewModel.getRoadtrip(args.roadtripid)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }
}