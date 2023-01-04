package org.wit.tripshare.ui.roadtriplist

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import org.wit.tripshare.R
import org.wit.tripshare.activities.DestinationListActivity
import org.wit.tripshare.activities.RoadtripActivity
import org.wit.tripshare.adapters.RoadtripAdapter
import org.wit.tripshare.adapters.RoadtripListener
import org.wit.tripshare.databinding.FragmentRoadtripListBinding
import org.wit.tripshare.main.MainApp
import org.wit.tripshare.models.RoadtripModel

class RoadtripListFragment : Fragment(), RoadtripListener {

    private var _fragBinding: FragmentRoadtripListBinding? = null
    private val fragBinding get() = _fragBinding!!
    lateinit var loader: AlertDialog
    private val listPubsViewModel: ListPubsViewModel by activityViewModels()
    private val loggedInViewModel: LoggedInViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _fragBinding = FragmentRoadtripListBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        setupMenu()
        loader = createLoader(requireActivity())

        fragBinding.recyclerView.layoutManager = LinearLayoutManager(activity)


        return root;
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            RoadtripListFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}