package org.wit.tripshare.ui.destination

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.button.MaterialButton
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import org.wit.tripshare.R
import org.wit.tripshare.databinding.FragmentDestinationBinding
import org.wit.tripshare.helpers.showImagePicker
import org.wit.tripshare.main.MainApp
import org.wit.tripshare.models.DestinationLocation
import org.wit.tripshare.models.DestinationModel
import org.wit.tripshare.ui.map.MapActivity
import timber.log.Timber.i
import java.text.SimpleDateFormat
import java.util.*

class DestinationFragment : Fragment() {
    lateinit var app: MainApp
    private var _fragBinding: FragmentDestinationBinding? = null
    private val fragBinding get() = _fragBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        app = activity?.application as MainApp
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _fragBinding = FragmentDestinationBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        activity?.title = getString(R.string.action_report)

        fragBinding.progressBar.max = 10000
        fragBinding.amountPicker.minValue = 1
        fragBinding.amountPicker.maxValue = 1000

        fragBinding.amountPicker.setOnValueChangedListener { _, _, newVal ->
            //Display the newly selected number to paymentAmount
            fragLayout.paymentAmount.setText("$newVal")
        }
        return root;
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            DestinationFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}