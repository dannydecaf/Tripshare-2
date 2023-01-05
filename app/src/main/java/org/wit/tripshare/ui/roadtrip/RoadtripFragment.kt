package org.wit.tripshare.ui.roadtrip

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import org.wit.tripshare.R
import org.wit.tripshare.databinding.FragmentRoadtripBinding
import org.wit.tripshare.models.RoadtripModel

class RoadtripFragment : Fragment() {

    //lateinit var app: DonationXApp
    var totalDonated = 0
    private var _fragBinding: FragmentRoadtripBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val fragBinding get() = _fragBinding!!
    //lateinit var navController: NavController
    private lateinit var roadtripViewModel: RoadtripViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //app = activity?.application as DonationXApp
        //navController = Navigation.findNavController(activity!!, R.id.nav_host_fragment)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _fragBinding = FragmentRoadtripBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        activity?.title = getString(R.string.action_roadtrip)
        setupMenu()
        roadtripViewModel = ViewModelProvider(this).get(RoadtripViewModel::class.java)
        roadtripViewModel.observableStatus.observe(viewLifecycleOwner, Observer {
                status -> status?.let { render(status) }
        })
        setButtonListener(fragBinding)

        return root;
    }

    private fun setupMenu() {
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onPrepareMenu(menu: Menu) {
                // Handle for example visibility of menu items
            }

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_add_roadtrip, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Validate and handle the selected menu item
                return NavigationUI.onNavDestinationSelected(menuItem,
                    requireView().findNavController())
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }


//    companion object {
//        @JvmStatic
//        fun newInstance() =
//                RoadtripFragment().apply {
//                    arguments = Bundle().apply {}
//                }
//    }

    private fun render(status: Boolean) {
        when (status) {
            true -> {
                view?.let {
                    findNavController().popBackStack()
                }
            }
            false -> Toast.makeText(context,getString(R.string.roadtripError),Toast.LENGTH_LONG).show()
        }
    }

    fun setButtonListener(layout: FragmentRoadtripBinding) {
        layout.roadtripButton.setOnClickListener {
            if (layout.roadtripTitle.text!!.isEmpty() or layout.roadtripDescription.text!!.isEmpty())
                Toast.makeText(context, "Enter Details!", Toast.LENGTH_LONG).show()
            else {
                roadtripViewModel.addRoadtrip(
                    loggedInViewModel.liveFirebaseUser,
                    RoadtripModel(
                        roadtripTitle = layout.roadtripTitle.text.toString(),
                        roadtripDescription = layout.roadtripDescription.text.toString(),
                        roadtripHighlights = layout.roadtripHighlights.text.toString(),
                        roadtripLowlights = layout.roadtripLowlights.text.toString(),
                        roadtripRating = layout.roadtripRatingBarInput.rating,
                        uid = loggedInViewModel.liveFirebaseUser.value?.uid!!
                    )
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }
}