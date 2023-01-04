package org.wit.tripshare.ui.roadtriplist

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.wit.tripshare.R
import org.wit.tripshare.adapters.RoadtripAdapter
import org.wit.tripshare.adapters.RoadtripClickListener
import org.wit.tripshare.databinding.FragmentRoadtripBinding
import org.wit.tripshare.databinding.FragmentRoadtripListBinding
import org.wit.tripshare.main.MainApp
import org.wit.tripshare.models.RoadtripModel

class RoadtripListFragment : Fragment(), RoadtripClickListener {

    lateinit var app: MainApp
    private var _fragBinding: FragmentRoadtripListBinding? = null
    private val fragBinding get() = _fragBinding!!
    private lateinit var roadtripListViewModel: RoadtripListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragBinding = FragmentRoadtripListBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        //activity?.title = getString(R.string.action_roadtrip_list)
        setupMenu()
        fragBinding.recyclerView.layoutManager = LinearLayoutManager(activity)
        roadtripListViewModel = ViewModelProvider(this).get(RoadtripListViewModel::class.java)
        roadtripListViewModel.observableRoadtripsList.observe(viewLifecycleOwner, Observer {
                roadtrips ->
            roadtrips?.let { render(roadtrips) }
        })

        val fab: FloatingActionButton = fragBinding.fab
        fab.setOnClickListener {
            val action = RoadtripListFragmentDirections.actionRoadtripListFragmentToRoadtripFragment()
            findNavController().navigate(action)
        }
        return root
    }

    private fun setupMenu() {
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onPrepareMenu(menu: Menu) {
                // Handle for example visibility of menu items
            }

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_roadtrip_list, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Validate and handle the selected menu item
                return NavigationUI.onNavDestinationSelected(menuItem,
                    requireView().findNavController())
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun render(roadtripsList: List<RoadtripModel>) {
        fragBinding.recyclerView.adapter = RoadtripAdapter(roadtripsList,this)
        if (roadtripsList.isEmpty()) {
            fragBinding.recyclerView.visibility = View.GONE
            fragBinding.roadtripsNotFound.visibility = View.VISIBLE
        } else {
            fragBinding.recyclerView.visibility = View.VISIBLE
            fragBinding.roadtripsNotFound.visibility = View.GONE
        }
    }

    override fun onRoadtripClick(roadtrip: RoadtripModel) {
        val action = RoadtripListFragmentDirections.actionRoadtripListFragmentToRoadtripDetailFragment(roadtrip.id)
        findNavController().navigate(action)
    }

    override fun onResume() {
        super.onResume()
        roadtripListViewModel.load()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }
}