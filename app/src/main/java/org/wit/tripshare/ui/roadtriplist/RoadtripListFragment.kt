package org.wit.tripshare.ui.roadtriplist

import android.app.AlertDialog
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
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.wit.tripshare.R
import org.wit.tripshare.adapters.RoadtripAdapter
import org.wit.tripshare.adapters.RoadtripClickListener
import org.wit.tripshare.databinding.FragmentRoadtripListBinding
import org.wit.tripshare.main.MainApp
import org.wit.tripshare.models.RoadtripModel
import org.wit.tripshare.utils.SwipeToDeleteCallback
import org.wit.tripshare.utils.createLoader
import org.wit.tripshare.utils.hideLoader
import org.wit.tripshare.utils.showLoader

class RoadtripListFragment : Fragment(), RoadtripClickListener {

    lateinit var app: MainApp
    private var _fragBinding: FragmentRoadtripListBinding? = null
    private val fragBinding get() = _fragBinding!!
    private lateinit var roadtripListViewModel: RoadtripListViewModel
    lateinit var loader: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragBinding = FragmentRoadtripListBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        setupMenu()
        loader = createLoader(requireActivity())

        fragBinding.recyclerView.layoutManager = LinearLayoutManager(activity)
        roadtripListViewModel = ViewModelProvider(this).get(RoadtripListViewModel::class.java)
        showLoader(loader,"Downloading Roadtrips")
        roadtripListViewModel.observableRoadtripsList.observe(viewLifecycleOwner, Observer { roadtrips ->
            roadtrips?.let {
                render(roadtrips as ArrayList<RoadtripModel>)
                hideLoader(loader)
                checkSwipeRefresh()
            }
        })

        fragBinding.fab.setOnClickListener {
            val action = RoadtripListFragmentDirections.actionRoadtripListFragmentToRoadtripFragment()
            findNavController().navigate(action)
        }

        setSwipeRefresh()

        val swipeDeleteHandler = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                showLoader(loader, "Deleting Roadtrip")
                val adapter = fragBinding.recyclerView.adapter as RoadtripAdapter
                adapter.removeAt(viewHolder.adapterPosition)
                roadtripListViewModel.delete(viewHolder.itemView.tag as String)//.uid!!
                hideLoader(loader)
            }
        }
        val itemTouchDeleteHelper = ItemTouchHelper(swipeDeleteHandler)
        itemTouchDeleteHelper.attachToRecyclerView(fragBinding.recyclerView)

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

    private fun render(roadtripsList: ArrayList<RoadtripModel>) {
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
        val action = RoadtripListFragmentDirections.actionRoadtripListFragmentToRoadtripDetailFragment(roadtrip.uid!!)
        findNavController().navigate(action)
    }

    fun setSwipeRefresh() {
        fragBinding.swiperefresh.setOnRefreshListener {
            fragBinding.swiperefresh.isRefreshing = true
            showLoader(loader,"Downloading Roadtrips")
            roadtripListViewModel.load()
        }
    }

    fun checkSwipeRefresh() {
        if (fragBinding.swiperefresh.isRefreshing)
            fragBinding.swiperefresh.isRefreshing = false
    }

//    override fun onResume() {
//        super.onResume()
//        showLoader(loader, "Downloading Roadtrips")
//        loggedInViewModel.liveFirebaseUser.observe(viewLifecycleOwner, Observer { firebaseUser ->
//            if (firebaseUser != null) {
//                roadtripListViewModel.liveFirebaseUser.value = firebaseUser
//                roadtripListViewModel.load()
//            }
//        })
//        //hideLoader(loader)
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }
}