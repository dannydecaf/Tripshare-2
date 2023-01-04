package org.wit.tripshare.ui.map

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.GoogleMap
package org.wit.tripshare.R
import org.wit.tripshare.databinding.ContentDestinationMapsBinding
import org.wit.tripshare.databinding.FragmentMapBinding
import org.wit.tripshare.main.MainApp
import org.wit.tripshare.models.DestinationModel

class MapFragment : Fragment() {
        lateinit var app: MainApp
        lateinit var map: GoogleMap

private var _fragBinding: FragmentMapBinding? = null
private val fragBinding get() = _fragBinding!!
        val destinations = DestinationModel()
private lateinit var contentBinding: ContentDestinationMapsBinding

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        }

        override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
        ): View? {
        /** inflate fragment_guitar */
        _fragBinding = FragmentMapBinding.inflate(inflater, container, false)
        /** Tie into the root (overall layout) */
        val root = fragBinding.root
        activity?.title = getString(R.string.action_maps)
        return root;
        }
        }
